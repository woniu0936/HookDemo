package com.example.hook.replace;

import android.os.Handler;

import com.example.hook.utils.Reflect;

import java.lang.reflect.Proxy;

public class HookHelper {

    public static final String TAG = "hook_replace_activity";

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    /**
     * hook AMN
     */
    public static void hookAMN() {
        //hook ActivityManagerNative可以同时hook住activity.startActivity()方法和context.startActivity()方法
        Object gDefault = Reflect.on("android.app.ActivityManagerNative").get("gDefault");
        Object mInstance = Reflect.on(gDefault).get("mInstance");
        Class<?> iActivityManagerInterface = Reflect.on("android.app.IActivityManager").type();

        Object proxy = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{iActivityManagerInterface},
                new HookHandler(mInstance));

        Reflect.on(gDefault).set("mInstance", proxy);
    }

    /**
     * hook ActivityThread的mH对象
     */
    public static void hookActivityThread() {
        Object activityThread = Reflect.on("android.app.ActivityThread").get("sCurrentActivityThread");
        Handler mH = Reflect.on(activityThread).get("mH");
        Reflect.on(mH).set("mCallback", new HCallback(mH));
    }

}
