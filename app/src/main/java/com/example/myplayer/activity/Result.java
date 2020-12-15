package com.example.myplayer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplayer.R;
import com.example.myplayer.adapter.MusicListAdapter;
import com.example.myplayer.db.MusicListInfo;
import com.example.myplayer.utils.LikeCRUD;
import com.example.myplayer.utils.MenuList;

public class Result extends AppCompatActivity {


    private RecyclerView mRvResult;
    private MusicListAdapter mAdapter;
    private TextView mInfo;
    private ProgressBar mProgressBar;
    public static Handler mHandler;
    private String mTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        new Thread(new Runnable(){
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        });
        InitView();
    }
    private void InitView() {
        mInfo = findViewById(R.id.tv_info);
        mRvResult = findViewById(R.id.rv_result);
        mAdapter = new MusicListAdapter();
        mProgressBar = findViewById(R.id.pb_result);
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        mTag = intent.getStringExtra("Tag");
        String info = mTag +intent.getStringExtra("keyword");
        mInfo.setText(info);
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what==100){
                    /**
                     * Result数据加载完毕，开始更新UI
                     */
                    mProgressBar.setVisibility(View.GONE);
                    //UI更新搜索结果
                    update();
                    initListener();
                }
                return true;
            }
        });
    }

    //实现点击的接口
    public void initListener(){
        mAdapter.setOnMenuClickListener(new MusicListAdapter.OnMenuClickListener() {
            @Override
            public void onMenuClick(int position) {
                Intent intent = new Intent(Result.this, Player.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        mAdapter.setOnMusicClickListener(new MusicListAdapter.OnMusicClickListener() {
            @Override
            public void onMusicClick(int position) {
                if (MenuList.sMusicListInfo.get(position).getUrl()!=null){
                    Intent intent = new Intent(Result.this,Player.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }else {
                    Toast.makeText(Result.this, "付费歌曲，不支持试听", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAdapter.setOnLongClickListener(new MusicListAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Result.this);
                MusicListInfo info = MenuList.sMusicListInfo.get(position);
                builder.setMessage("音乐名："+info.getName()+"\r\n作者："+info.getArtistsName()+"\r\n专辑："+info.getAlbumName());
                builder.setCancelable(true);
                if (mTag.equals("我喜欢的音乐")){
                    builder.setTitle("确认删除："+info.getName());
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new LikeCRUD().likeDelete(Result.this,position);
                            MenuList.sMusicListInfo.remove(position);
                            finish();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    //更新UI
    private void update() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter.setData();
        mRvResult.setAdapter(mAdapter);
        mRvResult.setLayoutManager(layoutManager);
    }
}
