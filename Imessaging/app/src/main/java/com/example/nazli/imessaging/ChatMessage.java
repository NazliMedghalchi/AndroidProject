package com.example.nazli.imessaging;

// Create this class to set direction
// Used in "ChatActivity" sendChatMessage

public class ChatMessage{
    public boolean left;
    public String message;
    public String usrId;

    public ChatMessage (boolean left, String message, String usrId){
        super();
        this.left = left;
        this.message = message;
        this.usrId = usrId;
    }
}
