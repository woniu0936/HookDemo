package com.example.hook.ams;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hook.databinding.ActivityHookBinding;

public class HookActivity extends AppCompatActivity {

    // 这个方法比onCreate调用早; 在这里Hook比较好.
    @Override
    protected void attachBaseContext(Context newBase) {
        HookHelper.hookActivityManager();
        HookHelper.hookPackageManager(newBase);
        super.attachBaseContext(newBase);
    }

    private ActivityHookBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHookBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnHook.setOnClickListener(view -> {
            // 测试AMS HOOK (调用其相关方法)
            Uri uri = Uri.parse("http://wwww.baidu.com");
            Intent t = new Intent(Intent.ACTION_VIEW);
            t.setData(uri);
            startActivity(t);
        });

        //测试有没有hook住PMS
        getPackageManager().getPackageInstaller();

    }
}
