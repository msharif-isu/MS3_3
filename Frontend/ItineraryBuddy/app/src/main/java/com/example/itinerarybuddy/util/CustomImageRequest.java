package com.example.itinerarybuddy.util;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CustomImageRequest extends Request<NetworkResponse> {

    /**
     * Response Listener for this request.
     */
    private final Response.Listener<NetworkResponse> listener;

    /**
     * Response Error Listener for this request.
     */
    private final Response.ErrorListener errorListener;

    /**
     * Image data. Only used for a PUT request.
     */
    private final byte[] image;

    private final String boundary = "apiclient-" + System.currentTimeMillis();

    /**
     * Constructor to create a PUT request to add a group image.
     * @param url for the request.
     * @param image data for the body.
     * @param listener for the request.
     * @param errorListener for the request.
     */
    public CustomImageRequest(String url, byte[] image, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(Request.Method.PUT, url, errorListener);
        this.image = image;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    /**
     * Constructor to create a DELETE request to remove a group image.
     * @param url for the request.
     * @param listener for the request.
     * @param errorListener for the request.
     */
    public CustomImageRequest(String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(Request.Method.DELETE, url, errorListener);
        this.image = null;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody(){
        byte[] byteArray = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try{
            dos.writeBytes("--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"image.jpeg\"\r\n");
            dos.writeBytes("\r\n");

            dos.write(image);

            dos.writeBytes("\r\n");
            dos.writeBytes("--" + boundary + "--\r\n");

            byteArray = bos.toByteArray();
        }catch(IOException e){
            Log.e("Error: ", e.toString());
        }

        return byteArray;
    }

    @Nullable
    @Override
    protected Map<String, String> getParams(){
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "image/jpeg");
        return map;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            return Response.success(networkResponse,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse networkResponse) {
        listener.onResponse(networkResponse);
    }

    @Override
    public void deliverError(VolleyError error) {
        errorListener.onErrorResponse(error);
    }
}
