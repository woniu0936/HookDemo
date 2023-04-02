package com.example.hook.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hook.activity.hook01.HookStartActivity01;
import com.example.hook.activity.hook02.HookStartActivity02;
import com.example.hook.activity.hook03.HookStartActivity03;
import com.example.hook.activity.hook04.HookStartActivity04;
import com.example.hook.databinding.ActivityHookStartBinding;
import com.example.hook.utils.Utils;


public class HookStartActivity extends BaseActivity {

    private ActivityHookStartBinding mBinding;

    @Override
    protected void attachBaseContext(Context newBase) {
        HookHelper.hookActivityThreadHandler();
        HookHelper.hookActivityThreadInstrumentation();
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHookStartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnHook01.setOnClickListener(view -> Utils.startActivity(HookStartActivity.this));

        mBinding.btnHook02.setOnClickListener(view -> Utils.startActivity(HookStartActivity02.class));

        mBinding.btnHook03.setOnClickListener(view -> Utils.startActivity(HookStartActivity03.class));

        mBinding.btnHook04.setOnClickListener(view -> Utils.startActivity(HookStartActivity04.class));

    }
}
