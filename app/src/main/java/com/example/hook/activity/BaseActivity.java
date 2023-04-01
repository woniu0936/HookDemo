package com.example.hook.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //必须在这里调用，attachBaseContext方法回调的时候mInstrumentation还没有赋值
        HookHelper.hookActivity(this);
    }
}
