package com.example.hook.activity.hook01;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.hook.utils.Reflect;
import com.example.hook.utils.Utils;

public class EvilInstrumentation extends Instrumentation {

    public static final String TAG = "EvilInstrumentation";

    private Instrumentation mBase;

    public EvilInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;
    }

    /**
     * 这里有一个细节，虽然父类的execStartActivity方法是private的，无法重写，
     * 但是我们在这里定义一个和父类方法名与参数相同的execStartActivity方法，
     * 最终用EvilInstrumentation替换掉Instrumentation之后，还是会调用这个方法
     *
     * @param who
     * @param contextThread
     * @param token
     * @param target
     * @param intent
     * @param requestCode
     * @param options
     * @return
     */
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode, Bundle options) {
        try {
            Utils.logInvoke(TAG, "execStartActivity", who, contextThread, token, target, intent, requestCode, options);
            return Reflect.on(mBase).call("execStartActivity", who, contextThread, token, target, intent, requestCode, options).get();
        } catch (Exception e) {
            Log.e(TAG, "execStartActivity invoke error: " + e.getMessage());
            return null;
        }
    }

}
