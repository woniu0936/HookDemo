package com.example.hook.activity;

import android.app.Activity;
import android.app.Instrumentation;

import com.example.hook.activity.hook01.EvilInstrumentation;
import com.example.hook.activity.hook02.HookHandler;
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

}
