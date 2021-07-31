
package com.czt.mp3recorder.sample;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button mRecorderBtn;
    private Button mMergeBtn;
    private Button mCutBtn;
    private Button mWAVTransMP3Btn;
    private Button mMP3TransWAVBtn;
    private Button mSpeedPlayBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecorderBtn = (Button) findViewById(R.id.recorder_mp3_btn);
        mMergeBtn = (Button) findViewById(R.id.merge_mp3_btn);
        mCutBtn = (Button) findViewById(R.id.cut_mp3_btn);
        mWAVTransMP3Btn = (Button) findViewById(R.id.wav_trans_mp3_btn);
        mMP3TransWAVBtn = (Button) findViewById(R.id.wav_trans_wav_btn);
        mSpeedPlayBtn = (Button) findViewById(R.id.speed_play_btn);

        mRecorderBtn.setOnClickListener(this);
        mMergeBtn.setOnClickListener(this);
        mCutBtn.setOnClickListener(this);
        mWAVTransMP3Btn.setOnClickListener(this);
        mMP3TransWAVBtn.setOnClickListener(this);
        mSpeedPlayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.recorder_mp3_btn:
                intent = new Intent(MainActivity.this, RecorderMP3Activity.class);
                break;
            case R.id.merge_mp3_btn:
                intent = new Intent(MainActivity.this, MergeMP3Activity.class);
                break;
            case R.id.cut_mp3_btn:
                intent = new Intent(MainActivity.this, CutMP3Activity.class);
                break;
            case R.id.wav_trans_mp3_btn:
                intent = new Intent(MainActivity.this, WAVTransMP3Activity.class);
                break;
            case R.id.wav_trans_wav_btn:
                intent = new Intent(MainActivity.this, MP3TransWAVActivity.class);
                break;
            case R.id.speed_play_btn:
                intent = new Intent(MainActivity.this, SpeedPlayActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }



/*    private void cultMP3(String str, String cuting_name,) {
        String fenLiData = null;
        try {
            fenLiData = CaoZuoMp3Utils.fenLiData(str);

            final List<Integer> list = CaoZuoMp3Utils.initMP3Frame
                    (fenLiData);
            if (list == null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "剪切失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                final String path = CaoZuoMp3Utils.CutingMp3(fenLiData, cuting_name,
                        list,
                        0, 5);
                final File file = new File(fenLiData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeMP3(String path, String path1, String name) {
        try {
            final String s = CaoZuoMp3Utils.heBingMp3(path, path1, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
