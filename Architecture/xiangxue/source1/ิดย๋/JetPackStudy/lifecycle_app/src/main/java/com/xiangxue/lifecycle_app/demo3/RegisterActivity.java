package com.xiangxue.lifecycle_app.demo3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.xiangxue.lifecycle_app.demo3.engine.GpsEngine;

// todo 方式三 ： 1.不想被入侵代码    2.解决一致性问题

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定
        getLifecycle().addObserver(GpsEngine.getInstance());
    }

}
