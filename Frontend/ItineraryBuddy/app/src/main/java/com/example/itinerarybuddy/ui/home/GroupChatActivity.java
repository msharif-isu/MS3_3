package com.example.itinerarybuddy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.FragmentGroupChatBinding;
import com.example.itinerarybuddy.util.ChatWebSockets.ChatAdapter;
import com.example.itinerarybuddy.util.ChatWebSockets.ChatData;
import com.example.itinerarybuddy.util.ChatWebSockets.ChatWebsocketManager;
import com.example.itinerarybuddy.util.ChatWebSockets.WebsocketListener;

import org.java_websocket.handshake.ServerHandshake;

/**
 * The activity for a group's chat.
 */
public class GroupChatActivity extends AppCompatActivity implements WebsocketListener {

    /**
     * Manager for the websocket connection.
     */
    private ChatWebsocketManager chatWebsocket;

    /**
     * List to display each message.
     */
    private ListView chatList;

    /**
     * Custom adapter to generate our list of messages.
     */
    private ChatAdapter adapter;

    /**
     * Input to send a new message to the chat.
     */
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        /**
         * The binding for the view.
         */
        com.example.itinerarybuddy.databinding.FragmentGroupChatBinding binding = FragmentGroupChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TextView title = view.findViewById(R.id.chat_title);
        chatList = view.findViewById(R.id.chat_text);
        message = view.findViewById(R.id.message_input);
        ImageButton send = view.findViewById(R.id.send_button);

        /**
         * Referenced group for this chat.
         */
        Group group = LoadGroup.group;

        assert group != null;
        String text = group.getTravelGroupName() + " Chat";
        title.setText(text);

        adapter = new ChatAdapter(getApplicationContext());
        chatList.setAdapter(adapter);
        chatList.setClickable(false);
        chatList.setDivider(null);
        chatList.setDividerHeight(0);

        chatWebsocket = new ChatWebsocketManager(GroupChatActivity.this);
        String ws = "ws://coms-309-035.class.las.iastate.edu:8080/chat/" + group.getTravelGroupID() + "/" + UserData.getUsername();
        chatWebsocket.connect(ws);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = message.getText().toString();
                if (!text.isEmpty()) {
                    try {
                        chatWebsocket.sendMessage(text);
                        message.setText("");
                    } catch (Exception e) {
                        Log.e("Message error: ", e.toString());
                    }
                }
            }
        });

        ImageButton back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatWebsocket.disconnect();
                Intent i = new Intent(getApplicationContext(), LoadGroup.class);
                Bundle bundle = new Bundle();
                bundle.putString("POSITION", Integer.valueOf(LoadGroup.index).toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    @Override
    public void onOpen(ServerHandshake handshake) {}

    @Override
    public void onMessage(String msg) {
        runOnUiThread(() -> {
            // Split into separate messages given chat history.
            String[] messages = msg.split("\n");
            for(String s : messages){
                // Parse out the message and the sender.
                if(!s.isEmpty()){
                    if(!s.contains(" disconnected")){
                        String sender = s.substring(0, s.indexOf(":"));
                        String message = s.substring(s.indexOf(":") + 2);
                        if(sender.equals(UserData.getUsername())){
                            sender = "You";
                        }
                        adapter.add(new ChatData(message, sender));
                    }
                }
            }

            adapter.notifyDataSetChanged();

            // Automatically scrolls the list down to latest message.
            chatList.post(new Runnable() {
                @Override
                public void run() {
                    chatList.smoothScrollToPosition(adapter.getCount() - 1);
                }
            });
        });
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {}

    @Override
    public void onError(Exception ex) {
        Toast.makeText(getApplicationContext(), "There was an error with the connection!", Toast.LENGTH_LONG).show();
    }
}