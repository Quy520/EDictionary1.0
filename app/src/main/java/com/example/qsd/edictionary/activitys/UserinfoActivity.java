package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.qsd.edictionary.R;

/**
 * 用户修改信息界面
 */
public class UserinfoActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout info_image,info_phone,info_pass,info_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView();
        initOnClick();
    }

    private void initOnClick() {
        info_name.setOnClickListener(this);
        info_phone.setOnClickListener(this);
        info_pass.setOnClickListener(this);
        info_image.setOnClickListener(this);

    }

    private void initView() {
        info_phone= (RelativeLayout) findViewById(R.id.info_user);
        info_pass= (RelativeLayout) findViewById(R.id.info_pass);
        info_image= (RelativeLayout) findViewById(R.id.info_image);
        info_name= (RelativeLayout) findViewById(R.id.info_name);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_user:
                Toast.makeText(this, "手机号码修改", Toast.LENGTH_SHORT).show();
                break;
            case R.id.info_pass:
                Toast.makeText(this, "密码修改", Toast.LENGTH_SHORT).show();
                break;
            case R.id.info_image:
                Toast.makeText(this, "图片修改", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,PhotoChange.class);
                startActivity(intent);
                break;
            case R.id.info_name:
                Toast.makeText(this, "昵称修改", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
