package com.example.hook.activity.hook02;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hook.R;
import com.example.hook.activity.BaseActivity;

/**
 * 通过hook ActivityManagerNative的getDefault()方法
 */
public class HookStartActivity02 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_start_01);
    }
}
