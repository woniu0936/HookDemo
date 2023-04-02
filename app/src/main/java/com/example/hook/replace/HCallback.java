package com.example.hook.replace;

import static com.example.hook.replace.HookHelper.TAG;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hook.utils.Reflect;

public class HCallback implements Handler.Callback {

    private Handler mBase;

    public HCallback(Handler base) {
        this.mBase = base;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        //源码中 public static final int LAUNCH_ACTIVITY = 100;
        if (msg.what == 100) {
            handleLaunchActivity(msg);
        }
        mBase.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(@NonNull Message msg) {
        Log.d(TAG, "handle launch activity : " + msg);
        //简单起见，这里直接取出
        Object obj = msg.obj;

        //把替身恢复成真身
        Intent intent = Reflect.on(obj).get("intent");
        Intent targetIntent = intent.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
        if (targetIntent != null) {
            intent.setComponent(targetIntent.getComponent());
        }
    }

}
