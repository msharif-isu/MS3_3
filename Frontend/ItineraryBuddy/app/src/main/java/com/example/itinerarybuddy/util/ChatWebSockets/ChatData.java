package com.example.itinerarybuddy.util.ChatWebSockets;

public class ChatData {

    /**
     * The contents of the message.
     */
    private final String message;

    /**
     * The sender of the message.
     */
    private final String sender;

    /**
     * Creates a ChatData item.
     * @param message content.
     * @param sender of message.
     */
    public ChatData(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    /**
     * Getter for message.
     * @return message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter for sender.
     * @return sender.
     */
    public String getSender(){
        return sender;
    }
}
