package com.example.myplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplayer.R;
import com.example.myplayer.utils.DlCRUD;
import com.example.myplayer.utils.GetMenuList;
import com.example.myplayer.utils.LikeCRUD;
import com.example.myplayer.utils.GetTopList;

import java.io.File;


public class Main extends AppCompatActivity {

    private EditText mEditText;
    private static final String TAG = "Main";

    public static Handler mHandler;
    private String mKeyWord;
    private RecyclerView mTopList;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new GetTopList();
    }

    private void initView() {
        mEditText = findViewById(R.id.keywords);

        mProgressBar = findViewById(R.id.pv_main);
        mProgressBar.setVisibility(View.GONE);
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 200) {
                    mProgressBar.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    public void like(View view){
        if(new LikeCRUD().likeSelete(getApplicationContext(),10)){
            Intent intent = new Intent(Main.this,Result.class);
            intent.putExtra("Tag","我喜欢的音乐");
            intent.putExtra("keyword","");
            startActivity(intent);
        }else {
            Toast.makeText(this, "喜欢的音乐为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void dlList(View view){
        File parentFile = new File(getFilesDir(),"/music");
        if (new DlCRUD().getDL(parentFile)){
        startActivity(new Intent(Main.this,Download.class));
        }else {
            Toast.makeText(this, "暂无本地音乐", Toast.LENGTH_SHORT).show();
        }
    }

    public void search(View view) {
        mKeyWord = mEditText.getText().toString();
        if(mKeyWord ==null|| mKeyWord.equals("")){
            Toast.makeText(this, "请输入搜索关键词", Toast.LENGTH_SHORT).show();
        }
        // TODO: 2020/11/11 实现加载更多后更改这里 
        new GetMenuList(30).getMusicList(mKeyWord);
        Intent intent = new Intent(Main.this,Result.class);
        intent.putExtra("Tag","搜索：");
        intent.putExtra("keyword",mKeyWord);
        startActivity(intent);
    }





}
