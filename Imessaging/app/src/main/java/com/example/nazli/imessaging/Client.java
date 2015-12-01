package com.example.nazli.imessaging;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

import static com.example.nazli.imessaging.ChatActivity.fromServer;
import static com.example.nazli.imessaging.ChatActivity.from_server;


/**
 * Created by nazlimedghalchi on 2015-11-21.
 */

/*
* Note that ChatActivity is related to ChatArrayAdapter, ChatMessaeg,
* however ClientServer is using ClientServer and Server
* */

public class Client extends AsyncTask<Void, String , String>{

    String SOCKET_CONNECT_TAG;
    String destAddress;
    int destPort;
    String response = "";
    String txtResponse;

    Client(String address, int portNum, String txtRes){
        destAddress = address;
        destPort = portNum;
        this.txtResponse = txtRes;
    }
//
//    @Override
//    protected void onPreExecute(){
//        MainActivity.receiveServer = response;
//        super.onPreExecute();
//    }
    ChatMessage chatMessage;

    @Override
    protected void onPreExecute(){
        fromServer = "Connecting to server...";
    }
    @Override
    protected String doInBackground(Void... params) {

        Socket socket;
        try {
//            Log.i(SOCKET_CONNECT_TAG, "Check SOCKET");
            fromServer = "Check Socket";
//            socket = new Socket(String.valueOf(
//                    new InetSocketAddress(getIpAddress(), destPort)), 6000);
            socket = new Socket(destAddress, destPort);
            fromServer = "passed socket";
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            fromServer = txtResponse + "connected";

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);

            byte[] buffer = new byte[1024];
            int byteStreamR;

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            OutputStream outputStream = socket.getOutputStream();
            while ((byteStreamR = inputStreamReader.read()) != -1) {
//                from_server.setText(bufferedReader.toString("UTF-8"));
                byteArrayOutputStream.write(buffer, 0, byteStreamR);
                response += byteArrayOutputStream.toString("UTF-8");
                ChatActivity.fromServer= response;
            }
            byteArrayOutputStream.close(); // testing

        }catch (UnknownHostException e){
            e.printStackTrace();
            response = "UnknownHostException: : " + e.toString();
            ChatActivity.fromServer = response;

        } catch (IOException e) {
            e.printStackTrace();
            response += "IOException: " + e.toString();
        }
        return null;
    }
// I'm looking for this
    protected void onProgressUpdate(){
        ChatActivity.fromServer = "Connected to Server on port: " + destPort;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        final ChatActivity chatActivity = new ChatActivity();
        response += "server is ON";
        chatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fromServer = response;
            }
        });

    }
    public String getIpAddress() {
        String ip = "";
        try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
					.getNetworkInterfaces();
            response += "got network"; //extra
            from_server.setText(response); //extra
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress
							.nextElement();
					if (inetAddress.isSiteLocalAddress()) {
						ip += "Client running at : "
								+ inetAddress.getHostAddress();
					}
				}
			}
            ip = "127.0.0.1"; //Failed - Permission denied

        } catch (Exception e) { //SocketException
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }

}
