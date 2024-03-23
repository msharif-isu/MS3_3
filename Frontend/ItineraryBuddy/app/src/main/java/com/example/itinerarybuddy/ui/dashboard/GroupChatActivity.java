package com.example.itinerarybuddy.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.util.ChatWebsocketManager;
import com.example.itinerarybuddy.util.WebsocketListener;

import org.java_websocket.handshake.ServerHandshake;

import java.util.Objects;

public class GroupChatActivity extends AppCompatActivity implements WebsocketListener {

    private Group group;

    private TextView chat;

    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.fragment_group_chat);

        TextView title = findViewById(R.id.chat_title);
        chat = findViewById(R.id.chat_text);
        message = findViewById(R.id.message_input);
        ImageButton send = findViewById(R.id.send_button);

        Bundle bundle = getIntent().getExtras();
        int position;
        if (bundle != null) {
            position = Integer.parseInt(Objects.requireNonNull(bundle.getString("POSITION")));
            group = ListGroups.adapter.getItem(position);

            assert group != null;
            String text = group.getTravelGroupName() + " Chat";
            title.setText(text);
        }

        ChatWebsocketManager websocket = new ChatWebsocketManager(GroupChatActivity.this);
        websocket.connect("ws://10.0.2.2:8080/chat/");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = message.getText().toString();
                try {
                    websocket.sendMessage(text);
                }catch(Exception e){
                    Log.e("Message error: ", e.toString());
                }
            }
        });
    }

    @Override
    public void onOpen(ServerHandshake handshake) {

    }

    @Override
    public void onMessage(String msg) {
        runOnUiThread(() -> {
            String text = chat.getText().toString() + "\n" + message;
            chat.setText(text);
        });

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }

}
