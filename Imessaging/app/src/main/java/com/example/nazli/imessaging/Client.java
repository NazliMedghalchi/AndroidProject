package com.example.nazli.imessaging;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.webkit.ClientCertRequest;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by nazlimedghalchi on 2015-11-04.
 */

/*
* Client is defined as sender/receiver in a chat process
* Server would manage all connections
* */
public class Client extends BroadcastReceiver {


    public Socket getSocket() {
        return socket;
    }


    // todo one service manages the tCP connextion
    Context smsContext;
    private String netStatus = Application.NETWORK_STATS_SERVICE;




// Print intent action name on EdiitText - Toast Message
    @Override
    public void OnReceive (Context context, Intent intent) {
        Client client = new Client();
        client.onReceive(context, intent);
    }

//    private EditText title = (EditText) R

    private Socket socket = new Socket();
    private EditText message;

    private InputStream inputStream = new InputStream() {
        @Override
        public int read() throws IOException {

            return 0;
        }
    };
    private OutputStream outputStream = new OutputStream() {
        @Override
        public void write(int oneByte) throws IOException {

        }
    };

    public void connectToServer(){

    }

}
