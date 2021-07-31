package com.czt.mp3recorder.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.czt.mp3recorder.MP3Recorder;

import java.io.File;
import java.io.IOException;

public class RecorderMP3Activity extends Activity {
    private MP3Recorder mRecorder;
    private File mFile;
    private EditText mPathEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);


        Button startButton = (Button) findViewById(R.id.StartButton);
        Button stopButton = (Button) findViewById(R.id.StopButton);
        Button playRecordBtn = (Button) findViewById(R.id.recorder_play_btn);
        mPathEt = (EditText) findViewById(R.id.recorder_path_et);
        mPathEt.setText(Environment.getExternalStorageDirectory() + "/Test/test1.mp3");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mFile = new File(mPathEt.getText().toString());
                    mRecorder = new MP3Recorder(mFile);
                    mRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecorder.stop();
            }
        });

        playRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFile.exists()) {
                    Intent intent = new Intent();
                    intent.putExtra("path", mFile.getAbsolutePath());
                    intent.putExtra("MSG", Constant.PLAY_MSG);
                    intent.setClass(RecorderMP3Activity.this, PlayerService.class);
                    startService(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mRecorder && mRecorder.isRecording()) {
            mRecorder.stop();
        }

    }
}
