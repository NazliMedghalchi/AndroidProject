package com.example.nazli.imessaging;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.widget.EditText;
import android.os.Bundle;

/**
 * Created by nazlimedghalchi on 2015-11-03.
 */
public abstract class ChatService extends BroadcastReceiver {
    Context smsContext;
    private String netStatus = Application.NETWORK_STATS_SERVICE;

//    private EditText title = (EditText) R


}
