package com.example.hook.ams;

import android.content.Context;
import android.content.pm.PackageManager;

import com.example.hook.utils.RefInvoke;

import java.lang.reflect.Proxy;


public class HookHelper {

    /**
     * hook ams
     */
    public static void hookActivityManager() {
        try {
            //获取AMN的gDefault单例，gDefault是静态的
            Object gDefault = RefInvoke.getStaticFieldObject("android.app.ActivityManagerNative", "gDefault");
            //gDefault是一个android.util.Singleton对象，我们取出这个单例里面的mInstance字段, IActivityManager类型
            Object rawIActivityManager = RefInvoke.getFieldObject("android.util.Singleton", gDefault, "mInstance");
            //创建这个iActivityManagerInterface，然后替换这个字段，让我们的代理对象帮忙干活
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerInterface},
                    new HookHandler(rawIActivityManager));
            //把Singleton的mInstance替换为proxy
            RefInvoke.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hook pms
     * PMS是系统服务，是没办法进行Hook的，这里是能修改他在Android App进程中的代理对象
     *
     * @param context
     */
    public static void hookPackageManager(Context context) {
        try {
            //获取全局的ActivityThread对象
            Object currentActivityThread = RefInvoke.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");

            //获取ActivityThread里面原始的sPackageManager对象
            Object sPackageManager = RefInvoke.getFieldObject("android.app.ActivityThread", currentActivityThread, "sPackageManager");

            //准备好代理对象，用来替换原始对象
            Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iPackageManagerInterface},
                    new HookHandler(sPackageManager));

            //替换掉ActivityThread里面的sPackageManager对象
            RefInvoke.setFieldObject("android.app.ActivityThread", sPackageManager, "sPackageManager", proxy);

            //替换掉ApplicationPackageManager里面的mPM对象
            PackageManager pm = context.getPackageManager();
            RefInvoke.setFieldObject(pm, "mPM", proxy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
