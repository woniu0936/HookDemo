package com.example.hook;

import android.app.Application;

public class HookDemoApp extends Application {

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
