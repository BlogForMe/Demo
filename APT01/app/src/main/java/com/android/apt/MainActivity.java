package com.android.apt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.lib_annotations.SubTypeAutoGenerate;
//https://juejin.cn/post/6925283352698159117#heading-14
@SubTypeAutoGenerate("com.android.apt.wxapi.WXEntryActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}