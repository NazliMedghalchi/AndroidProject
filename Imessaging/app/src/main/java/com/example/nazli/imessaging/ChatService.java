package com.example.nazli.imessaging;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by nazlimedghalchi on 2015-11-23.
 */
public class ChatService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        String ip = "10.20.141.218";
        int port = 5554;
        String message = "run AsyncTask from service";
        Client client = new Client(ip, port, message);
        client.execute();
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
        return null;
    }
}
