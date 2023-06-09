package com.example.hook.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.hook.HookDemoApp;

import java.lang.reflect.Method;

public class Utils {

    private static final String TAG = "HookDemoApp";

    /**
     * 通过Context启动activity
     * @param clazz
     */
    public static final void startActivity(Class clazz) {
        Intent intent = new Intent(HookDemoApp.instance(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        HookDemoApp.instance().startActivity(intent);
    }

    /**
     * 通过Activity启动activity
     * @param activity
     */
    public static final void startActivity(Activity activity) {
        Intent intent = new Intent(HookDemoApp.instance(), activity.getClass());
        activity.startActivity(intent);
    }

    public static void logInvoke(Object o, Method method, Object[] args) {
        logInvoke(TAG, o, method, args);
    }

    public static void logInvoke(String tag, Object o, Method method, Object[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("object: ")
                .append(o.getClass().getName())
                .append(", ")
                .append("method: ")
                .append(method.getName());
        if (args != null) {
            for (Object arg : args) {
                if (arg != null) {
                    sb.append(", ")
                            .append(arg.getClass().getSimpleName())
                            .append(": ")
                            .append(arg);
                }
            }
        }
        Log.d(tag, sb.toString());
    }

    public static void logInvoke(Object o, String methodName, Object... args) {
        logInvoke(TAG, o, methodName, args);
    }

    public static void logInvoke(String tag, Object o, String methodName, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("object: ")
                .append(o.getClass().getName())
                .append(", ")
                .append("method: ")
                .append(methodName);
        if (args != null) {
            for (Object arg : args) {
                if (arg != null) {
                    sb.append(", ")
                            .append(arg.getClass().getSimpleName())
                            .append(": ")
                            .append(arg);
                }
            }
        }
        Log.d(tag, sb.toString());
    }
}
