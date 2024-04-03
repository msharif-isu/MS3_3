package com.example.itinerarybuddy.util.ChatWebSockets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.UserData;

/**
 * Custom adapter made for the chat list for improved layout and design.
 */
public class ChatAdapter extends ArrayAdapter<ChatData> {

    /**
     * Creates a new adapter. By default utilizes the chat_list_item resource for the card.
     * @param context application context.
     */
    public ChatAdapter(@NonNull Context context) {
        super(context, R.layout.chat_list_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ChatData chatData = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.chat_list_item, parent, false);
        }

        TextView message = view.findViewById(R.id.chat_message);
        TextView sender = view.findViewById(R.id.sender);

        assert chatData != null;
        message.setText(chatData.getMessage());
        sender.setText(chatData.getSender());
        sender.setText(chatData.getSender());

        if(chatData.getSender().equals("You")){
            message.setBackgroundResource(R.drawable.chat_card_sent);
        }
        else{
            message.setBackgroundResource(R.drawable.chat_card_received);

        }

        return view;
    }
}
