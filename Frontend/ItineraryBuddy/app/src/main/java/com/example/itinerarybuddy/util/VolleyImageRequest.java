package com.example.itinerarybuddy.util;

import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VolleyImageRequest extends Request<String> {

    private final byte[] image;

    public VolleyImageRequest(int method, String url, byte[] image, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.image = image;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=apiclient" + System.currentTimeMillis();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        byte[] byteArray = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try{
            dos.write(image);
            byteArray = bos.toByteArray();
        }catch(IOException e){
            Log.e("Error: ", e.toString());
        }

        return byteArray;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }

    @Override
    protected void deliverResponse(String s) {

    }
}
