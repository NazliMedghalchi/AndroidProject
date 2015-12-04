package com.example.myimsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	ClientThread clientThread;
	Handler tcpHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
        	String m=msg.obj.toString();
            if (msg.what == 0x123) {  
                if (m.equals("R1"))
                {
                	Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                	new Handler().postDelayed(new Runnable(){
                		@Override
                		public void run(){
                			finish();
                		}
                	}, 3000);
                }
                else
                {
            		Toast.makeText(getApplicationContext(), "Your password did not match，please try again!", Toast.LENGTH_SHORT).show();
                }
            }  
            if (msg.what == 0x124) {  
            	Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
            }
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_register);

		clientThread = ((MyApplication) getApplication()).clientThread;
		clientThread.setHandler(tcpHandler);

	}

	public void btn_ok(View v) {
    	String uid = ((EditText)findViewById(R.id.et_account)).getText().toString().trim();
    	String pwd=((EditText)findViewById(R.id.et2_password)).getText().toString().trim();
    	String pwd2=((EditText)findViewById(R.id.et2_password2)).getText().toString().trim();
    	if ((uid.equals(""))||(pwd.equals(""))||(!pwd.equals(pwd2)))
    	{
    		Toast.makeText(getApplicationContext(), "Your password did not match，please try again!", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	clientThread.sendMessage("R"+uid+","+pwd);  
    }

	public void btn_cancel(View v) {
		finish();
	}
}
