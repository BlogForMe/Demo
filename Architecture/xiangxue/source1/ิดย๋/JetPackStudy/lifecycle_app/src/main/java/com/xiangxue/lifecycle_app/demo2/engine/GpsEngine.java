package com.xiangxue.lifecycle_app.demo2.engine;

import android.util.Log;

public class GpsEngine implements ICallback {

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

    public void onResumeAction() {
        Log.d("Derry", "onResumeAction: 开启中...");
    }

    public void onPauseAction() {
        Log.d("Derry", "onPauseAction: close中...");
    }

}
