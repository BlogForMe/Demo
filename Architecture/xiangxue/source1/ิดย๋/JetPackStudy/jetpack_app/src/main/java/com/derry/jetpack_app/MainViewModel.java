package com.derry.jetpack_app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// 管理 UI Model Data
public class MainViewModel extends /*ViewModel 没有环境 */ AndroidViewModel {

    // 我的UI数据 是什么 ？

    // 传统方式的 数据
    // private String phoneInfo = ""; 无法做到感应


    private MutableLiveData<String> phoneInfo;

    private Context context;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context =  application;
    }

    // 暴露数据  UI 数据
    public MutableLiveData<String> getPhoneInfo() {
        if (phoneInfo == null) {
            phoneInfo = new MutableLiveData<>();
            // 默认值
            phoneInfo.setValue("");
        }
        return phoneInfo;
    }

    /**
     * 输入
     * @param number
     */
    public void appendNumber(String number) {
        phoneInfo.setValue(phoneInfo.getValue() + number);;
    }

    /**
     * 删除
     */
    public void backspaceNumber() {
        int length = phoneInfo.getValue().length();
        if (length > 0) {
            phoneInfo.setValue(phoneInfo.getValue().substring(0, phoneInfo.getValue().length() - 1));
        }
    }

    /**
     * 清空
     */
    public void clear() {
        phoneInfo.setValue("");;
    }

    // 要有环境 Context
    public void  callPhone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneInfo.getValue()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
