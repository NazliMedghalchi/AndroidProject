package com.example.nazli.imessaging;

// Create this class to set direction
// Used in "ChatActivity" sendChatMessage

public class ChatMessage{
    public boolean side;
    public String message;
    public String usrId;

    public ChatMessage (boolean side, String message, String usrId){
        super();
        this.side = true;
        this.message = message;
        this.usrId = usrId;
    }
}
