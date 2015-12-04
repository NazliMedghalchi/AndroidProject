package com.example.myimsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ClientThread implements Runnable {
	private Socket s;
	Handler handler;
	Handler revHandler;
	BufferedReader br = null;
	OutputStream os = null;

	public boolean isConn = false;;

	public ClientThread() {
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		s = new Socket();
		try {
			s.connect(new InetSocketAddress("10.10.183.231", 4025), 5000);
			isConn = true;
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = s.getOutputStream();
			new Thread() {

				@Override
				public void run() {
					String content = null;
					try {
						while ((content = br.readLine()) != null) {
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = content;
							handler.sendMessage(msg);
						}
					} catch (IOException io) {
						Message msg = new Message();
						msg.what = 0x124;
						msg.obj = "disconnected!";
						handler.sendMessage(msg);
						io.printStackTrace();
					}
					isConn = false;
				}

			}.start();
			Looper.prepare();


			revHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 0x345) {
						try {
							os.write((msg.obj.toString() + "\r\n")
									.getBytes("UTF-8"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			};

			Message msg = new Message();
			msg.what = 0x125;
			msg.obj = "connected!";
			handler.sendMessage(msg);

			if (buffer != "") {
				Message bmsg = new Message();
				bmsg.what = 0x345;
				bmsg.obj = buffer;
				revHandler.sendMessage(bmsg);
				buffer = "";
			}
			Looper.loop();

		} catch (SocketTimeoutException e) {
			Message msg = new Message();
			msg.what = 0x124;
			msg.obj = "timeout!";
			handler.sendMessage(msg);
		} catch (IOException io) {
			io.printStackTrace();
		}
		isConn = false;

	}

	String buffer = "";

	public void sendMessage(String m) {
		if (isConn) {
			Message msg = new Message();
			msg.what = 0x345;
			msg.obj = m;
			revHandler.sendMessage(msg);
		} else {
			buffer = m;
			new Thread(this).start();
		}
	}
}