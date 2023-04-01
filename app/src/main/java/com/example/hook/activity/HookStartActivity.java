package com.example.hook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hook.activity.hook01.HookHelper01;
import com.example.hook.activity.hook01.HookStartActivity01;
import com.example.hook.databinding.ActivityHookStartBinding;


public class HookStartActivity extends AppCompatActivity {

    private ActivityHookStartBinding mBinding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //必须在这里调用，attachBaseContext方法回调的时候mInstrumentation还没有赋值
        HookHelper01.hook(this);
        mBinding = ActivityHookStartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnHook01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HookStartActivity.this, HookStartActivity01.class);
                startActivity(intent);
            }
        });

    }
}
