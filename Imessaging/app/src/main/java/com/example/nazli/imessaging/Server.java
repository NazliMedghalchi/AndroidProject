package com.example.nazli.imessaging;

import android.content.BroadcastReceiver;
import android.content.ServiceConnection;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by nazlimedghalchi on 2015-11-04.
 */
public abstract class Server extends BroadcastReceiver{
    
//    // TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip
    private Socket serverSocket = new Socket();
//    private Socket clientSocket = serverSocket.

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
