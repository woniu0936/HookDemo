package com.example.hook.activity.hook03;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class HCallback implements Handler.Callback {

    public static final String TAG = "HCallback";

    private Handler mBase;

    public HCallback(Handler base) {
        this.mBase = base;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        //源码中 public static final int LAUNCH_ACTIVITY = 100;
        if (msg.what == 100) {
            Log.d(TAG, "launch activity : " + msg);
        }
        mBase.handleMessage(msg);
        return true;
    }

}
