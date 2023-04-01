package com.example.hook.activity.hook04;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;

import com.example.hook.utils.Reflect;
import com.example.hook.utils.Utils;

public class EvilInstrumentation04 extends Instrumentation {

    public static final String TAG = "EvilInstrumentation04";

    private Instrumentation mBase;

    public EvilInstrumentation04(Instrumentation mBase) {
        this.mBase = mBase;
    }

    public Activity newActivity(Class<?> clazz, Context context,
                                IBinder token, Application application, Intent intent, ActivityInfo info,
                                CharSequence title, Activity parent, String id,
                                Object lastNonConfigurationInstance) throws InstantiationException,
            IllegalAccessException {
        Utils.logInvoke(TAG, this, "newActivity", clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance);
        Activity activity = Reflect.on(mBase).call("newActivity", clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance).get();
        return activity;
    }

    public Activity newActivity(ClassLoader cl, String className, Intent intent) {
        Utils.logInvoke(TAG, this, "newActivity", cl, className, intent);
        Activity activity = Reflect.on(mBase).call("newActivity", cl, className, intent).get();
        return activity;
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        Utils.logInvoke(TAG, this, "callActivityOnCreate", activity, icicle);
        Reflect.on(mBase).call("callActivityOnCreate", activity, icicle).get();
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle,
                                     PersistableBundle persistentState) {
        Utils.logInvoke(TAG, this, "callActivityOnCreate", activity, icicle, persistentState);
        Reflect.on(mBase).call("callActivityOnCreate", activity, icicle, persistentState).get();
    }

}
