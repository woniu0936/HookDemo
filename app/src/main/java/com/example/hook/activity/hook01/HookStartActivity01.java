package com.example.hook.activity.hook01;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hook.R;

/**
 * 通过hook Activity的mInstrumentation字段hook startActivity
 */
public class HookStartActivity01 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_start_01);
    }
}
