package com.example.nazli.imessaging;

// Create this class to set direction
// Used in "ChatActivity" sendChatMessage

public class ChatMessage{
    public boolean left;
    public String message;

    public ChatMessage (boolean left, String message){
        super();
        this.left = left;
        this.message = message;
    }
}
