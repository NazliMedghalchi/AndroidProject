package com.androidsrc.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

					inputStream = socket.getInputStream();
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder stringBuilder = new
							StringBuilder();
					final String line = bufferedReader.readLine();
					while (line != null) {
						stringBuilder.append(line);
					}

					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							activity.msg.setText(message + line);
						}
					});

					SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
							socket, count);
					socketServerReplyThread.run();
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Server sends out ...
	private class SocketServerReplyThread extends Thread {

		private Socket hostThreadSocket;
		int cnt;

		SocketServerReplyThread(Socket socket, int c) {
			hostThreadSocket = socket;
			cnt = c;
		}

		@Override
		public void run() {
			OutputStream outputStream;
//			outputStream = hostThreadSocket.getOutputStream();
//			printStream = new PrintStream(new OutputStream() {
//				@Override
//				public void write(int oneByte) throws IOException {
//
//				}
//			});
			String msgReply = "Hello from Server, you are #" + cnt;

			try {

				while (hostThreadSocket.isConnected()){ // hostThreadSocket.isConnected
					inputStreamReader = new InputStreamReader
								(hostThreadSocket.getInputStream());
					msgReply += bufferedReader.readLine().toString();

					printStream.print("From client:" + msgReply + "\n");
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								activity.msg.setText(message);
							}
						});
					if (inputStreamReader == null){
						activity.msg.setText("printStream and socket are closed by Client" + cnt);
//					message += "Client replayed: " + msgReply + "\n";
//				System.out.print(message);
					}
				}
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
				}
			});
			printStream.close();

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
