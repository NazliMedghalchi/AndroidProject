package com.example.nazli.imessaging;

        import android.app.Activity;
        import android.app.ListFragment;
        import android.database.Cursor;
        import android.provider.ContactsContract;
        import android.widget.ListView;

/**
 * Created by nazlimedghalchi on 2015-11-03.
 */
public class ContactsList extends Activity{
        @Override
        protected void onStart() {
                super.onStart();
                DatabaseHelper db = new DatabaseHelper(this);
                db.
        }
}