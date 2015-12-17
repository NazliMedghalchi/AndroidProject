package com.example.nazli.imessaging;

        import android.app.Activity;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.provider.ContactsContract;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;
        import android.widget.TextView;

/**
 * Created by nazlimedghalchi on 2015-11-03.
 */
public class ContactsList extends Activity{

        ContactsContract contactsContract = new ContactsContract();
        ListView accountsList = (ListView) findViewById(R.id.contacts);
        public static final String SENDER = "sender";

        @Override
        public void onCreate(Bundle savedInstance) {
                super.onCreate(savedInstance);
                setContentView(R.layout.list_of_contacts);
                showAllAccounts(getApplicationContext());

        }
        // populates all accounts
        public void showAllAccounts(Context c){
                DatabaseHelper db = new DatabaseHelper(this);
                Cursor cursorAdapter;
                String[] fromDB = new String[] {db.username, db.user_status};
                int[] toGUI = new int[] {R.id.account_name, R.id.account_status};
                cursorAdapter = db.getACCOUNTS();
                SimpleCursorAdapter simpleCursorAdapter;
                simpleCursorAdapter = new SimpleCursorAdapter(c,
                        R.layout.list_contact_selector,cursorAdapter, fromDB, toGUI, 0);
                accountsList.setAdapter(simpleCursorAdapter);
        }
        public void selectAccount(){
                accountsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                ContentValues values = new ContentValues();
                                TextView address = (TextView) findViewById(R.id.account_name);
                                values.put(SENDER, address.getText().toString());
                                startActivity(intent);
                        }
                });

        }
}