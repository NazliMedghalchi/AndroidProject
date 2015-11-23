package com.example.nazli.imessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazlimedghalchi on 2015-11-11.
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatArrayAdapter.ChatMessage> {
    private TextView message;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private Context context;

    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public class ChatMessage extends ChatService {
        public boolean left;
        public String message;

        public ChatMessage (boolean left, String message){
            super();
            this.left = left;
            this.message = message;
        }
    }



    public ChatArrayAdapter(Context context, int txtViewResourceID) {
        super(context, txtViewResourceID);
        this.context = context;
    }
    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index){
        return this.chatMessageList.get(index);
    }

    public View getView (int position, View convertView, ViewGroup parent){
        ChatMessage chatMessageObj = getItem(position);
        LayoutInflater inflater = (LayoutInflater)
                (this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        if (chatMessageObj.left) {

            convertView = inflater.inflate(R.layout.chat_item_right, parent, false);
            message = (TextView) convertView.findViewById(R.id.sentItem);
        }
        else {
            convertView = inflater.inflate(R.layout.chat_item_left, parent, false);
            message = (TextView) convertView.findViewById(R.id.receivedItem);
        }
        message.setText(chatMessageObj.message);
        return convertView;
    }
}


