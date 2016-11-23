package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qsd.edictionary.MainActivity;
import com.example.qsd.edictionary.R;

public class LoginActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button login;
    private TextView register,froget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        froget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        imageView= (ImageView) findViewById(R.id.logo);
        login= (Button) findViewById(R.id.login);
        register=(TextView) findViewById(R.id.register);
        froget= (TextView) findViewById(R.id.forget);
        int width=getWindowManager().getDefaultDisplay().getWidth();
        int heigh=getWindowManager().getDefaultDisplay().getHeight();
        LinearLayout.LayoutParams para= (LinearLayout.LayoutParams) imageView.getLayoutParams();
        para.width=width/2;
        para.height=heigh/5;
        imageView.setLayoutParams(para);
    }

}
