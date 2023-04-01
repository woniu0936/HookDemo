package com.example.hook.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.hook.HookDemoApp;

public class Utils {

    public static final void startActivity(Class clazz) {
        Intent intent = new Intent(HookDemoApp.instance(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        HookDemoApp.instance().startActivity(intent);
    }

}
