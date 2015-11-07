package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.Service;
import android.content.Entity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.MediaSession;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLClientInfoException;
import java.util.List;

public class MainActivity extends Activity {

// For each instance of application that is running for users
    Thread thread = new Thread();
    private String usrname = "admin";
    private String pass = "password";
    private ArrayAdapter<Integer> adapter;
    private List<Integer> conversation_id;
    private Boolean loginStatus = false;

    // check connection to GSM
    private Boolean connected;

    // Toast or inflate error message on Incorrect useername or password
    private String error_message = "Incorrect username or password";

    Client client;
    IntentFilter intentFilter;

    // Connect to Network
//    private ConnectivityManager connect =  (ConnectivityManager)
//            this.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Client client = new Client();
        IntentFilter intentFilter = new IntentFilter("com.example.nazli.imessaging");
        Intent intent = getIntent();
//        MainActivity.this.startActivity(intent);
    }


    @Override
    protected void onStart() {
        Button signin = (Button) findViewById(R.id.signin);
        super.onStart();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    // Log in to conversation list
    public Boolean logIn() {
        EditText etUsername = (EditText) findViewById(R.id.uname);
        EditText etPassword = (EditText) findViewById(R.id.password);
        // Boolean status;

        if ((etUsername.getText().toString()).equals(usrname) && (etPassword.getText().toString()).equals(pass)
                && loginStatus == false) {
            setContentView(R.layout.list_of_conversations);
            Intent intentConversation = new Intent(this, Conversation.class);
            intentConversation.addCategory(NETWORK_STATS_SERVICE);
            Service connect = new Service() {
                @Override
                public IBinder onBind(Intent intent) {
                    return null;
                }
            };
            bindService(intentConversation, (ServiceConnection) connect, BIND_AUTO_CREATE);
            loginStatus = true;

        } else {
            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_LONG).show();
            etUsername.setText("");
            etPassword.setText("");
            loginStatus= false;
        }
        return  loginStatus;
    }


// Exit the application
    public void exit() {
        EditText etUsername = (EditText) findViewById(R.id.uname);
        EditText etPassword = (EditText) findViewById(R.id.password);

        setContentView(R.layout.activity_main);
        etUsername.setText("");
        etPassword.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the acti    on bar if it is present.
//        while (loginStatus == true) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
//        }
//        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
            //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_friedns && loginStatus == true) {
            setContentView(R.layout.list_of_contacts);
            CursorAdapter cursorAdapter;
            Cursor cursor;
            DatabaseHelper db = new DatabaseHelper(this);
            ListView convList = (ListView) findViewById(R.id.listView_conv);



        } else if (id == R.id.action_groups && loginStatus == true) {
            setContentView(R.layout.list_of_groups);
            Intent intent = new Intent(this, Groups.class);
            CursorAdapter cursorAdapter;
            Cursor cursor;
            DatabaseHelper db = new DatabaseHelper(this);
            ListView groups = (ListView) findViewById(R.id.listView_groups);

        } else if (id == R.id.conversation && loginStatus == true) {
            setContentView(R.layout.list_of_conversations);
            Intent[] appIntents = {new Intent(this, Conversation.class),
                    new Intent(this, ChatService.class),
                    new Intent(this, Groups.class),
                    new Intent(this, ContactList.class)};

            MainActivity.this.startActivities(appIntents);

        } else if (id == R.id.logOut) {
            setContentView(R.layout.activity_main);
            exit();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause(){
        super.onPause();
        // TODO: 2015-11-06 unregister bCastReceiver
        unregisterReceiver(new Client());

    }

//        Register BroadcastReceiver
//    http://hmkcode.com/android-sending-receiving-custom-broadcasts/
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(new Client(), new IntentFilter("com.example.nazli.imessaging.RECEIVE_SMS"));
    }


    }
