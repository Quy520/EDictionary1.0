package com.example.qsd.edictionary.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.utils.APPManager;

//我的里面的关于界面
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout about,introduce,user,update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        APPManager.addActivity(this);
        initView();
    }

    private void initView() {
        about= (LinearLayout) findViewById(R.id.about_help);
        introduce= (LinearLayout) findViewById(R.id.about_introduce);
        user= (LinearLayout) findViewById(R.id.about_greement);
        update= (LinearLayout) findViewById(R.id.about_update);
        initOnClick();
    }

    private void initOnClick() {
        about.setOnClickListener(this);
        introduce.setOnClickListener(this);
        user.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_help:
                Toast.makeText(this, "帮助使用", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_introduce:
                Toast.makeText(this, "介绍", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_greement:
                Toast.makeText(this, "用户协议", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_update:
                Toast.makeText(this, "检查更新", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
