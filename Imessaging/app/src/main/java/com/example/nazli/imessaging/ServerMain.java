package com.example.nazli.imessaging;


import android.view.LayoutInflater;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Scanner;

/**
 * Created by nazlimedghalchi on 2015-11-04.
 */
public abstract class ServerMain {

    public static void main (String[] args) {
        // TODO: 2015-11-05 use laptop as server with DNS service or hardcoding the Ip
//        final String SERVERURL = "hrrp://loccalhost:8888";
        Socket socket;
        ServerSocket serverSocket;

//        Socket serverSocket;
        final BufferedReader in;
        final PrintWriter out;

        // Read from keyboard
        final Scanner scanner = new Scanner(System.in);
        try {
            // create server address by means of port number
            serverSocket = new ServerSocket(5000);
            socket = serverSocket.accept();
            // send out
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            PrintWriter out;
            String text;
            @Override
            public void run() {
                while(true){
                    text = scanner.next();
                    out.write(text);
                    out.flush();
                }
            }
        });

    }

//    private Socket clientSocket = serverSocket.
private InputStream inputStream = new InputStream() {
    @Override
    public int read() throws IOException {
        URL url = new URL("http://android.com");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = null;
//        httpURLConnection.setRequestMethod("GET");

        try {
            httpURLConnection.connect();
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();

            String line = "";
            line = reader.readLine();
            while(line!=null)
            {
                buffer.append(reader);
            }
//            in.read();
//            urlConnection = (HttpURLConnection) url.openConnection();
        }


        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return 0;
    }
};
    private OutputStream outputStream = new OutputStream() {
        @Override
        public void write(int oneByte) throws IOException {

        }
    };

    public void connectToServer(){
//        URL url = null;
//        try {
//            url = new URL("http://android.com/");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

//        Socket socket = new Socket();
//        outputStream.write(MidiDeviceInfo.PortInfo.TYPE_OUTPUT);

    }



}
