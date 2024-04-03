package com.example.itinerarybuddy.util;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/** Create a singleton class to ensure only one instantiation for our request queue given the context. */
public class Singleton {

    /** Saves the instance of our one class. */
    private static Singleton inst;

    /* Saves the context. */
    private static Context context;

    /* The request queue for Volley requests. */
    private RequestQueue queue;

    private Singleton(Context c){
        context = c;
        queue = getQueue();
    }

    /** Gets the Singleton instantiation, or creates one if there is not one. */
    public static Singleton getInstance(Context c){
        if(inst == null)
            inst = new Singleton(c);
        return inst;
    }

    /** Get the request queue. */
    public RequestQueue getQueue(){
        if(queue == null){
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }

    /** Adds a request to our request queue. */
    public<T> void addRequest(Request<T> request){
        getQueue().add(request);
    }
}
