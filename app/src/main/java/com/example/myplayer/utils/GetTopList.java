package com.example.myplayer.utils;

import android.os.Message;
import android.util.Log;

import com.example.myplayer.api.Api;
import com.example.myplayer.activity.Main;
import com.example.myplayer.db.TopList;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetTopList {
    private final Retrofit mRetrofit;
    private final Api mApi;
    public static List<TopList.ListBean> sTopList;
    private static final String TAG = "GetTopList";

    public GetTopList(){
        mRetrofit = RetrofitManager.getRetrofit();
        mApi = mRetrofit.create(Api.class);
        Call<TopList> topListTask = mApi.getTopList();
        topListTask.enqueue(new Callback<TopList>() {
            @Override
            public void onResponse(Call<TopList> call, Response<TopList> response) {
                if (response.code()== HttpURLConnection.HTTP_OK){
                    sTopList = response.body().getList();
                    Message message = Message.obtain();
                    message.what = 200;
                    Main.mHandler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<TopList> call, Throwable t) {
                Log.i(TAG,"获取失败");
                Log.i(TAG,t.toString());
            }
        });
    }
}
