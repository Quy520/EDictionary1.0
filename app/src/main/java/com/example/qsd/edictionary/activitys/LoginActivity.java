package com.example.qsd.edictionary.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.MainActivity;
import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.LoginBean;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.NetUtils;
import com.example.qsd.edictionary.utils.Utils;
import com.google.gson.Gson;

import java.io.IOException;

import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 登陆界面
 */
public class LoginActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button login;
    private CheckBox checkBox;
    private TextView register,froget;
    private EditText username,password;
    private SharedPreferences sharedPreferences;


    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x111) {
                Toast.makeText(LoginActivity.this, "登陆失败,账号或密码错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x222) {
                Toast.makeText(LoginActivity.this, "当前账号已在线", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有网络
                checkNetState();
              final String    name = username.getText().toString();
                final String pw = password.getText().toString();
                if (TextUtils.isEmpty(name))
                {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isnumber(name)){
                    Toast.makeText(LoginActivity.this, "手机号填写有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pw))
                {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("qsd","登陆"+name+pw);
                loginApp(name,pw);//第一次登陆
//                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
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
        Log.i("qsd","1"+111);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("qsd","1"+222);
                if(isChecked){
                    //设置为明文显示
                    Log.i("qsd","1"+333);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //设置为密文显示
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }


        }
        });

    }


    public void loginApp(final String name, final String pw) {
        Log.i("qsd","是否请求1"+"LoginActivity请求数据");
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userName",name)
                .add("password",pw)
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"loginAPI")
                .post(requestBody)
                .build();
        Log.i("qsd","是否请求1"+UrlString.URL_LOGIN+"loginAPI");
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("qsd","是否请求2"+"LoginActivity请求数据");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.i("qsd",s+"LoginActivity请求数据");
                LoginBean loginBean=new Gson().fromJson(s,LoginBean.class);
                String code = loginBean.getCode();
                int studyBean=loginBean.getData().getStudyBean();
                String nickname=loginBean.getData().getNickName();
                String headImageurl=loginBean.getData().getHeadImageUrl();
                int costStudyBean=loginBean.getData().getCostStudyBean();
                String studyCode=loginBean.getData().getStudyCode();
                int loginStatus=loginBean.getData().getLoginStatus();
                int userID=loginBean.getData().getUserID();

                //将数据保存
                sharedPreferences=getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putInt("studyBean",studyBean)//学习豆
                        .putInt("costStudyBean",costStudyBean)
                        .putInt("loginStatus",loginStatus)
                        .putInt("userID",userID)
                        .putString("nickname",nickname)
                        .putString("headImageurl",headImageurl)
                        .putString("studyCode",studyCode).commit();


                if (code.equals("SUCCESS")){//登陆成功
                    SharedPreferences savepw=getSharedPreferences("ED", Context.MODE_PRIVATE);//保存密码
                    SharedPreferences.Editor editsavepw = savepw.edit();
                    editsavepw.putString("password", pw)
                            .putString("userName",name).commit();
                    Log.i("qsd","Logactivity登陆成功"+pw+name);
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (code.equals("USAPWERR")){
                    handler.sendEmptyMessage(0x111);//发送消息

                }
                if (code.equals("REPEAT_LOGIN")){
                    handler.sendEmptyMessage(0x222);//发送消息

                }

            }
        });

    }

    /**
     * 检查网络是否连接
     */
    private void checkNetState() {
        if(!NetUtils.isNetWork(this)){
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setTitle("网络状态提醒")
                    .setMessage("当前网络状态不可以，是否打开网络设置？？")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (android.os.Build.VERSION.SDK_INT>10){
                                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                            }else {
                                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                            }
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.create().show();
        }
    }

    private void initView() {
        imageView= (ImageView) findViewById(R.id.logo);
        login= (Button) findViewById(R.id.login);
        register=(TextView) findViewById(R.id.register);
        froget= (TextView) findViewById(R.id.forget);
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        checkBox= (CheckBox) findViewById(R.id.eyes);
        Utils.setEditTextInhibitInputSpace(password);//密码不能有空格
        Utils.setEditTextInhibitInputSpeChat(password);//对密码进行过滤
        int width=getWindowManager().getDefaultDisplay().getWidth();
        int heigh=getWindowManager().getDefaultDisplay().getHeight();
        LinearLayout.LayoutParams para= (LinearLayout.LayoutParams) imageView.getLayoutParams();
        para.width=width/3;
        para.height=heigh/6;
        imageView.setLayoutParams(para);
    }

}
