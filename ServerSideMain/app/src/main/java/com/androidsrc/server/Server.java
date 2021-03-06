package com.androidsrc.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

public class Server {
	MainActivity activity;
	ServerSocket serverSocket;
	String message = "";

	InputStreamReader inputStreamReader;
	InputStream inputStream;
	BufferedReader bufferedReader;

	OutputStream outputStream;
	OutputStreamWriter outputStreamWriter;
	BufferedWriter bufferedWriter;
	PrintStream printStream;

	File file;

	JSONArray jArray = null;
	JSONObject jObj = null;

	Socket hostThreadSocket;


	static final int socketServerPORT = 6443; //6000

	public Server(MainActivity activity) {
		this.activity = activity;
		Thread socketServerThread = new Thread(new SocketServerThread());
		socketServerThread.start();
		file = new File("fileOut.txt");
	}

	public int getPort() {
		return socketServerPORT;
	}

	// Client sends ...
	private class SocketServerThread extends Thread {
		int count = 0;

		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(socketServerPORT);
				Socket socket;
				SocketServerReplyThread socketServerReplyThread;
				while (true) {
					socket = serverSocket.accept(); //supporting multiple clients
					count++;
					message += "#" + count + " from "
							+ socket.getInetAddress() + ":"
							+ socket.getPort() + "\n";
					socketServerReplyThread = new SocketServerReplyThread(
							socket, count);
					//for every client get the input thread
					socketServerReplyThread.run();
					}
			} catch ( IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Server replies to what client sent on inputStream
	private class SocketServerReplyThread extends Thread {
		int cnt;
		SocketServerReplyThread(Socket socket, int c) {
			hostThreadSocket = socket;
			cnt = c;
		}
		@Override
		public void run() {
			GetInputThread getInputThread = null;

			final String msgReply = "Hello from Server, you are #" + cnt;
				try {
					inputStreamReader = new InputStreamReader
							(hostThreadSocket.getInputStream());
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					while ((message = bufferedReader.readLine()) != null){
						message += msgReply + bufferedReader.readLine();
					}
					printStream = new PrintStream(file);
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								activity.msg.setText(message);
								printStream.print("Client: " + "\n");

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					try {
						getInputThread = new GetInputThread(hostThreadSocket);
						getInputThread.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					message += "Something is wrong Input! " + e.toString() + "\n";
				}
				activity.runOnUiThread(new Runnable() {
					// this is where I can pass message to other client
					@Override
					public void run() {
						activity.msg.setText(message);
					}
				});
			try {
				outputStream = hostThreadSocket.getOutputStream();
				if (outputStream != null){
					bufferedReader = new BufferedReader(new InputStreamReader(hostThreadSocket.getInputStream()));
//					outputStreamWriter = new OutputStreamWriter(outputStream);
//					printStream = new PrintStream(String.valueOf(outputStreamWriter));
					message += bufferedReader.readLine();
//					printStream.print(message + bufferedWriter + "\n");
//					printStream.flush();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
//			printStream.close();
		}
	}

	public class GetInputThread extends Thread {
		GetInputThread (Socket hostSocket) throws IOException {
			inputStream = hostSocket.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			message += bufferedReader.read();
			if (inputStreamReader == null) {
				message += "Client replayed: " + "\n";
				System.out.println(message);
			}
		}
	}

	public String getIpAddress() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress
							.nextElement();
					if (inetAddress.isSiteLocalAddress()) {
						ip += "Server running at : "
								+ inetAddress.getHostAddress();
					}
				}
			}
//		ip += "10.0.2.2"; //Failed - Permission denied

		} catch (Exception e) { //SocketException
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}
		return ip;
	}
// Added to check server connection on pause
	public void onPause() throws IOException {
		if (serverSocket.isClosed()) {
			serverSocket.accept();
		}
	}

	public void onDestroy() {
		try {
			outputStream.close();
			inputStream.close();
			hostThreadSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
