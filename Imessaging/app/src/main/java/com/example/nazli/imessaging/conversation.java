package com.example.nazli.imessaging;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.NetPermission;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by nazlimedghalchi on 2015-10-30.
 */
public class Conversation extends ListActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    ListView clist =  (ListView) findViewById(R.id.listView_conv);

//    list of lists for values of listView Items
    ArrayList<String> convListItem = new ArrayList<String>();
//    adapter to bind data to 
    ArrayAdapter<String>


    @Override
    public void setContentTransitionManager(TransitionManager tm) {
        super.setContentTransitionManager(tm);
        setContentView(R.layout.list_of_conversations);

        //Check if usr is connected to GSM or not
        checkConnection();

        // populate receiver phone number
        searchContact();
    }

    // search a contact to send new message
    // Display List of contcts from phone
    public void searchContact() { // TODO: 2015-11-04 get from DB
        DatabaseHelper db = new DatabaseHelper(this);
        EditText smsreceiver;

        Cursor contactsCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//        String phoneNumber = findViewById(R.id.contacts).toString() ;
        Cursor contacts = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,
                new String[] {ContactsContract.RawContacts._ID},
                ContactsContract.RawContacts.CONTACT_ID + "=?",
                new String[] {String.valueOf(ContactsContract.RawContacts.CONTACT_ID)},null);
        smsreceiver.setText(); //// TODO: 2015-11-05


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
        unregisterReceiver(); //todo destroy defined receiver
        exit();
        super.onResume();
    }

//    Register BroadcastReceiver
//    http://hmkcode.com/android-sending-receiving-custom-broadcasts/
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(new Server(), new IntentFilter("com.example.nazli.imessaging.ACTION_") {
            @Override
            public void onReceive(Context context, Intent intent) {


            }
        });
    }
    public void newConversation () {

    }


    public void displayChat(){

        Inflater inflater = new Inflater()
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
        Cursor cursor;
        CursorAdapter convAdapter = new CursorAdapter().newView(getApplicationContext(), cursor, null);

        String convTitle;
        Integer convId;
        ArrayList<Integer> members = new ArrayList<Integer>();

        // show conversations
        final Cursor convDBCursor = db.showConversation();

        String firstLineTxt;
        String lastTxtTime;



        convTitle = (findViewById(R.id.title_of_conv)).toString();

        ContentValues values = new ContentValues();
        values.put();
        // create ArrayAdapter and use it to bind data to listView
//        ArrayAdapter adapter = new ArrayAdapter<Integer>(this, R.layout.list_of_conversations, );
    }

    Button addConv = (Button) findViewById(R.id.add_conv_btn);
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ChatService, null);
            db.newConversation();
        }
    }
}