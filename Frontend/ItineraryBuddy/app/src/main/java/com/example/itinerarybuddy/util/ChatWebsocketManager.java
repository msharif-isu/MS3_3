package com.example.itinerarybuddy.util;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class ChatWebsocketManager {

    private WebsocketListener listener;

    private ChatWebsocketClient client;

    public ChatWebsocketManager(WebsocketListener websocketListener){
        this.listener = websocketListener;
    }

    public void connect(String url){
        try{
            URI uri = URI.create(url);
            client = new ChatWebsocketClient(uri);
            client.connect();
        }catch(Exception e){
            Log.e("Exception ", e.toString());
        }
    }

    public void sendMessage(String msg){
        client.send(msg);
    }

    private class ChatWebsocketClient extends WebSocketClient{

        public ChatWebsocketClient(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.d("Websocket opened", handshakedata.toString());
            listener.onOpen(handshakedata);
        }

        @Override
        public void onMessage(String message) {
            Log.d("Websocket message sent", message);
            listener.onMessage(message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            Log.d("Websocket closed", reason);
            listener.onClose(code, reason, remote);
        }

        @Override
        public void onError(Exception ex) {
            Log.d("WebSocket error", ex.toString());
            listener.onError(ex);
        }
    }
}
