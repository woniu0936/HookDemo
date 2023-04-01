package com.example.hook.activity.hook01;

import android.app.Activity;
import android.app.Instrumentation;

import com.example.hook.utils.Reflect;

public class HookHelper01 {

    /**
     * 这里hook指定的activity对象的mInstrumentation，替换成我们自己的EvilInstrumentation
     * @param activity
     */
    public static void hook(Activity activity) {
        Instrumentation mInstrumentation = Reflect.on(activity).get("mInstrumentation");
        Reflect.on(activity).set("mInstrumentation", new EvilInstrumentation(mInstrumentation));
    }

}
