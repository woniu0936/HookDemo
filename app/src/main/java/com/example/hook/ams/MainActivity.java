package com.example.hook.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // 这个方法比onCreate调用早; 在这里Hook比较好.
    @Override
    protected void attachBaseContext(Context newBase) {
        HookHelper.hookActivityManager();
        HookHelper.hookPackageManager(newBase);
        super.attachBaseContext(newBase);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_hook).setOnClickListener(v -> {
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