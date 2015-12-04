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

public class LoginActivity extends Activity {

	ClientThread clientThread;
	Handler tcpHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
        	String m=msg.obj.toString();
            if (msg.what == 0x123) {  
                if (m.equals("L1"))
                {
                	((MyApplication)getApplication()).UID=uid;
            		Intent intent = new Intent (LoginActivity.this,MainActivity.class);			
            		startActivity(intent);	
            		LoginActivity.this.finish();
                }
                else
                {
                	Toast.makeText(getApplicationContext(), "Your username and password did not match, please try again!", Toast.LENGTH_SHORT).show();
                }
            }  
            if (msg.what == 0x124) {  
            	Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
            }
        }  
    };  
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		clientThread.setHandler(tcpHandler);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_login);
		
		clientThread=((MyApplication)getApplication()).clientThread;
		clientThread.setHandler(tcpHandler);

    } 
	
	String uid="";
    public void btn_login(View v) {
    	uid = ((EditText)findViewById(R.id.et_uid)).getText().toString().trim();
    	String pwd=((EditText)findViewById(R.id.et_password)).getText().toString().trim();
    	
    	if (uid.equals("")||pwd.equals(""))
    	{
    		Toast.makeText(getApplicationContext(), "Your username and password are not matched，please try again!", Toast.LENGTH_SHORT).show();
    		return;
    	}
  		clientThread.sendMessage("L"+uid+","+pwd);  
    }
    
    public void btn_register(View v) {
		Intent intent = new Intent (LoginActivity.this,RegisterActivity.class);			
		startActivityForResult(intent,2000);	
		//LoginActivity.this.finish();
    }
    
}
