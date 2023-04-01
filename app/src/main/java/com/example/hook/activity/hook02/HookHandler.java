package com.example.hook.activity.hook02;

import com.example.hook.utils.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HookHandler implements InvocationHandler {

    public static final String TAG = "HookHandler";

    private Object mBase;

    public HookHandler(Object mBase) {
        this.mBase = mBase;
    }


    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())) {
            Utils.logInvoke(TAG, o, method, args);
        }
        return method.invoke(mBase, args);
    }
}
