package com.xiangxue.lifecycle_app.demo2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiangxue.lifecycle_app.demo2.engine.GpsEngine;
import com.xiangxue.lifecycle_app.demo2.engine.ICallback;

public class LoginActivity extends AppCompatActivity {

    private ICallback iCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iCallback = new GpsEngine();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iCallback.onResumeAction();
    }

    @Override
    protected void onPause() {
        super.onPause();
        iCallback.onPauseAction();
    }

}
