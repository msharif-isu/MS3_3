package com.example.itinerarybuddy.util.ChatWebSockets;

import org.java_websocket.handshake.ServerHandshake;

public interface WebsocketListener {

    void onOpen(ServerHandshake handshake);

    void onMessage(String msg);

    void onClose(int code, String reason, boolean remote);

    void onError(Exception ex);
}
