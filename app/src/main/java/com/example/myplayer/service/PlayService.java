package com.example.myplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myplayer.presenter.PlayerPresenter;

public class PlayService extends Service {

    private PlayerPresenter mPlayerPresenter;
    private static final String TAG = "PlayService";

    @Override
    public void onCreate() {
        Log.i(TAG,"->onCreate");
        if (mPlayerPresenter == null) {
            mPlayerPresenter = new PlayerPresenter();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"->IBinder");
        return mPlayerPresenter;
    }

    @Override
    public void onDestroy() {
        mPlayerPresenter = null;
    }
}
