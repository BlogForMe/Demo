package com.jonzhou.tinkerbugly;

import android.app.Application;

import com.tencent.bugly.Bugly;

/**
 * @author : John
 * @date : 2018/10/17
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplicationContext(), "2195904314", true);
    }
}
