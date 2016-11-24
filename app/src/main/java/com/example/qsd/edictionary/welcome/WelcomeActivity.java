package com.example.qsd.edictionary.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.qsd.edictionary.MainActivity;
import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.LoginActivity;
import com.example.qsd.edictionary.adapter.MypageAdapter;

import java.util.ArrayList;
import java.util.List;
public class WelcomeActivity extends AppCompatActivity {
    private Context context;
    private LinearLayout layout;
    private List<View> mlist;
    private ViewPager viewPager;
    private Button button;
    private SharedPreferences sharedPreferences;
    private MypageAdapter mypageAdapter;
    private int[] images=new int[]{R.mipmap.welcom,R.mipmap.welcom2,R.mipmap.welcome3,R.mipmap.welcome4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在页面初始化之前进行判断是否第一次安装

        sharedPreferences = getSharedPreferences("ED", context.MODE_PRIVATE);
        Boolean flag=sharedPreferences.getBoolean("FIRST",false);
        if (flag){
            startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_welcome);
        initView();
        //初始化数据
        initData();
        mypageAdapter=new MypageAdapter(context,mlist);
        viewPager.setAdapter(mypageAdapter);
        //对viewpage进行监听Button的显示
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("qsd",position+"--"+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                if (position==images.length-1){
                    button.setVisibility(View.VISIBLE);
                }else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("qsd",state+"--");
            }
        });
        //button的监听跳转
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putBoolean("FIRST",true).commit();
                Intent intent=new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        mlist=new ArrayList<>();
        for (int i=0;i<images.length;i++){
            ImageView iv=new ImageView(this);
            int width=getWindowManager().getDefaultDisplay().getWidth();
            int heigh=getWindowManager().getDefaultDisplay().getHeight();
            Log.i("qsd",width+"=屏幕信息=="+heigh);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width, heigh);
            iv.setLayoutParams(lp);
            iv.setImageResource(images[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mlist.add(iv);
        }
    }

    private void initView() {
        button= (Button) findViewById(R.id.weicom_button);
        viewPager= (ViewPager) findViewById(R.id.view_welcome);
    }

}
