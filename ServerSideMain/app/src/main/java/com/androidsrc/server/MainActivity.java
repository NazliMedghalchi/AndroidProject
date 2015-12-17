package com.androidsrc.server;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity {

	Server server;
	TextView infoip, msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		infoip = (TextView) findViewById(R.id.infoip);
		msg = (TextView) findViewById(R.id.msg);
		server = new Server(this);
		String ipAddress = server.getIpAddress();
		int ip = server.getPort();
		infoip.setText(String.format("%s:%d", ipAddress, ip));
	}

	@Override
	protected void onDestroy() {
		server.onDestroy();
		super.onDestroy();
	}

	@Override
	protected void onPause(){
		try {
			server.onPause();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onPause();
	}

}