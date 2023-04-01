package com.example.hook.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.RecoverableSecurityException;
import android.os.Handler;

import com.example.hook.activity.hook01.EvilInstrumentation;
import com.example.hook.activity.hook02.HookHandler;
import com.example.hook.activity.hook03.HCallback;
import com.example.hook.utils.Reflect;

import java.lang.reflect.Proxy;

public class HookHelper {

    /**
     * 这里hook指定的activity对象的mInstrumentation，替换成我们自己的EvilInstrumentation
     * 【注意：这种Hook的方式有个很大的缺点——只针对于当前Activity生效，因为它只修改了当前Activity实例的mInstrumentation字段。
     * 在BaseActivity中调用这段hook代码，继承自BaseActivity的类对象的mInstrumentation都会被替换】
     *
     * @param activity
     */
    public static void hookActivity(Activity activity) {
        Instrumentation mInstrumentation = Reflect.on(activity).get("mInstrumentation");
        Reflect.on(activity).set("mInstrumentation", new EvilInstrumentation(mInstrumentation));
    }

    public static void hookAMN() {

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
    public static void hookActivityThreadHandler() {
        Object activityThread = Reflect.on("android.app.ActivityThread").get("sCurrentActivityThread");
        Handler mH = Reflect.on(activityThread).get("mH");
        /*这里的mH因为是ActivityThread的内部类，所以类型是android.app.ActivityThread$H
         自定义的Handler继承android.os.Handler和mH的类型不一样，所以替换不了，但是观察Handler源码发现
         可以替换mCallback来拦截后面的handleMessage(msg);方法，从而达到拦截mH的handleMessage(msg);方法的目的
         public void dispatchMessage(@NonNull Message msg) {
             if (msg.callback != null) {
                handleCallback(msg);
             } else {
                if (mCallback != null) {
                     if (mCallback.handleMessage(msg)) {
                     return;
                 }
             }
             handleMessage(msg);
             }
         }
         */
        Reflect.on(mH).set("mCallback", new HCallback(mH));
    }

}
