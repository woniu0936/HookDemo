package com.example.hook;

import android.app.Application;
import android.content.Context;

import com.example.hook.activity.HookHelper;

public class HookDemoApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        HookHelper.hookAMN();
        super.attachBaseContext(base);
    }

    private static HookDemoApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static HookDemoApp instance() {
        return mInstance;
    }
}
