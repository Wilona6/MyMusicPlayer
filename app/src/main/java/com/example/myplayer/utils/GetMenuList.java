package com.example.myplayer.utils;

import android.util.Log;

import com.example.myplayer.api.Api;
import com.example.myplayer.db.MusicList;
import com.example.myplayer.db.TopMusicList;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class GetMenuList {

    private Retrofit mRetrofit;
    private  Api mApi;
    private  Call<MusicList> mMusicListTask;
    private Call<TopMusicList> mTopMusicListTask;
    private List<String> mSongsId;
    private static final String TAG = "GetMenuList";
    public static int sCount;

    /**
     * 初始化播放菜单的音乐
     * @param count 菜单音乐的个数
     */
    public GetMenuList(int count){
        sCount = count;
        mRetrofit = RetrofitManager.getRetrofit();
        mApi = mRetrofit.create(Api.class);
    }

    /**
     * 根据关键词获得音乐列表，并将ID信息存放到数组中
     * @param keyWord 关键词
     */
    public void getMusicList(String keyWord){
        mMusicListTask = mApi.getMusicList(keyWord);
        mMusicListTask.enqueue(new Callback<MusicList>() {
            @Override
            public void onResponse(Call<MusicList> call, Response<MusicList> response) {
                if(response.code()== HttpURLConnection.HTTP_OK){
                    List<MusicList.ResultBean.SongsBean> songs = response.body().getResult().getSongs();
                    //将取出的id存放到列表中，将具体信息查询独立出来，方便本地数据库的查询
                    saveMusicListId(songs);
                    //根据列表的信息更新每一条信息的地址
                    new MenuList(mSongsId,sCount);
                }
            }

            @Override
            public void onFailure(Call<MusicList> call, Throwable t) {
                Log.i(TAG,t.toString());
            }
        });
    }

    /**
     * 根据模块ID，并将模块中音乐ID存放到数组中
     * @param topListID 模块ID
     */
    public void getTopList(String topListID){
        mTopMusicListTask = mApi.getTopMusicList(topListID);
        mTopMusicListTask.enqueue(new Callback<TopMusicList>() {
            @Override
            public void onResponse(Call<TopMusicList> call, Response<TopMusicList> response) {
                if(response.code()== HttpURLConnection.HTTP_OK){
                    List<TopMusicList.PlaylistBean.TrackIdsBean> trackIds = response.body().getPlaylist().getTrackIds();
                    saveTopListId(trackIds);
                    new MenuList(mSongsId,sCount);
                }
            }

            @Override
            public void onFailure(Call<TopMusicList> call, Throwable t) {
                Log.i(TAG,t.toString());
            }
        });
    }

    private void saveTopListId(List<TopMusicList.PlaylistBean.TrackIdsBean> trackIds) {
        //将存放数据的数组清空，防止内存泄露
        if (mSongsId != null){
            mSongsId.clear();
            mSongsId = null;
        }
        mSongsId = new ArrayList<String>();
        for(TopMusicList.PlaylistBean.TrackIdsBean trackIdsBean :trackIds){
            mSongsId.add(trackIdsBean.getId());
        }
        trackIds.clear();
        trackIds = null;
    }

    private void saveMusicListId(List<MusicList.ResultBean.SongsBean> songs) {
        //将存放数据的数组清空，防止内存泄露
        if (mSongsId != null){
            mSongsId.clear();
            mSongsId = null;
        }
        mSongsId = new ArrayList<String>();
        for (MusicList.ResultBean.SongsBean songsBean : songs){
            mSongsId.add(songsBean.getId());
        }
        songs.clear();
        songs = null;
    }
}
