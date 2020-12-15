package com.example.myplayer.utils;

import android.os.Message;
import android.util.Log;

import com.example.myplayer.activity.Player;
import com.example.myplayer.db.MusicListInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class DlCRUD {

    private static final String TAG = "DlCRUD";
    private  String mUrl;
    private  File mFile;
    public static List<String> sDlList;

    public boolean getDL(File parentFile){
        sDlList = new ArrayList<String>();
        for(File file : parentFile.listFiles()){
            if (file != null) {
                sDlList.add(file.getName());
            }
        }
        return !sDlList.isEmpty();
    }

    public boolean deleteMusic(File file){
        if (file.exists()){
            file.delete();
            return true;
        }
        return false;
    }

    public void downLoadMusic(int position, File file){
        MusicListInfo info = MenuList.sMusicListInfo.get(position);
        mUrl = info.getUrl();
        mFile = file;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .get()
                .url(mUrl)
                .build();
        //用浏览器创建任务
        Call task = client.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,e.toString());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if(response.code()== HttpURLConnection.HTTP_OK){
                    downLoad(response.body());
                }
            }
        });

    }

    private void downLoad(ResponseBody body) {
        InputStream mInputStream = body.byteStream();
        Message message = Message.obtain();
        try {
            if (!mFile.getParentFile().exists()){
                mFile.getParentFile().mkdirs();
            }
            if (mFile.exists()){
                message.what = 303;
                Player.sHandler.sendMessage(message);
                return;
            }
            Log.i(TAG,mFile.getPath());
            FileOutputStream fos = new FileOutputStream(mFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = mInputStream.read(buffer))!=-1){
                fos.write(buffer,0,len);
            }
            fos.close();
            message.what = 301;
            Player.sHandler.sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,e.toString());
            message.what = 302;
            Player.sHandler.sendMessage(message);
        }
    }
}
