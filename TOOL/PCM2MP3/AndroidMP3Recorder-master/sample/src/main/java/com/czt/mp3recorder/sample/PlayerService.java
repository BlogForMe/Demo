package com.czt.mp3recorder.sample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PlayerService extends Service {


    private MediaPlayer mediaPlayer = new MediaPlayer();        //媒体播放器对象
    private String path;                        //音乐文件路径
    private boolean isPause;                    //暂停状态
    private float speed;                    //倍速


    public final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer.isPlaying()) {
            stop();
        }
        path = intent.getStringExtra("path");
        int msg = intent.getIntExtra("MSG", 0);
        speed = intent.getFloatExtra("speed", 1);
        if (msg == Constant.PLAY_MSG) {
            play(0);
        } else if (msg == Constant.PAUSE_MSG) {
            pause();
        } else if (msg == Constant.STOP_MSG) {
            stop();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 播放音乐
     *
     * @param position
     */
    public void play(int position) {
        if (speed != 1) {
/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                MediaPlay(position);
            } else {
                SoundPoolPlay();
            }*/
            SoundPoolPlay();
        } else {
            MediaPlay(position);
        }

    }

    private void MediaPlay(int position) {
        try {
            mediaPlayer.reset();//把各项参数恢复到初始状态
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();    //进行缓冲
            mediaPlayer.setOnPreparedListener(new PreparedListener(position));//注册一个监听器
            changeplayerSpeed(speed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停音乐
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    /**
     * 停止音乐
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    /**
     * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
     */
    public final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int positon;

        public PreparedListener(int positon) {
            this.positon = positon;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();    //开始播放
            if (positon > 0) {    //如果音乐不是从头播放
                mediaPlayer.seekTo(positon);
            }
        }
    }


    public void changeplayerSpeed(float speed) {
        // this checks on API 23 and up
        if (null != mediaPlayer) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                } else {
                    mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                    mediaPlayer.pause();
                }
            }
        }

    }

    private void SoundPoolPlay() {
        final SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);

        final int soundId = soundPool.load(path, 1);
        AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final float volume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
                soundPool.play(soundId, volume, volume, 1, 0, speed);
            }
        });
    }

}
