package com.xiangxue.lifecycle_app.demo3.engine;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

// 角色 观察者 眼睛
public class GpsEngine implements DefaultLifecycleObserver {

    private static GpsEngine gpsEngine;

    public static GpsEngine getInstance() {
        if (gpsEngine == null) {
            synchronized (GpsEngine.class) {
                if (gpsEngine == null) {
                    gpsEngine = new GpsEngine();
                }
            }
        }
        return gpsEngine;
    }

    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        onResumeAction();
        setActive(true);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        onPauseAction();
        setActive(false);
    }

    private void onResumeAction() {
        Log.d("Derry", "onResumeAction: 开启中...");
    }

    private void onPauseAction() {
        Log.d("Derry", "onPauseAction: close中...");
    }

}
