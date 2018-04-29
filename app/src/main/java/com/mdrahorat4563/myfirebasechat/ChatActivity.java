package com.mdrahorat4563.myfirebasechat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mdrahorat4563.myfirebasechat.adapters.MessageAdapter;
import com.mdrahorat4563.myfirebasechat.models.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText message;
    ImageButton sendButton;

    ListView lv;

    List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        message = findViewById(R.id.message);
        sendButton = findViewById(R.id.btn_send);

        lv = findViewById(R.id.message_listview);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String messageToSend = message.getText().toString();
                    Timestamp timestamp = new Timestamp(new Date());
                    FirebaseUser userObj = FirebaseAuth.getInstance().getCurrentUser();
                    String user = userObj.getEmail();

                    if (messageToSend != null || !messageToSend.isEmpty()) {
                        Map<String, Object> message = new HashMap<>();
                        message.put("user", user);
                        message.put("message", messageToSend);
                        message.put("timestamp", timestamp);

                        db.collection("messages").add(message);
                    }
                }
                catch (NullPointerException e){
                    Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
                message.getText().clear();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        db.collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        messageList.clear();
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            messageList.add(documentSnapshot.toObject(Message.class));
                        }
                        MessageAdapter ma = new MessageAdapter(ChatActivity.this, messageList);

                        ma.notifyDataSetChanged();
                        lv.setAdapter(ma);
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
