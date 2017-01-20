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
import com.example.qsd.edictionary.bean.GetBeansBean;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity {
    private Context context;
    private LinearLayout layout;
    private List<View> mlist;
    private ViewPager viewPager;
    private Button button;
    private SharedPreferences sharedPreferences,userinfosharedPreferences;
    private MypageAdapter mypageAdapter;
    private int[] images=new int[]{R.mipmap.welcom,R.mipmap.welcom2,R.mipmap.welcome3,R.mipmap.welcome4};
    private int userID=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在页面初始化之前进行判断是否第一次安装
        sharedPreferences = getSharedPreferences("ED", context.MODE_PRIVATE);
        Boolean flag=sharedPreferences.getBoolean("FIRST",false);
        if (flag){
            String name=sharedPreferences.getString("userName","false");
            String pw=sharedPreferences.getString("password","false");
             Log.i("qsd","welcome密码获取"+pw+"用户名"+name);
            if(name.equals("false")&&pw.equals("false")){
                Log.i("qsd","welcom"+"第一次输密码");
               startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                finish();
            }else{
                Log.i("qsd","welcom"+"第er次输密码");
                getBeansAPI(userID);
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
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
    private void getBeansAPI(int userID) {
        OkHttpClient okHttpClient=new OkHttpClient();

        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
                .build();

        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"getBeansAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("qsd2","第二次登陆进来在家失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.i("qsd2","第二次登陆进来加载成功"+s);
                GetBeansBean beansBean=new Gson().fromJson(s,GetBeansBean.class);
                int consumeBean = beansBean.getData().getConsumeBean();
                int restBean = beansBean.getData().getRestBean();
                int rechargeBean = beansBean.getData().getRechargeBean();
                int systemGiveBean = beansBean.getData().getSystemGiveBean();
                userinfosharedPreferences=getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor userinfo = userinfosharedPreferences.edit();
                userinfo.putInt("studyBean",restBean)//学习豆
                        .putInt("costStudyBean",consumeBean)
                        .putInt("rechargeBean",rechargeBean)
                        .putInt("systemGivenBean",systemGiveBean)
                        .commit();
            }
        });
    }

    private void initData() {
        mlist=new ArrayList<>();
        for (int i=0;i<images.length;i++){
            ImageView iv=new ImageView(this);
            int width=getWindowManager().getDefaultDisplay().getWidth();
            int heigh=getWindowManager().getDefaultDisplay().getHeight();
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

    @Override
    protected void onStop(){
        finish();
    }

}
