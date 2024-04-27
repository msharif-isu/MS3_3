package com.example.itinerarybuddy.ui.notifications;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.BlogImageConfirmationBinding;
import com.example.itinerarybuddy.util.CustomImageRequest;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BlogPhotoAlbum extends AppCompatActivity {

    /**
     * Adapter for the list of images to display for this blog.
     */
    private ImageAdapter adapter;

    /**
     * URI for a selected image to be uploaded.
     */
    private Uri uploadUri;

    /**
     * The ID for this blog post.
     */
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.blog_album);

        //id = getIntent().getStringExtra("ID");
        String poster = getIntent().getStringExtra("USER");
        id = "1";

        GridView grid = findViewById(R.id.grid);
        adapter = new ImageAdapter(getApplicationContext());
        grid.setAdapter(adapter);
//        adapter.add(BitmapFactory.decodeResource(getResources(), R.drawable.earth_icon));
//        adapter.add(BitmapFactory.decodeResource(getResources(), R.drawable.airplane_trip));
//        adapter.add(BitmapFactory.decodeResource(getResources(), R.drawable.airplane_trip));
//        adapter.add(BitmapFactory.decodeResource(getResources(), R.drawable.earth_icon));
//        adapter.notifyDataSetChanged();

        // Load images
        getAlbum();

        // Image selection process
        ActivityResultLauncher<String> getImage = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            uploadUri = uri;
            if(uploadUri != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.layout.blog_image_confirmation);
                builder.setTitle("Photo Upload Confirmation");
                View view = BlogImageConfirmationBinding.inflate(getLayoutInflater()).getRoot();
                builder.setView(view);

                ImageView image = view.findViewById(R.id.upload_to_blog);
                image.setImageURI(uri);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploadImage(uriToImage(uri));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
            else{
                Toast.makeText(getApplicationContext(), "No Image Selected!", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton addPhoto = findViewById(R.id.add_photo);

        // Blog post will only be editable to the user that posted it.
        //if(UserData.getUsername().equals(poster)){
        if(true){
            addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getImage.launch("image/*");
                }
            });
            grid.setClickable(true);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
        else{
            addPhoto.setVisibility(View.INVISIBLE);
            grid.setClickable(false);
        }
    }

    /**
     * Retrieve the blog item and parse through the list of images to load the adapter.
     */
    private void getAlbum(){
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/BlogPost/1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.d("Blog:", jsonObject.toString());
                    JSONArray images = jsonObject.getJSONArray("blogImageList");
                    for(int i = 0; i < images.length(); i++) {
                        int id = images.getJSONObject(i).getInt("id");
                        getImage(id);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Volley Error:", volleyError.toString());
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(request);
    }

    /**
     * Makes an image request to get the group image.
     */
    private void getImage(int id){
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/BlogPost/Image/" + id;
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                Log.d("Volley Image: ", bitmap.toString());
                adapter.insert(bitmap, 0);
                adapter.notifyDataSetChanged();
            }
        }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Volley Error: ", volleyError.toString());
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(request);
    }

    /**
     * Helper method to convert the image data format to be uploaded.
     * @param image uri.
     * @return uri as bytes.
     */
    private byte[] uriToImage(Uri image){
        byte[] bytes = new byte[4*1024];
        try{
            @SuppressLint("Recycle") InputStream input = getContentResolver().openInputStream(image);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            while (true) {
                assert input != null;
                if (input.read(bytes) == -1){
                    break;
                }
                else{
                    buffer.write(bytes, 0, bytes.length);
                }
            }

            bytes = buffer.toByteArray();
        }catch(IOException e){
            Log.e("Error: ", e.toString());
        }

        return bytes;
    }

    /**
     * Uploads a new image to the collection of photos for this blog post.
     * @param bytes image data.
     */
    private void uploadImage(byte[] bytes){
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/BlogPost/Image/" + id;
        CustomImageRequest request = new CustomImageRequest(Request.Method.PUT, url, bytes, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse networkResponse) {
                Log.d("Uploaded Image", networkResponse.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Image Upload Error", volleyError.toString());
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(request);
    }

    /**
     * Removes this photo from the blog album.
     * @param id of the photo.
     */
    private void deleteImage(String id){
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/BlogPost/Image/" + id;
    }
}