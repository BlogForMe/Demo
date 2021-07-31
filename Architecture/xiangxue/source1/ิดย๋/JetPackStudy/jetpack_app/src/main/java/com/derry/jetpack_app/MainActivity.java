package com.derry.jetpack_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.derry.jetpack_app.databinding.ActivityMainBinding;

/**
 * TODO 传统方式的
 *
 * 功能越多，下面的缺点就越大
 *
 * 1.Activity 或 Fragment 是大管家，代码脓肿
 *
 * 2.Activity 要处理逻辑
 *
 * 3.Activity 要处理Model数据  UI 数据， 不仅管理了 却又管不好（横竖屏切换 数据丢失）
 *
 * 4.Activity 要实时刷新UI，例如：更新TextView
 *
 * 5.Activity 如果横竖屏切换 会丢失数据（号码数据一定会丢失的）
 *
 * 6.焊死
 *
 * ...... 等等
 *
 * TODO JetPack 改造 ...
 * MainActivity 单一原则   Google JetPack   Android（第三方）
 */
public class MainActivity extends AppCompatActivity  {

    // 定义 DataBinding  setVm   setAaaaaa
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // 老版本
        // viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        // 新版本  AndroidViewModelFactory == extends AndroidViewModel
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        // 管理者的工资，建立绑定
        binding.setVm(viewModel);

        // 让感应生效
        binding.setLifecycleOwner(this);
    }
}
