package com.example.nazli.imessaging;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.MediaSession;
import android.os.Bundle;
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

    private String usrname = "admin";
    private String pass = "password";
    private ArrayAdapter<Integer> adapter;
    private List<Integer> conversation_id;
    private Boolean loginStatus = false;


    // Toast or inflate error message on Incorrect useername or password
    private String error_message = "Incorrect username or password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create ArrayAdapter and use it to bind data to listView
        adapter = new ArrayAdapter<Integer>(this, R.layout.list_of_conversations, conversation_id);


    }


//    @Override
//    public void onStop() {
//        super.onStop();
//        exit();
//    }

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
//        Boolean status;

        if ((etUsername.getText().toString()).equals(usrname) && (etPassword.getText().toString()).equals(pass)
                && loginStatus == false) {
            setContentView(R.layout.list_of_conversations);
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
//        Intent Conversation  = new

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            } else if (id == R.id.action_friedns && loginStatus == true) {

                setContentView(R.layout.list_of_contacts);
            } else if (id == R.id.action_groups && loginStatus == true) {
                setContentView(R.layout.list_of_groups);
            } else if (id == R.id.conversation && loginStatus == true) {
                setContentView(R.layout.list_of_conversations);
            } else if (id == R.id.logOut) {
                setContentView(R.layout.activity_main);
            }
            return super.onOptionsItemSelected(item);
        }


    }
