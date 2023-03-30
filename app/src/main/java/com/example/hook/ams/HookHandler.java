package com.example.hook.ams;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class HookHandler implements InvocationHandler {

    private static final String TAG = "HookHandler";

    private final Object obj;

    public HookHandler(Object rawIActivityManager) {
        obj = rawIActivityManager;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "hey, baby; you are hooked!!");
        Log.d(TAG, "method:" + method.getName() + " called with args:" + Arrays.toString(args));
        return method.invoke(obj, args);
    }
}
