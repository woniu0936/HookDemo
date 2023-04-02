package com.example.hook.replace;

import static com.example.hook.replace.HookHelper.TAG;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.example.hook.utils.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HookHandler implements InvocationHandler {

    private Object mBase;

    public HookHandler(Object mBase) {
        this.mBase = mBase;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        //Hook的上半场，在启动的时候替换目标activity为占位activity
        if ("startActivity".equals(method.getName())) {
            Utils.logInvoke(TAG, o, method, args);
            // 只拦截这个方法
            // 替换参数, 任你所为;甚至替换原始Activity启动别的Activity偷梁换柱

            // 找到参数里面的第一个Intent 对象
            Intent raw;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                Object argObj = args[i];
                if (argObj instanceof Intent) {
                    index = i;
                }
            }
            raw = (Intent) args[index];

            Intent newIntent = new Intent();
            // 替身Activity的包名, 也就是我们自己的包名
            String stubPackage = raw.getComponent().getPackageName();

            //这里把我们启动的Activity换成StubActivity
            ComponentName componentName = new ComponentName(stubPackage, StubActivity.class.getName());
            newIntent.setComponent(componentName);

            //把我们要启动的原始Activity存起来
            newIntent.putExtra(HookHelper.EXTRA_TARGET_INTENT, raw);

            //替换掉Intent，达到欺骗AMS的目的
            args[index] = newIntent;

            Log.d(TAG, "hook success");
            return method.invoke(mBase, args);
        }
        return method.invoke(mBase, args);
    }
}
