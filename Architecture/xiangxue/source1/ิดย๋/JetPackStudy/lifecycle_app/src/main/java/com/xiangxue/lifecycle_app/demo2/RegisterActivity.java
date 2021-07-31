package com.xiangxue.lifecycle_app.demo2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiangxue.lifecycle_app.demo2.engine.GpsEngine;
import com.xiangxue.lifecycle_app.demo2.engine.ICallback;

// todo 方式二 ： 接口 来解决    代码被入侵了

public class RegisterActivity extends AppCompatActivity {

    private ICallback iCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iCallback = new GpsEngine();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 人类无法避免出现忘记写的情况 （架构：一致性问题 轻量级）
    }

    @Override
    protected void onPause() {
        super.onPause();
        iCallback.onPauseAction();
    }

}
