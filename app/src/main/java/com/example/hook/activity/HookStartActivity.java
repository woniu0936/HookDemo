package com.example.hook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.hook.activity.hook01.HookStartActivity01;
import com.example.hook.activity.hook02.HookStartActivity02;
import com.example.hook.activity.hook03.HookStartActivity03;
import com.example.hook.databinding.ActivityHookStartBinding;
import com.example.hook.utils.Utils;


public class HookStartActivity extends BaseActivity {

    private ActivityHookStartBinding mBinding;

    @Override
    protected void attachBaseContext(Context newBase) {
        HookHelper.hookActivityThreadHandler();
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHookStartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnHook01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startActivity(HookStartActivity01.class);
            }
        });

        mBinding.btnHook02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startActivity(HookStartActivity02.class);
            }
        });

        mBinding.btnHook03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startActivity(HookStartActivity03.class);
            }
        });

    }
}
