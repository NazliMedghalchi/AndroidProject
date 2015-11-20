package Server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nazlimedghalchi on 2015-11-19.
 */
public class SocketServerReplyThread extends Thread {

    private Socket mainSocket;
    int cnt;
    SocketServerReplyThread(Socket socket, int c){
        mainSocket = socket;
        cnt = c;
    }

    @Override
    public void run(){
        OutputStream outputStream;
        String msgReply = "Hi, Client number is: " + cnt;
        try {
            outputStream = mainSocket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.close();

            msgReply += "reply: " + msgReply + "\n";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
