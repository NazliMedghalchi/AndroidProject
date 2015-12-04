package com.example.myimsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GroupListActivity extends Activity {

	ClientThread clientThread;
	Handler tcpHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
        	String m=msg.obj.toString();
            if (msg.what == 0x123) {  
    			String ty = m.substring(0, 1);
    			String ms = m.substring(1);
                if (ty.equals("S"))
                {
                	mGroupArrayAdapter.add(ms);
                }
            }  
            if (msg.what == 0x124) {  
            	Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
            }
        }  
    };  
	
    private ArrayAdapter<String> mGroupArrayAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_grouplist);
		
		mGroupArrayAdapter = new ArrayAdapter<String>(this,R.layout.group_name);
        ListView pairedListView = (ListView) findViewById(R.id.list_group);
        pairedListView.setAdapter(mGroupArrayAdapter);
        pairedListView.setOnItemClickListener(mGroupClickListener);

		clientThread=((MyApplication)getApplication()).clientThread;
		clientThread.setHandler(tcpHandler);
		
		clientThread.sendMessage("S");
    } 
	
    private OnItemClickListener mGroupClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long id) {
            if(id<0) {
                return;
            }
            int rid=(int)id;
            String gid=mGroupArrayAdapter.getItem(rid);
            Intent intent = new Intent();
            Bundle data = new Bundle();
            data.putString("gid", gid);
            intent.putExtras(data);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
    
}
