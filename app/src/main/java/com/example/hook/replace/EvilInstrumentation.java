package com.example.hook.replace;

import static com.example.hook.replace.HookHelper.TAG;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;

import com.example.hook.utils.Reflect;
import com.example.hook.utils.Utils;

public class EvilInstrumentation extends Instrumentation {

    private Instrumentation mBase;

    public EvilInstrumentation(Instrumentation mBase) {
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
        //恢复真身
        Intent targetIntent = intent.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
        //如果targetIntent == null，则说明是其他业务正常的intent
        if (targetIntent != null) {
            //同HCallback的方案一样，在最终启动activity的时候，替换成目标activity
            intent.setComponent(targetIntent.getComponent());
            className = targetIntent.getComponent().getClassName();
        }
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
