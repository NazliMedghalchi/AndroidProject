package com.example.nazli.imessaging;

import other.Message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

/**
 * Created by nazlimedghalchi on 2015-11-14.
 */
public abstract class MessageListAdapter extends BaseAdapter{

    private Context context;
    private List<Message> messagesItems;

    public MessageListAdapter (Context context, List<Message> navDrawerItems){
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount(){
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * The following list not implemented reuseable
         * are showing incorrect data Add the solution if there is
         * ***/
        Message m = messagesItems.get(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        // Identifying the message owner
        if(messagesItems.get(position).isSelf()) {
            // message belongs to you, so load the right layout
            convertView = mInflater.inflate(R.layout.chat_item_right, null);
        } else {
            // message belongs to other person, lad the left layout
            convertView = mInflater.inflate(R.layout.chat_item_left, null);
        }
        TextView lblForm = (TextView) convertView.findViewById(R.id.editText_search);
        EditText txtMsg = (EditText) convertView.findViewById(R.id.editText_msg);

        txtMsg.setText(m.getMessage());
        lblForm.setText(m.getFromName());

        return convertView;
    }
}
