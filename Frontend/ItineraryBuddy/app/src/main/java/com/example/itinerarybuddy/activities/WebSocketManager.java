package com.example.itinerarybuddy.activities;

import android.util.Log;

import com.example.itinerarybuddy.data.Post_Itinerary;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class WebSocketManager {
    private static WebSocketManager instance;
    private MyWebSocketClient webSocketClient;
    private WebSocketListener webSocketListener;

    private WebSocketManager(){}

    public static synchronized WebSocketManager getInstance(){
        if(instance == null){
            instance = new WebSocketManager();
        }

        return instance;
    }

    public void setWebSocketListener(WebSocketListener listener){
        this.webSocketListener = listener;
    }

    public void removeWebSocketListener(){
        this.webSocketListener = null;
    }

    public void connectWebSocket(String serverUrl){

        try{
            URI serverUri = URI.create(serverUrl);
            webSocketClient = new MyWebSocketClient(serverUri);
            webSocketClient.connect();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendPost(Post_Itinerary post){

        if(webSocketClient != null && webSocketClient.isOpen()){

            webSocketClient.send(toJson(post));
        }
    }

    private String toJson(Post_Itinerary post) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", post.getUsername());
            jsonObject.put("timePosted", post.getTimePosted());
            jsonObject.put("itinerary", post.getPostFile());
            jsonObject.put("caption", post.getCaption());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void disconnectWebSocket(){

        if(webSocketClient != null){
            webSocketClient.close();
        }
    }

    //a private inner class: MyWebSocketClient

    private class MyWebSocketClient extends WebSocketClient{

        private MyWebSocketClient(URI serverUri){
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata){
            Log.d("WebSocket", "Connected");

            if(webSocketListener != null){
                webSocketListener.onWebSocketOpen(handshakedata);
            }
        }

        @Override
        public void onMessage(String message){

            Log.d("WebSocket", "Received message: " + message);
            if (webSocketListener != null) {
                webSocketListener.onWebSocketMessage(message);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote){

            Log.d("WebSocket", "Closed");

            if(webSocketListener != null){
                webSocketListener.onWebSocketClose(code, reason, remote);
            }
        }

        @Override
        public void onError(Exception ex){
            Log.d("WebSocket", "Error");

            if(webSocketListener!= null){
                webSocketListener.onWebSocketError(ex);
            }
        }

    }

}
