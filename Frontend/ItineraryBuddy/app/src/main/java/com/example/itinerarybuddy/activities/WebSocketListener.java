package com.example.itinerarybuddy.activities;

import com.example.itinerarybuddy.data.Post_Itinerary;

import org.java_websocket.handshake.ServerHandshake;
public interface WebSocketListener {

    void onWebSocketOpen(ServerHandshake handshakedata);
    void onWebSocketMessage(String message);
    void onWebSocketClose(int code, String reason, boolean remote);
    void onWebSocketError(Exception ex);

}
