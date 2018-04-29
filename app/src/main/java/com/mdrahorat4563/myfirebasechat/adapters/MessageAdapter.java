package com.mdrahorat4563.myfirebasechat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mdrahorat4563.myfirebasechat.R;
import com.mdrahorat4563.myfirebasechat.models.Message;

import java.util.List;

/**
 * Created by Michal Drahorat on 4/28/2018.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(@NonNull Context context, @NonNull List<Message> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message msg = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.message_item, parent,
                            false);
        }

        TextView user = convertView.findViewById(R.id.txt_msg_user);
        TextView timestamp = convertView.findViewById(R.id.txt_msg_timestamp);
        TextView message = convertView.findViewById(R.id.txt_msg_message);

        user.setText(msg.getUser());
        timestamp.setText(msg.getTimeStamp());
        message.setText(msg.getMessage());


        return convertView;
    }
}
