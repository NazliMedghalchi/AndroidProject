package com.example.nazli.imessaging;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by nazlimedghalchi on 2015-10-30.
 */

//http://stackoverflow.com/questions/4540754/dynamically-add-elements-to-a-listview-android
//    list of lists for values of listView Items

//    http://stackoverflow.com/questions/4540754/dynamically-add-elements-to-a-listview-android
//    Handle dynamic insertion to listView

public class Conversation extends ListActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    ListView clist = (ListView) findViewById(R.id.listView_conv);
    Button addNewconv;


    ArrayList<String> convListItem = new ArrayList<>();
    //    adapter to bind data to
    ArrayAdapter<String> adapter;

    //    Item to be added to listItems
    String title = ((TextView) findViewById(R.id.threadSender)).toString();
    String time = ((TextView) findViewById(R.id.convTime)).toString();
    String text = ((TextView) findViewById(R.id.convFirstLine)).toString();
    Intent intent;

    @Override
    public void onCreate(Bundle Main) {
        super.onCreate(Main);
        setContentView(R.layout.list_of_conversations);

        addNewconv = (Button) findViewById(R.id.add_conv_btn);
        intent = getIntent();

// The adapter passed through Conversation by MainActivity
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, convListItem);
        setListAdapter(adapter);

        checkConnection();
        Button addConv = (Button) findViewById(R.id.add_conv_btn);
        addConv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newConv();
            }
        });

        Button addBTN = (Button) findViewById(R.id.btn_send);
        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newConversation();
            }
        });
       /*
       * onClick on every Item of conversation list view the item which is the entire conversation
       * will open as in ChatService activity and related layout
       * The intentfilter and context should be declared
       * */
        clist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = getSelectedItemPosition();
                String title = getString(position);
                TextView convID = (TextView) findViewById(R.id.threadSender);
                Intent itemIntent = new Intent(intent);
                itemIntent.hasExtra(getString(position));
                displayConversation(convID);
            }
        });
    }


    @Override
        public void setContentTransitionManager (TransitionManager tm){
            super.setContentTransitionManager(tm);
            setContentView(R.layout.list_of_conversations);

            Transition transition = TransitionInflater.from(this).
                    inflateTransition(R.transition.fade_transition);

        }


        @Override
        protected void onPause () {
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

    // search a contact to send new message
    // Display List of contacts from phone
    public void searchContact() { // TODO: 2015-11-04 get from DB
        DatabaseHelper db = new DatabaseHelper(this);
        EditText smsreceiver;
        EditText receivedTxt = (EditText) findViewById(R.id.listView_chat);
        Cursor contactsCursor = getContentResolver().query(ContactsContract.
                CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//        String phoneNumber = findViewById(R.id.contacts).toString() ;
        Cursor contacts = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID},
                ContactsContract.RawContacts.CONTACT_ID + "=?",
                new String[]{String.valueOf(ContactsContract.RawContacts.CONTACT_ID)}, null);
        receivedTxt.setText(contacts.toString()); //// TODO: 2015-11-05
    }

    public void newConversation() {
        //    items from layout needed for new conversation and transition
        Transition transition = new Fade();
        transition.addTarget(R.layout.new_con_fragment);
        ChatService chatService = new ChatService();

        ContentValues values = new ContentValues();
        values.put("", title);
        values.put("", time);
        values.put("", text);
        try {
            Intent chat = new Intent(Intent.ACTION_VIEW);
            chat.setType("vnd.android-dir/mms-sms");
            startActivity(chat);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Failed to send - Please try again!", Toast.LENGTH_LONG);
            e.printStackTrace();
        }

        Intent intent = getIntent();
        startActivity(intent);
    }


    public void checkConnection() {
        Context context;
        ConnectivityManager connection = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        Boolean connected;

        NetworkRequest networkRequest;
        ConnectivityManager.NetworkCallback networkCallback;

        //check GSM if it's available
        // Otherwise use WiFi
        // REf: http://www.programcreek.com/java-api-examples/android.net.ConnectivityManager

        if (!networkInfo.isAvailable())
            networkInfo = connection.getNetworkInfo(connection.TYPE_WIFI);
        if (connection != null && networkInfo.isConnected()) {
            displayConversation();
        }
        if (connection == null)
            Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();
    }

    public void displayConversation(String convID) {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor;
        CursorAdapter convAdapter = CursorAdapter;
        String convTitle;
        Integer convId;
        ArrayList<Integer> members = new ArrayList<>();
        // show conversations
        final Cursor convDBCursor = db.showConversation();
        String firstLineTxt;
        String lastTxtTime;
        convTitle = (findViewById(R.id.threadSender)).toString();

        ContentValues values = new ContentValues();


        // TODO: 2015-11-06 add values to contentValues to intent for next running activivty
//        values.put(); todo values.extra() for intent
        // create ArrayAdapter and use it to bind data to listView
//        ArrayAdapter adapter = new ArrayAdapter<Integer>(this, R.layout.list_of_conversations, );
    }

//    //Check if usr is connected to GSM or not
//    checkConnection();
//
//    // populate receiver phone number
//    searchContact();

    public void newConv() {
        Intent intent = new Intent(getApplicationContext(), ChatService.class);
        convListItem.add(title);
        convListItem.add(time);
        convListItem.add(text);
        adapter.notifyDataSetChanged();
        startActivity(intent);
    }

    public void sendMessage(){

    }
}