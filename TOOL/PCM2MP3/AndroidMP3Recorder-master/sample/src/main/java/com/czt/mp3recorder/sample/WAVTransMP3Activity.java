package com.czt.mp3recorder.sample;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.czt.mp3recorder.util.LameUtil;

import java.io.File;
import java.io.IOException;

public class WAVTransMP3Activity extends Activity implements View.OnClickListener {

    private Button mPlayBtn;
    private Button mTransBtn;
    private EditText mWAVPathEt;
    private EditText mMP3PathEt;

    private File mFile;
    private int inSamplerate;
    private int inChannel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_for_mp3);
        mTransBtn = (Button) findViewById(R.id.recorder_trans_btn);
        mPlayBtn = (Button) findViewById(R.id.recorder_play_btn);
        mWAVPathEt = (EditText) findViewById(R.id.recorder_wav_path_et);
        mMP3PathEt = (EditText) findViewById(R.id.recorder_mp3_path_et);

        mWAVPathEt.setText(Environment.getExternalStorageDirectory() + "/Test/5.wav");
        mMP3PathEt.setText(Environment.getExternalStorageDirectory() + "/Test/5.mp3");

        mTransBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recorder_trans_btn:
                if (!TextUtils.isEmpty(mWAVPathEt.getText()) && !TextUtils.isEmpty(mMP3PathEt.getText())) {
                    mFile = new File(mMP3PathEt.getText().toString());
//                    printMusicFormat(mWAVPathEt.getText().toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            WavFileReader reader = new WavFileReader();
                            try {
                                if (reader.openFile(mWAVPathEt.getText().toString())) {
                                    WavFileHeader wavFileHeader = reader.getmWavFileHeader();
                                    long duration = System.currentTimeMillis();
                                    LameUtil.convert(mWAVPathEt.getText().toString(), mMP3PathEt.getText().toString(), wavFileHeader.getmSampleRate());
                                    Log.e("WAVTransMP3Activity", "WAV trans MP3 duration：" +  (System.currentTimeMillis() - duration));
                                    Log.e("WAVTransMP3Activity", "WAV SampleRate：" +  wavFileHeader.getmSampleRate());
                                    Log.e("WAVTransMP3Activity", "WAV NumChannel：" +  wavFileHeader.getmNumChannel());
                                    Log.e("WAVTransMP3Activity", "WAV ByteRate：" +  wavFileHeader.getmByteRate());
                                }


                                if (mFile.exists()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(WAVTransMP3Activity.this, "转码成功:\t" + mFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(WAVTransMP3Activity.this, "转码失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            queryMusics();
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
                    intent.setClass(WAVTransMP3Activity.this, PlayerService.class);
                    startService(intent);
                }
                break;
        }
    }


    private void printMusicFormat(String musicPath) {
        try {
            MediaExtractor extractor = new MediaExtractor();
            extractor.setDataSource(musicPath);
            MediaFormat format = extractor.getTrackFormat(getAudioTrack(extractor));
            inSamplerate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
            inChannel = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);

            Log.e("WAVTransMP3Activity", "码率：" + format.getInteger(MediaFormat.KEY_BIT_RATE));
            Log.e("WAVTransMP3Activity", "轨道数:" + format.getInteger(MediaFormat.KEY_CHANNEL_COUNT));
            Log.e("WAVTransMP3Activity", "采样率：" + format.getInteger(MediaFormat.KEY_SAMPLE_RATE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int getAudioTrack(MediaExtractor extractor) {
        for (int i = 0; i < extractor.getTrackCount(); i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith("audio")) {
                return i;
            }
        }
        return -1;
    }


    private void queryMusics() {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(mFile.getAbsolutePath());
            String strDuration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long duration = Long.valueOf(strDuration);
            Log.e("WAVTransMP3Activity", "METADATA_KEY_DURATION duration：" + mFile.getAbsolutePath() + "\t\t" + duration);

            File file = new File(mWAVPathEt.getText().toString());
            mmr.setDataSource(file.getAbsolutePath());
            strDuration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            duration = Long.valueOf(strDuration);

            Log.e("WAVTransMP3Activity", "METADATA_KEY_DURATION duration：" + file.getAbsolutePath() + "\t\t" + duration);


            mmr.setDataSource(mFile.getAbsolutePath());
            long fileSize = mFile.length();
            long bitRate = Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
            duration = (fileSize*8) /(bitRate);//单位，秒
            Log.e("WAVTransMP3Activity", "METADATA_KEY_BITRATE duration：" + mFile.getAbsolutePath() + "\t\t" + duration);

            mmr.setDataSource(file.getAbsolutePath());
            fileSize = file.length();
            bitRate = Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
            duration = (fileSize*8) /(bitRate);//单位，秒
            Log.e("WAVTransMP3Activity", "METADATA_KEY_BITRATE duration：" + file.getAbsolutePath() + "\t\t" + duration);
        } catch (Exception e) {

        }

    }
}
