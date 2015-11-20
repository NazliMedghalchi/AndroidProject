package Server;

import android.os.AsyncTask;
import android.provider.Telephony;

import com.example.nazli.imessaging.ChatService;
import com.example.nazli.imessaging.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by nazlimedghalchi on 2015-11-19.
 */

/*ref:
* http://androidsrc.net/android-client-server-using-sockets-server-implementation/*/

// TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip

public class Server{

    MainActivity mainActivity;
    ServerSocket serverSocket;
    String msg = "";
    final int socketServerPort = 8644;
    public Server (MainActivity mainActivity) throws IOException {
        this.mainActivity = mainActivity;
        serverSocket = new ServerSocket(getPort());
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        Thread socketServerThread = new Thread(new SocketServereThread());
        socketServerThread.start();

    }

    public int getPort() {
        return socketServerPort;
    }

    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class SocketServereThread extends Thread{
        int count = 0;
        @Override
        public void run(){
            try {
                serverSocket = new ServerSocket(socketServerPort);
                while (true){
                    Socket socket = serverSocket.accept();
                    count ++;
                    msg += "#" + count + "from"
                            + socket.getInetAddress() + ":"
                            + socket.getPort();
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.fromServer.setText(msg);
                        }
                    });
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            msg += "Something is WRONG!!" + toString() + "\n";
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.fromServer.setText(msg);
                }
            });
        }
    public class SocketServerReplyThread extends Thread {
        private Socket hostThreadSocket;
        int counter;

        SocketServerReplyThread (Socket socket, int cnt){
            this.hostThreadSocket = socket;
            this.counter = cnt;
        }

        @Override
        public void run(){
            OutputStream out;
            String message = "Reply - your number is: "+ counter;

            try {
                out = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(out);
                printStream.print(msg);
                printStream.close();

                msg += "Replied: " + message + "\n";
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.fromServer.setText(msg);
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
                msg += "Something went WRONG!!" + e.toString() + "\n";
            }
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.fromServer.setText(msg);
                }
            });
        }
    }
    }

    public String getIpAddress(){
        String IP = ""; //tried IP address: 10.20.141.218, localhost, 192.168.0.14
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()){
                NetworkInterface networkInterface = enumeration.nextElement();
                Enumeration<InetAddress> enumerationNetInterface =
                        networkInterface.getInetAddresses();
                while (enumerationNetInterface.hasMoreElements()){
                    InetAddress inetAddress = enumerationNetInterface.nextElement();
                    if (inetAddress.isAnyLocalAddress()){
                        IP += "Server running at: " + inetAddress.getHostAddress();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return IP;
    }
}
