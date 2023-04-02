package com.example.hook.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Handler;

import com.example.hook.activity.hook01.EvilInstrumentation;
import com.example.hook.activity.hook02.HookHandler;
import com.example.hook.activity.hook03.HCallback;
import com.example.hook.activity.hook04.EvilInstrumentation04;
import com.example.hook.utils.Reflect;

import java.lang.reflect.Proxy;

/**
 * 截至现在，我们已经陆陆续续Hook了很多类，要么使用静态代理，要么使用动态代理，回顾一下：
 *
 * ·使用静态代理，只有两个类，一个是Handler.Callback，另一个是Instrumentation。参与Android系统运转的类，暴露给我们的只有这两个。
 *
 * ·使用动态代理，只有两个接口，一个是IPackageManager，另一个是IActivityManager。这符合Proxy.newProxyInstance方法的特性，它只能对接口类型的对象进行Hook。
 *
 * 再看ActivityThread的mH字段，它是H类型的，H类是不对外暴露的，所以我们没办法伪造一个H类型的对象取代mH字段；同时，H类也不是接口类型，所以Proxy.newProxyInstance是派不上用场的。
 *
 * Android系统中大部分类是不对外暴露的，能让我们做Hook的类和对象实在不多。
 *
 */
public class HookHelper {

    /**
     * 这里hook指定的activity对象的mInstrumentation，替换成我们自己的EvilInstrumentation
     * 【注意：这种Hook的方式有个很大的缺点——只针对于当前Activity生效，因为它只修改了当前Activity实例的mInstrumentation字段。
     * 在BaseActivity中调用这段hook代码，继承自BaseActivity的类对象的mInstrumentation都会被替换】
     *
     * @param activity
     */
    public static void hookActivity(Activity activity) {
        //hook Activity的mInstrumentation 只能hook住activity.startActivity()方法
        Instrumentation mInstrumentation = Reflect.on(activity).get("mInstrumentation");
        Reflect.on(activity).set("mInstrumentation", new EvilInstrumentation(mInstrumentation));
    }

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

    /**
     * hook ActivityThread的mInstrumentation类
     */
    public static void hookActivityThreadInstrumentation() {
        //hook ActivityThread的mInstrumentation 只能hook住context.startActivity()方法
        Object activityThread = Reflect.on("android.app.ActivityThread").get("sCurrentActivityThread");
        Instrumentation mInstrumentation = Reflect.on(activityThread).get("mInstrumentation");
        Reflect.on(activityThread).set("mInstrumentation", new EvilInstrumentation04(mInstrumentation));
    }

}
