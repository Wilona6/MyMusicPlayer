package com.example.myplayer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myplayer.R;
import com.example.myplayer.utils.DlCRUD;

import java.io.File;

public class Download extends AppCompatActivity {

    private ListView mDl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mDl = findViewById(R.id.lv_dl);
        setDl();
    }

    private void setDl() {
        File parentFile = new File(getFilesDir(),"/music");
        new DlCRUD().getDL(parentFile);
        mDl.setAdapter(new dlAdapter());
    }

    class dlAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return DlCRUD.sDlList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dllist, null);
            String fileName = DlCRUD.sDlList.get(position);
            TextView id = view.findViewById(R.id.tv_dl);
            id.setText(fileName);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View viewas) {

                    return false;
                }
            });

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Download.this);
                    builder.setMessage(fileName);
                    builder.setCancelable(true);
                    builder.setTitle("删除确认");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(new DlCRUD().deleteMusic(new File(getFilesDir(),"/music/"+fileName))){
                                Toast.makeText(Download.this, "删除成功", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            }else {
                                Toast.makeText(Download.this, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            });
            return view;
        }
    }
}
