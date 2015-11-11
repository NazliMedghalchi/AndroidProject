package com.example.nazli.imessaging;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyServiceCheckTxt extends Service {
    public MyServiceCheckTxt() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
