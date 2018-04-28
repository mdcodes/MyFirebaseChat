package com.mdrahorat4563.myfirebasechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity {

    EditText message;
    ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        message = findViewById(R.id.message);
        sendButton = findViewById(R.id.btn_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opt_logout:
                FirebaseAuth.getInstance().signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
