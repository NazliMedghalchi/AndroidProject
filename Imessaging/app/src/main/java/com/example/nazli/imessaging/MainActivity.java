package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonParser;

public class MainActivity extends Activity {

// For each instance of application that is running for users
    private String usrname = "admin";
    private String pass = "password";
    private ArrayAdapter<Integer> adapter;
    private List<Integer> conversation_id;
    private Boolean loginStatus = false;


    // check connection to GSM
    private Boolean connected;

    // Toast or inflate error message on Incorrect useername or password
    private String error_message = "Incorrect username or password";

    // Connect to Network

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Connected to Network", Toast.LENGTH_LONG);
        HTTPgetRequest httPgetRequest = new HTTPgetRequest();
        httPgetRequest.execute();

        Button signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }

    // Log in to conversation list
    public Boolean logIn() {
        EditText etUsername = (EditText) findViewById(R.id.uname);
        EditText etPassword = (EditText) findViewById(R.id.password);
        // Boolean status;

        if ((etUsername.getText().toString()).equals(usrname) && (etPassword.getText().toString()).equals(pass)
                && loginStatus == false) {
            setContentView(R.layout.list_of_conversations);
            Intent intentConversation = getIntent();
            startActivity(intentConversation, null);
            loginStatus = true;

        }
        else {
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
        // Inflate the menu; this adds items to the action on bar if it is present.
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
        }
        else if (id == R.id.action_friends && loginStatus == true) {
            setContentView(R.layout.list_of_contacts);
            Intent intentContact = new Intent(this, ContactList.class);
            startActivity(intentContact);
        }
        else if (id == R.id.action_groups && loginStatus == true) {
            setContentView(R.layout.list_of_groups);
            Intent intentGroup = new Intent(this, Groups.class);
            try {
                startActivity(intentGroup);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
//        else if (id == R.id.conversation && loginStatus == true) {
//            setContentView(R.layout.list_of_conversations);
//            Intent[] appIntents = {new Intent(getApplicationContext(), Conversation.class),
//                    new Intent(this, ChatService.class),
//                    new Intent(this, Groups.class),
//                    new Intent(this, ContactList.class)};
//
//            MainActivity.this.startActivities(appIntents);
//
//        }
        else if (id == R.id.logOut) {
            super.onResume();
            setContentView(R.layout.activity_main);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            exit();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause(){
        super.onPause();
        // TODO: 2015-11-06 unregister bCastReceiver
//        unregisterReceiver(new Client());

    }

//        Register BroadcastReceiver
//    http://hmkcode.com/android-sending-receiving-custom-broadcasts/
    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver(new Client(), new IntentFilter("com.example.nazli.imessaging.RECEIVE_SMS"));
    }
    @Override
    public void onDestroy(){
        stopService(this.getIntent());
        super.onDestroy();
    }
}
