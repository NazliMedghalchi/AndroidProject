package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.NetPermission;

/**
 * Created by nazlimedghalchi on 2015-10-30.
 */
public class Conversation extends ListActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    ListView clist =  (ListView) findViewById(R.id.listView_conv);


    @Override
    public void setContentTransitionManager(TransitionManager tm) {
        super.setContentTransitionManager(tm);
        setContentView(R.layout.list_of_conversations);

        //Check if usr is connected to GSM or not
        checkConnection();
    }

    // search a contact to send new message
    //
    public void searchContact() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor contactsCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//        String phoneNumber = findViewById(R.id.contacts).toString() ;
        Cursor contacts = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,
                new String[] {ContactsContract.RawContacts._ID},
                ContactsContract.RawContacts.CONTACT_ID + "=?",
                new String[] {String.valueOf(ContactsContract.RawContacts.CONTACT_ID)},null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(Bundle.EMPTY);
    }

    public void exit() {
        EditText etUsername = (EditText) findViewById(R.id.uname);
        EditText etPassword = (EditText) findViewById(R.id.password);

        setContentView(R.layout.activity_main);
        etUsername.setText("");
        etPassword.setText("");
    }

    @Override
    protected void onDestroy() {
        exit();
        super.onResume();
    }




    public void checkConnection(){
        Context context;
        ConnectivityManager connection = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        Boolean connected;

        NetworkRequest networkRequest;
        ConnectivityManager.NetworkCallback networkCallback;

        //check GSM if it's available
        // Otherwise use WiFi
        // REf: http://www.programcreek.com/java-api-examples/android.net.ConnectivityManager

//          if (!networkInfo.isAvailable())
        networkInfo = connection.getNetworkInfo(connection.TYPE_WIFI);
        if (connection != null && networkInfo.isConnected()) {
            displayConversation();
        }
       if (connection == null)
           Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();

    }


    public void displayConversation() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor convDBCursor;
        CursorAdapter convAdapter;

        // create ArrayAdapter and use it to bind data to listView
//        ArrayAdapter adapter = new ArrayAdapter<Integer>(this, R.layout.list_of_conversations, );
    }

}