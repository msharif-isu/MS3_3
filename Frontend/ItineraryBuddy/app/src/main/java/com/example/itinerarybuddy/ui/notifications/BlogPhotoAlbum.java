package com.example.itinerarybuddy.ui.notifications;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.databinding.BlogImageConfirmationBinding;
import com.example.itinerarybuddy.util.Singleton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BlogPhotoAlbum extends AppCompatActivity {

    private ImageAdapter adapter;

    private Uri uploadUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.blog_album);

        GridView grid = findViewById(R.id.grid);
        grid.setNumColumns(2);
        adapter = new ImageAdapter(getApplicationContext());
        grid.setAdapter(adapter);
        adapter.add(BitmapFactory.decodeResource(getResources(), R.drawable.earth_icon));
        adapter.add(BitmapFactory.decodeResource(getResources(), R.drawable.airplane_trip));
        adapter.add(BitmapFactory.decodeResource(getResources(), R.drawable.airplane_trip));
        adapter.notifyDataSetChanged();

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
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage.launch("image/*");
            }
        });
    }

    /**
     * Makes an image request to get the group image.
     */
    private void getImage(){
        String url = ""; //TODO
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                Log.d("Volley Image: ", bitmap.toString());
                //image.setImageBitmap(bitmap);
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

    private void uploadImage(byte[] bytes){

    }
}