package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.BufferOverflowException;
import java.util.List;

import Client.Client;

//import com.google.gson.JsonParser;

public class MainActivity extends Activity{

// For each instance of application that is running for users
    private String usrname = "admin";
    private String pass = "password";
    String ip = "10.0.2.15";
    int port = 5554;
    Boolean loginStatus = false;
    // Toast or inflate error message on Incorrect useername or password
    private String error_message = "Incorrect username or password";

    // Connect to Network
    public static String receiveServer = "Hi this is from server";
    public EditText fromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signin = (Button) findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Client client = new Client(ip, port, receiveServer);
        Toast.makeText(getApplicationContext(), "Connected to Network" , Toast.LENGTH_LONG).show();
//        fromServer.setText(receiveServer);
    }

    // Log in to conversation list
    public Boolean logIn() {
        EditText etUsername = (EditText) findViewById(R.id.uname);
        EditText etPassword = (EditText) findViewById(R.id.password);

        if ((etUsername.getText().toString()).equals(usrname) && (etPassword.getText().toString()).equals(pass)
                || loginStatus == true) {
            setContentView(R.layout.activity_chat);
            Intent intentChat = new Intent(this, ChatService.class);
            startActivity(intentChat);

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

//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.action_friends && loginStatus == true) {
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
        else if (id == R.id.conversation && loginStatus == true) {
            setContentView(R.layout.list_of_conversations);
            Intent intentConversation = new Intent(null, Conversation.class);
            startActivity(intentConversation);

        }
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
    }

//        Register BroadcastReceiver
//    http://hmkcode.com/android-sending-receiving-custom-broadcasts/
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy(){
        stopService(this.getIntent());
        super.onDestroy();
    }
}
