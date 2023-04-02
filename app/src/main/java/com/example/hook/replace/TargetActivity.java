package com.example.hook.replace;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hook.R;

/**
 * 这是一个没有在AndroidManifests文件里面注册的Activity，我们通过欺骗AMS，想办法启动这个activity
 */
public class TargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
    }
}
