package com.xiangxue.lifecycle_app.demo1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// todo 方式一 ： 生命周期 方法存在 严重 一致性问题 严重级别
import com.xiangxue.lifecycle_app.demo1.engine.GpsEngine;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GpsEngine.getInstance().onResumeAction();
        GpsEngine.getInstance().setActive(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GpsEngine.getInstance().onPauseAction();
        // 开发者忘记些这行代码了，怎么办，Bug   == 设计有问题，程序员责任50%
    }

}
