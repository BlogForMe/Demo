package com.czt.mp3recorder.sample;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.czt.mp3recorder.util.LameUtil;

import java.io.File;
import java.io.IOException;

public class MP3TransWAVActivity extends Activity implements View.OnClickListener {

    private Button mPlayBtn;
    private Button mTransBtn;
    private EditText mWAVPathEt;
    private EditText mMP3PathEt;

    private File mFile;
    private File mFilePCM;
    private PcmToWavUtil mPcmToWavUtil;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_for_wav);
        mTransBtn = (Button) findViewById(R.id.recorder_trans_btn);
        mPlayBtn = (Button) findViewById(R.id.recorder_play_btn);
        mWAVPathEt = (EditText) findViewById(R.id.recorder_wav_path_et);
        mMP3PathEt = (EditText) findViewById(R.id.recorder_mp3_path_et);

        mWAVPathEt.setText(Environment.getExternalStorageDirectory() + "/Test/5.wav");
        mMP3PathEt.setText(Environment.getExternalStorageDirectory() + "/Test/5.mp3");

        mTransBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);

        mPcmToWavUtil = new PcmToWavUtil();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recorder_trans_btn:
                if (!TextUtils.isEmpty(mWAVPathEt.getText()) && !TextUtils.isEmpty(mMP3PathEt.getText())) {
                    mFile = new File(mWAVPathEt.getText().toString());
                    mFilePCM = new File(Environment.getExternalStorageDirectory() + "/Test", "Rec_001.pcm");


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                long duration = System.currentTimeMillis();
                                CaoZuoMp3Utils.fenLiData(mMP3PathEt.getText().toString(), mFilePCM.getAbsolutePath());

                                if (mFilePCM.exists()) {
                                    mPcmToWavUtil.pcmToWav(mFilePCM.getAbsolutePath(), mFile.getAbsolutePath());
                                    Log.e("MP3TransWAVActivity", "MP3 trans WAV duration：" +  (System.currentTimeMillis() - duration));
                                    if (mFile.exists()) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MP3TransWAVActivity.this, "转码成功:\t" + mFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MP3TransWAVActivity.this, "转码失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MP3TransWAVActivity.this, "转码失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MP3TransWAVActivity.this, "转码失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(this, "地址为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.recorder_play_btn:
                if (null != mFile && mFile.exists()) {
                    Intent intent = new Intent();
                    intent.putExtra("path", mFile.getAbsolutePath());
                    intent.putExtra("MSG", Constant.PLAY_MSG);
                    intent.setClass(MP3TransWAVActivity.this, PlayerService.class);
                    startService(intent);
                }
                break;
        }
    }


}
