package com.example.hook.replace;

import android.app.Instrumentation;
import android.os.Handler;

import com.example.hook.utils.Reflect;

import java.lang.reflect.Proxy;

public class HookHelper {

    public static final String TAG = "hook_replace_activity";

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void attachBaseContext() {
        //hook上半场，在告诉AMS启动activity的时候，将目标activity替换成占位activity
        hookAMN();
        //hook下半场，在AMS告诉app启动activity的时候，将占位activity恢复成目标activity，如下两个方案做的是同一件事
//        hookActivityThread01();
        hookActivityThread02();
    }

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
    public static void hookActivityThread01() {
        Object activityThread = Reflect.on("android.app.ActivityThread").get("sCurrentActivityThread");
        Handler mH = Reflect.on(activityThread).get("mH");
        Reflect.on(mH).set("mCallback", new HCallback(mH));
    }

    /**
     * hook ActivityThread的mInstrumentation类
     */
    public static void hookActivityThread02() {
        //hook ActivityThread的mInstrumentation 只能hook住context.startActivity()方法
        Object activityThread = Reflect.on("android.app.ActivityThread").get("sCurrentActivityThread");
        Instrumentation mInstrumentation = Reflect.on(activityThread).get("mInstrumentation");
        Reflect.on(activityThread).set("mInstrumentation", new EvilInstrumentation(mInstrumentation));
    }

}
