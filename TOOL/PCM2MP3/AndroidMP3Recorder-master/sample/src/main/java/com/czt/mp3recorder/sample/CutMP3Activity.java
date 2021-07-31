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
import java.util.List;

public class CutMP3Activity extends Activity implements View.OnClickListener {

    private Button mPlayBtn;
    private Button mCutBtn;
    private EditText mPathEt;
    private EditText mStartEt;
    private EditText mEndEt;

    private File mFile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut);
        mCutBtn = (Button) findViewById(R.id.recorder_cut_btn);
        mPlayBtn = (Button) findViewById(R.id.recorder_play_btn);
        mPathEt = (EditText) findViewById(R.id.recorder_path_et);
        mStartEt = (EditText) findViewById(R.id.recorder_start_et);
        mEndEt = (EditText) findViewById(R.id.recorder_end_et);

        mPathEt.setText(Environment.getExternalStorageDirectory() + "/Test/5.mp3");
        mStartEt.setText("5");
        mEndEt.setText("9");

        mCutBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);

    }


    private void cultMP3(String str, String cuting_name) {
        String fenLiData = null;
        try {
            long duration = System.currentTimeMillis();
            fenLiData = CaoZuoMp3Utils.fenLiData(str);

            final List<Integer> list = CaoZuoMp3Utils.initMP3Frame
                    (fenLiData);
            if (list == null) {
                Toast.makeText(CutMP3Activity.this, "剪切失败",
                        Toast.LENGTH_SHORT).show();
            } else {
                final String path = CaoZuoMp3Utils.CutingMp3(fenLiData, cuting_name,
                        list,
                        Double.valueOf(mStartEt.getText().toString()), Double.valueOf(mEndEt.getText().toString()));
                Log.e("CutMP3Activity", "cut MP3 duration：" + (System.currentTimeMillis() - duration));
                mFile = new File(path);
                if (mFile.exists()) {
                    Toast.makeText(this, "剪切成功:\t" + mFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CutMP3Activity.this, "剪切失败",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(CutMP3Activity.this, "剪切失败",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recorder_cut_btn:
                if (!TextUtils.isEmpty(mPathEt.getText()) && !TextUtils.isEmpty(mStartEt.getText()) && !TextUtils.isEmpty(mEndEt.getText())) {
                    cultMP3(mPathEt.getText().toString(), "newTestCut");

/*                    File file = new File(Environment.getExternalStorageDirectory() + "/Test", "TestCut.mp3");
                    boolean isClipSuccess = CaoZuoMp3Utils.clip(mPathEt.getText().toString(), file.getAbsolutePath(), Integer.valueOf(mStartEt.getText().toString()), Integer.valueOf(mEndEt.getText().toString()));

                    if (isClipSuccess) {
                        Toast.makeText(this, "剪切成功:\t" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CutMP3Activity.this, "剪切失败",
                                Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    Toast.makeText(this, "地址为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.recorder_play_btn:
                if (null != mFile && mFile.exists()) {
                    Intent intent = new Intent();
                    intent.putExtra("path", mFile.getAbsolutePath());
                    intent.putExtra("MSG", Constant.PLAY_MSG);
                    intent.setClass(CutMP3Activity.this, PlayerService.class);
                    startService(intent);
                }
                break;
        }
    }
}
