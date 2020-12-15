package com.example.myplayer.utils;

import android.os.Message;
import android.util.Log;

import com.example.myplayer.api.Api;
import com.example.myplayer.activity.Result;
import com.example.myplayer.db.MusicInfo;
import com.example.myplayer.db.MusicList;
import com.example.myplayer.db.MusicUrl;
import com.example.myplayer.db.TopMusicList;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MenuList {

    public static List<com.example.myplayer.db.MusicListInfo> sMusicListInfo;
    public static int sCount;
    private  Retrofit mRetrofit;
    private  Api mApi;
    private List<String> mSongsId;
    private  Call<MusicList> mMusicListTask;
    private Call<TopMusicList> mTopMusicListTask;
    private static final String TAG = "MenuList";
    private Call<MusicInfo> mMusicInfoTask;
    private Call<MusicUrl> mMusicUrlTask;
    private int mNum;


    /**
     * 初始化播放菜单的音乐
     * @param count 菜单音乐的个数
     */
    public MenuList(List<String> songsId,int count){
        mRetrofit = RetrofitManager.getRetrofit();
        mApi = mRetrofit.create(Api.class);
        mSongsId = songsId;
        sCount = count>mSongsId.size()?mSongsId.size():count;
        //根据id数组，更新数组对应id的详细信息
        updateInfo();
    }

    /**
     * 更新每个列表的pic地址和Url地址
     * 执行此方法必须声明count的值
     */
    private void updateInfo() {
        if (sMusicListInfo != null) {
            sMusicListInfo.clear();
            sMusicListInfo = null;
        }
        sMusicListInfo = new ArrayList<com.example.myplayer.db.MusicListInfo>();
        mNum = 0;
        for (int i = 0; i < sCount; i++) {
            com.example.myplayer.db.MusicListInfo mMusicListInfo = new com.example.myplayer.db.MusicListInfo();
            String musicId = mSongsId.get(i);
            mMusicInfoTask = mApi.getMusicInfo(musicId);
            mMusicInfoTask.enqueue(new Callback<MusicInfo>() {
               @Override
               public void onResponse(Call<MusicInfo> call, Response<MusicInfo> response) {
                   if (response.code() == HttpURLConnection.HTTP_OK){
                       mMusicListInfo.setMusicInfo(response.body().getSongs().get(0));
                       mNum +=1;
                       if(mNum == sCount){
                           Message message = Message.obtain();
                           message.what = 100;
                           Result.mHandler.sendMessage(message);
                       }
                   }
               }

               @Override
               public void onFailure(Call<MusicInfo> call, Throwable t) {
                   Log.i(TAG,t.toString());
               }
            });
            mMusicUrlTask = mApi.getMusicUrl(musicId);
            mMusicUrlTask.enqueue(new Callback<MusicUrl>() {
               @Override
               public void onResponse(Call<MusicUrl> call, Response<MusicUrl> response) {
                   if (response.code() == HttpURLConnection.HTTP_OK){
                       mMusicListInfo.setUrl(response.body().getData().get(0).getUrl());
                   }
               }

               @Override
               public void onFailure(Call<MusicUrl> call, Throwable t) {
                   Log.i(TAG,t.toString());
               }
            });
            sMusicListInfo.add(mMusicListInfo);
        }
    }
}
