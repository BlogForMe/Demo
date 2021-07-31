package com.czt.mp3recorder.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MergeMP3Activity extends Activity implements View.OnClickListener {

    private Button mPlayBtn;
    private Button mMergeBtn;
    private EditText mPath1Et;
    private EditText mPath2Et;

    private File mFile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
        mMergeBtn = (Button) findViewById(R.id.recorder_merge_btn);
        mPlayBtn = (Button) findViewById(R.id.recorder_play_btn);
        mPath1Et = (EditText) findViewById(R.id.recorder_path_1_et);
        mPath2Et = (EditText) findViewById(R.id.recorder_path_2_et);

        mPath1Et.setText(Environment.getExternalStorageDirectory() + "/Test/5.mp3");
        mPath2Et.setText(Environment.getExternalStorageDirectory() + "/Test/5.mp3");

        mMergeBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);

    }


    private void mergeMP3(String path, String path1, String name) {
        try {
            long duration = System.currentTimeMillis();
            final String s = CaoZuoMp3Utils.heBingMp3(path, path1, name);
            Log.e("MergeMP3Activity", "merge MP3 duration：" +  (System.currentTimeMillis() - duration));
            mFile = new File(s);
            if (mFile.exists()) {
                Toast.makeText(this,  "合并成功:\t" + mFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "合并失败", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "合并失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recorder_merge_btn:
                if (!TextUtils.isEmpty(mPath1Et.getText()) && !TextUtils.isEmpty(mPath2Et.getText())) {
                    mergeMP3(mPath1Et.getText().toString(), mPath2Et.getText().toString(), "newTest");
                } else {
                    Toast.makeText(this, "地址为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.recorder_play_btn:
                if (null != mFile && mFile.exists()) {
                    Intent intent = new Intent();
                    intent.putExtra("path", mFile.getAbsolutePath());
                    intent.putExtra("MSG", Constant.PLAY_MSG);
                    intent.setClass(MergeMP3Activity.this, PlayerService.class);
                    startService(intent);
                }
                break;
        }
    }
}
