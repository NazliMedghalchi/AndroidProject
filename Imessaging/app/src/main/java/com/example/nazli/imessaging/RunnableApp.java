package com.example.nazli.imessaging;

import android.content.ContentValues;
import android.os.*;
import android.widget.Switch;

/**
 * Created by nazlimedghalchi on 2015-11-06.
 * The most helpful link in creating client-server design for networking is
 * http://developer.android.com/intl/ko/training/multiple-threads/define-runnable.html
 *
 * I used the developer.andoird to understand most APIs and their functionalties
 */
public class RunnableApp implements Runnable {

    Client.PendingResult pendingResult = new Client().goAsync();

    @Override
    public void run(){
 /*This approach reduces the resource competition for between the runnable object's
 * and the UI thread */
        Thread thread = new Thread();
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
    }

    }
