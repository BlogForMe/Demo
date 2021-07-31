package com.xiangxue.lifecycle_app.demo1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiangxue.lifecycle_app.demo1.engine.GpsEngine;

public class PersonalActivity extends AppCompatActivity {

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
        GpsEngine.getInstance().setActive(false);
    }

}
