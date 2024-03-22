package com.example.itinerarybuddy.util;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class ChatWebsocketManager {

    private WebsocketListener websocketListener;

    public ChatWebsocketManager(WebsocketListener websocketListener){

    }

    private class ChatWebsocketClient extends WebSocketClient{

        public ChatWebsocketClient(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {

        }

        @Override
        public void onMessage(String message) {

        }

        @Override
        public void onClose(int code, String reason, boolean remote) {

        }

        @Override
        public void onError(Exception ex) {

        }
    }
}
