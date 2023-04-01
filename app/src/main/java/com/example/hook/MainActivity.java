package com.example.hook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hook.R;
import com.example.hook.activity.HookStartActivity;
import com.example.hook.ams.HookHelper;
import com.example.hook.databinding.ActivityMainBinding;
import com.example.hook.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnHook.setOnClickListener(v -> {
            // 测试AMS HOOK (调用其相关方法)
            Uri uri = Uri.parse("http://wwww.baidu.com");
            Intent t = new Intent(Intent.ACTION_VIEW);
            t.setData(uri);
            startActivity(t);
        });

        mBinding.btnHookStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startActivity(HookStartActivity.class);
            }
        });

        //测试有没有hook住PMS
        getPackageManager().getPackageInstaller();

    }
}

