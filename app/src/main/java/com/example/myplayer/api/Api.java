package com.example.myplayer.api;


import com.example.myplayer.db.MusicInfo;
import com.example.myplayer.db.MusicList;
import com.example.myplayer.db.MusicUrl;
import com.example.myplayer.db.TopList;
import com.example.myplayer.db.TopMusicList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/search")
    Call<MusicList> getMusicList(@Query("keywords") String keywords);

    @GET("/song/detail")
    Call<MusicInfo> getMusicInfo(@Query("ids") String ids);

    @GET("/song/url")
    Call<MusicUrl> getMusicUrl(@Query("id") String id);

    @GET("/toplist")
    Call<TopList> getTopList();

    @GET("/playlist/detail")
    Call<TopMusicList> getTopMusicList(@Query("id") String id);


    //@GET("/comment/music")
    //Call<CommentList> getComment(@Query("id") String id);
}
