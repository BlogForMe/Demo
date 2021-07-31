package com.czt.mp3recorder.sample;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.czt.mp3recorder.NativeMP3Decoder;

import java.io.File;

public class SpeedPlayActivity extends Activity implements View.OnClickListener {

    private Button mPlayBtn;
    private EditText mMP3PathEt;

    private File mFile;

    private AudioTrack mAudioTrack;
    private int samplerate;
    private int mAudioMinBufSize;
    private NativeMP3Decoder MP3Decoder;
    private int ret;
    private short[] audioBuffer;
    private boolean mThreadFlag;
    private Thread mThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_play);
        mPlayBtn = (Button) findViewById(R.id.recorder_play_btn);
        mMP3PathEt = (EditText) findViewById(R.id.recorder_mp3_path_et);

        mMP3PathEt.setText(Environment.getExternalStorageDirectory() + "/Test/5.mp3");

        mPlayBtn.setOnClickListener(this);

        MP3Decoder = new NativeMP3Decoder();
        ret = MP3Decoder.initAudioPlayer(mMP3PathEt.getText().toString(), 0);


        startDecode();
    }

    private void startDecode() {
        if (ret == -1) {
            Log.i("conowen", "Couldn't open file '" + mMP3PathEt.getText().toString() + "'");

        } else {
            mThreadFlag = true;
            initAudioPlayer();

            audioBuffer = new short[1024 * 1024];
            mThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    while (mThreadFlag) {
                        if (null != mAudioTrack && mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_PAUSED) {
                            // ****从libmad处获取data******/
                            MP3Decoder.getAudioBuf(audioBuffer,
                                    mAudioMinBufSize);
                            if(null != mAudioTrack){
                                mAudioTrack.write(audioBuffer, 0, mAudioMinBufSize);
                            }
                        } else {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                }
            });

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recorder_play_btn:
                if (!TextUtils.isEmpty(mMP3PathEt.getText())) {
                   mFile = new File(mMP3PathEt.getText().toString());
 /*                    if (null != mFile && mFile.exists()) {
                        Intent intent = new Intent();
                        intent.putExtra("path", mFile.getAbsolutePath());
                        intent.putExtra("MSG", Constant.PLAY_MSG);
                        intent.putExtra("speed", Float.valueOf(mSetSpeedEt.getText().toString()));
                        intent.setClass(SpeedPlayActivity.this, PlayerService.class);
                        startService(intent);
                    }*/

                    if (ret == -1) {
                        Log.i("conowen", "Couldn't open file '" + mFile.getAbsolutePath() + "'");
                        Toast.makeText(getApplicationContext(),
                                "Couldn't open file '" + mFile.getAbsolutePath() + "'",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_STOPPED) {
                            //mThreadFlag = true;// 音频线程开始
                            mAudioTrack.play();
                             mThread.start();
                        } else if (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_PAUSED) {
                            //mThreadFlag = true;// 音频线程开始
                            mAudioTrack.play();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Already in play", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(this, "地址为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



    private void initAudioPlayer() {
        // TODO Auto-generated method stub
        samplerate = MP3Decoder.getAudioSamplerate();
        System.out.println("samplerate = " + samplerate);
        samplerate = samplerate / 2;
        // 声音文件一秒钟buffer的大小
        mAudioMinBufSize = AudioTrack.getMinBufferSize(samplerate,
                AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);

        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, // 指定在流的类型
                // STREAM_ALARM：警告声
                // STREAM_MUSCI：音乐声，例如music等
                // STREAM_RING：铃声
                // STREAM_SYSTEM：系统声音
                // STREAM_VOCIE_CALL：电话声音

                samplerate,// 设置音频数据的采样率
                AudioFormat.CHANNEL_CONFIGURATION_STEREO,// 设置输出声道为双声道立体声
                AudioFormat.ENCODING_PCM_16BIT,// 设置音频数据块是8位还是16位
                mAudioMinBufSize, AudioTrack.MODE_STREAM);// 设置模式类型，在这里设置为流类型
        // AudioTrack中有MODE_STATIC和MODE_STREAM两种分类。
        // STREAM方式表示由用户通过write方式把数据一次一次得写到audiotrack中。
        // 这种方式的缺点就是JAVA层和Native层不断地交换数据，效率损失较大。
        // 而STATIC方式表示是一开始创建的时候，就把音频数据放到一个固定的buffer，然后直接传给audiotrack，
        // 后续就不用一次次得write了。AudioTrack会自己播放这个buffer中的数据。
        // 这种方法对于铃声等体积较小的文件比较合适。
    }



    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mThreadFlag = false;// 音频线程暂停
        mAudioTrack.stop();
        mAudioTrack.release();// 关闭并释放资源
        MP3Decoder.closeAduioFile();
    }

}
