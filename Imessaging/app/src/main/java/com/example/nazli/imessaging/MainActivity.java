package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String usrname = "admin";
    private String pass = "password";
    private String error_message = "Incorrect username or password";

// Toast or inflate error message on Incorrect useername or password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText uname = (EditText) findViewById(R.id.uname);
        EditText password = (EditText) findViewById(R.id.password);

    }

    @Override
    protected void onStart () {
        Button signin = (Button) findViewById(R.id.signin);
        super.onStart();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

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
        else if (id == R.id.action_friedns) {
            setContentView(R.layout.list_of_friends);
        }
        else if (id == R.id.action_groups) {
            setContentView(R.layout.list_of_groups);
        }
        else if (id == R.id.conversation) {
            setContentView(R.layout.list_of_conversations);
        }
        else if (id == R.id.logOut) {
            setContentView(R.layout.activity_main);
        }

        return super.onOptionsItemSelected(item);
    }
//
    public void logIn () {
        EditText etUsername = (EditText) findViewById(R.id.uname);
        EditText etPassword = (EditText) findViewById(R.id.password);

        if  ((etUsername.getText().toString()).equals(usrname) && (etPassword.getText().toString()).equals(pass)){
            setContentView(R.layout.list_of_conversations);
        } else {
            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_LONG).show();
            etUsername.setText("");
            etPassword.setText("");
        }

    }
}