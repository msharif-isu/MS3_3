package com.example.itinerarybuddy.util;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
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

public class UploadImageRequest extends Request<NetworkResponse> {

    private final Response.Listener<NetworkResponse> listener;
    private final Response.ErrorListener errorListener;
    private final byte[] image;
    private final String boundary = "apiclient-" + System.currentTimeMillis();

    public UploadImageRequest(String url, byte[] image, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(Request.Method.PUT, url, errorListener);
        this.image = image;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
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
    protected Map<String, String> getParams() throws AuthFailureError {
        HashMap<String, String> map = new HashMap<String, String>();
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
