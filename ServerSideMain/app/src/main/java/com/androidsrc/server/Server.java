package com.androidsrc.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

	Socket hostThreadSocket;


	static final int socketServerPORT = 6443; //6000

	PrintStream printStream;
	public Server(MainActivity activity) {
		this.activity = activity;
		Thread socketServerThread = new Thread(new SocketServerThread());
		socketServerThread.start();
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
				while (true) {
					socket = serverSocket.accept(); //supporting multiple clients
					count++;
					message += "#" + count + " from "
							+ socket.getInetAddress() + ":"
							+ socket.getPort() + "\n";
//					activity.msg.set  Text(message + line);
					SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
							socket, count);
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
				try {
					outputStream = hostThreadSocket.getOutputStream();
					while (outputStream != null){
						bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
						printStream = new PrintStream(outputStream);
						message = bufferedWriter.toString();
					}
				}catch (IOException e) {
					e.printStackTrace();
				}

			final String msgReply = "Hello from Server, you are #" + cnt;
			while (hostThreadSocket.isConnected()) {
				try {
					inputStreamReader = new InputStreamReader
							(hostThreadSocket.getInputStream());
//					printStream.print("From client:" + "\n");
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							activity.msg.setText(message + msgReply);
						}
					});
//
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					message += "Something wrong in! " + e.toString() + "\n";
				}
				activity.runOnUiThread(new Runnable() {
					// this is where I can pass message to other client
					@Override
					public void run() {
						activity.msg.setText(message);

						try {
							getInputThread();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
			printStream.close();
		}
	}

	public void getInputThread () throws IOException {
		JSONArray jArray = new JSONArray(); //Array of all passed messages in every conversation
		JSONObject jObj = new JSONObject();
//		printStream.append(jObj.toString());
		jArray.put(jObj);
		inputStream = hostThreadSocket.getInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		message += bufferedReader.toString();

//		JsonReader jsonReader = new JsonReader(bufferedReader);
//		StringBuilder stringBuilder = new
//				StringBuilder();
//		String line = jsonReader.toString();
//		while (line != null) {
//			stringBuilder.append(line);
//			message += line;
//		}
		if (inputStreamReader == null) {
			activity.msg.setText("printStream and socket are closed by Client" );
					message += "Client replayed: " + "\n";
				System.out.print(message);
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
// Added to ckeck server connection on pause
	public void onPause() throws IOException {
		if (serverSocket.isBound()) {
			serverSocket.accept();
		}
	}


	public void onDestroy() {
		if (serverSocket != null) {
			try {
				printStream.close();
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
