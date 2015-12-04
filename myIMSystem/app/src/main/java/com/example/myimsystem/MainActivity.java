package com.example.myimsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	int cc=0;
	String uid="";
	String gid="testgroup";
	
	TextView textView_cc;
	TextView textView_gid;
	TextView textView_uid;
	TextView textView_cf;
	TextView txt1;
	Button button_send;
	Button button_sg;
	Button button_jg;
	Button button_qg;
	Button button_new;
	EditText editText1;
	
	ClientThread clientThread;
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			timer();
			handler.postDelayed(this, 1000);
		}
	};
	Handler tcpHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {
        	String m=msg.obj.toString();
            if (msg.what == 0x123) {  
    			String ty = m.substring(0, 1);
    			String ms = m.substring(1);
                if (m.equals("C0"))
                {
                	Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_SHORT).show();
                }
                if (m.equals("C1"))
                {
                	Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                }

                txt1.append("\n" + msg.obj.toString());  
            }  
            if (msg.what == 0x124) {  
                txt1.append("\n-" + msg.obj.toString());  
                textView_cf.setText("Status= Down");
            }  
            if (msg.what == 0x125) {  
                txt1.append("\n*" + msg.obj.toString());  
                textView_cf.setText("Status= Up");
                clientThread.sendMessage("U"+uid);  
            }  
        }  
    };  
	
	void timer()
	{
		if (clientThread.isConn)
		{
			cc=30;
			textView_cc.setText(String.valueOf(cc));
			return;
		}
		cc--;
		if (cc<0)
		{
			cc=30;
			new Thread(clientThread).start();  
		}
		textView_cc.setText(String.valueOf(cc));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_main);
		
		clientThread=((MyApplication)getApplication()).clientThread;
		clientThread.setHandler(tcpHandler);
		
		textView_cc = (TextView) findViewById(R.id.textView_cc);
		textView_gid = (TextView) findViewById(R.id.textView_gid);
		textView_cf = (TextView) findViewById(R.id.textView_cf);		
		textView_uid = (TextView) findViewById(R.id.textView_uid);
		txt1 = (TextView) findViewById(R.id.txt1);
		
		editText1 = (EditText) findViewById(R.id.editText1);
		button_new=(Button) findViewById(R.id.button_new);
		button_new.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
                txt1.setText("cls");
			}
		});
		button_sg=(Button) findViewById(R.id.button_sg);
		button_sg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showgroup();
				/*
                Message msg = new Message();  
                msg.what = 0x345;  
                msg.obj = "S";
                clientThread.revHandler.sendMessage(msg);  
                txt1.append("\n" + "users in group:"); 
                */
			}
		});
		button_jg=(Button) findViewById(R.id.button_jg);
		button_jg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				gid=((EditText)findViewById(R.id.et_group)).getText().toString().trim();
                Message msg = new Message();  
                msg.what = 0x345;  
                msg.obj = "J"+gid;
                clientThread.revHandler.sendMessage(msg);  
                textView_gid.setText("Group ID is: "+ gid);
                button_qg.setEnabled(true);
                button_jg.setEnabled(false);
			}
		});
		button_qg=(Button) findViewById(R.id.button_qg);
		button_qg.setEnabled(false);
		button_qg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
                Message msg = new Message();  
                msg.what = 0x345;  
                msg.obj = "Q"+gid;
                clientThread.revHandler.sendMessage(msg);  
                textView_gid.setText("Group ID is: Not Connected");
                button_qg.setEnabled(false);
                button_jg.setEnabled(true);

			}
		});
		button_send=(Button) findViewById(R.id.button_send);
		button_send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
                try {  
                	String input=editText1.getText().toString(); 
                    txt1.append("\n" + uid+": "+input); 
                    Message msg = new Message();  
                    msg.what = 0x345;  
                    msg.obj = "M"+input;  
                    clientThread.revHandler.sendMessage(msg);  
                    editText1.setText("");  
                } catch (Exception e) {  
                }  
			}
		});
        
        uid=((MyApplication)getApplication()).UID;
        textView_uid.setText("User info: "+uid);
        handler.postDelayed(runnable, 1000);
        
        clientThread.sendMessage("U"+uid);  
    } 
	
    public void btn_create(View v) {
    	String gn=((EditText)findViewById(R.id.et_group)).getText().toString().trim();
    	if (gn.equals(""))
    	{
    		Toast.makeText(getApplicationContext(), "please input group name!", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	clientThread.sendMessage("C"+gn);
    }
    
	private static final int REQUEST_GROUP = 1000;
	void showgroup() {
		Intent serverIntent = new Intent(this, GroupListActivity.class);
		startActivityForResult(serverIntent, REQUEST_GROUP);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		clientThread.setHandler(tcpHandler);
		if (resultCode == Activity.RESULT_OK) {
			String gid = data.getExtras().getString("gid");
			((EditText)findViewById(R.id.et_group)).setText(gid);
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
