package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private String usrname = "admin";
    private String pass = "password";
    private Button signin = (Button) findViewById(R.id.signin);
    private EditText username = (EditText) findViewById(R.id.uname);
    private EditText password = (EditText) findViewById(R.id.password);
    private String alertTitle = getResources().getString(R.string.alert_title);
    private String alertMessage = getResources().getString(R.string.alert_message);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void signIn (View view) {
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( (username.getText().toString()
                        == usrname) && (password.getText().toString() == pass)) {
                    setContentView(R.layout.list_of_conversations);
                } else {

//                    // Alert DialogBox
//                    AlertDialog.Builder failedLogin = new AlertDialog.Builder(appContext);
//                    // Title
//                    failedLogin.setMessage(alertTitle);
//                    failedLogin.setMessage(alertMessage);
//                    // cancel alert
//                    failedLogin.setCancelable(false);
//                    //
//                    failedLogin.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//                    AlertDialog error = failedLogin.create();
//                    error.show();
                    username.setText("");
                    password.setText("");
                }
            }
        });

    }

}
