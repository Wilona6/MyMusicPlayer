package com.example.myplayer.presenter;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.util.Log;

import com.example.myplayer.interfaces.IPlayerController;
import com.example.myplayer.interfaces.IPlayerViewController;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerPresenter extends Binder implements IPlayerController {

    private IPlayerViewController mViewController;
    private static final String TAG = "PlayerPresenter";
    public static int sCurrentState = PLAY_STATE_STOP;
    private MediaPlayer mPlayer;
    private Timer mTimer;
    private SeekTimeTask mTimeTask;
    private int mCurrentPosition;

    @Override
    public void registerIPlayViewController(IPlayerViewController iPlayerViewController) {
        mViewController = iPlayerViewController;
    }

    @Override
    public void unregisterIPlayViewController() {
        mViewController = null;
    }

    @Override
    public void start(String url,boolean isLike) {
        if (!isLike){
            stop();
            initMediaPlayer();
            try {
                Log.i(TAG,url);
                mPlayer.setDataSource(url);
                mPlayer.prepare();
                mPlayer.start();
                startTimeTask();
                sCurrentState = PLAY_STATE_START;
            } catch (IOException e) {
                Log.i(TAG,"error:"+e.toString());
                e.printStackTrace();
            }
        }else if (sCurrentState == PLAY_STATE_PAUSE){
            mViewController.onSeekChange(mCurrentPosition);
        }
        mViewController.onPlayStateChange(sCurrentState);
    }

    @Override
    public void pauseOrResume() {
        if(sCurrentState == PLAY_STATE_START){
            if (mPlayer != null) {
                mPlayer.pause();
                sCurrentState = PLAY_STATE_PAUSE;
                stopTimeTask();
            }
        }else if(sCurrentState == PLAY_STATE_PAUSE){
            if (mPlayer != null) {
                mPlayer.start();
                sCurrentState = PLAY_STATE_START;
                startTimeTask();
            }
        }
        mViewController.onPlayStateChange(sCurrentState);
    }

    /**
     * 初始化播放器
     */
    private void initMediaPlayer() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }


    /**
     * 停止播放
     */
    @Override
    public void stop() {
        Log.i(TAG,"停止播放");
        if (mPlayer != null) {
            mPlayer.stop();
            sCurrentState = PLAY_STATE_STOP;
            if (mViewController != null) {
                mViewController.onPlayStateChange(sCurrentState);
            }
            stopTimeTask();
            mPlayer.release();
            mPlayer = null;
            mViewController.onSeekChange(0);
        }
    }

    /**
     * 开启一个TimeTask
     */
    public void startTimeTask(){
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimeTask == null) {
            mTimeTask = new SeekTimeTask();
        }
        mTimer.schedule(mTimeTask,0,50);
    }

    /**
     * 关闭TimeTask
     */
    public void stopTimeTask(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimeTask != null) {
            mTimeTask.cancel();
            mTimeTask = null;
        }
    }

    /**
     * 定义TImeTask，实时获取进度
     */
    class SeekTimeTask extends TimerTask{
        @Override
        public void run() {
            mCurrentPosition = (int) (100.1*mPlayer.getCurrentPosition()/mPlayer.getDuration());
            mViewController.onSeekChange(mCurrentPosition);
        }
    }

    @Override
    public void seekTo(int seek) {
        Log.i(TAG,seek+"");
        if (mPlayer != null) {
            int tagSeek = (int) (seek*1.0/100*mPlayer.getDuration());
            mPlayer.seekTo(tagSeek);
        }
    }
}
