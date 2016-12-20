package com.example.qsd.edictionary.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.qsd.edictionary.utils.NetUtils;
import com.example.qsd.edictionary.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.filter;

/**
 * 登陆界面
 */
public class LoginActivity extends AppCompatActivity {
    private ImageView imageView,eyes;
    private Button login;
    private CheckBox checkBox;
    private TextView register,froget;
    private EditText username,password;
    private String name,pw;

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
                 name = username.getText().toString();
                 pw = password.getText().toString();
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
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
        Utils.setEditTextInhibitInputSpeChat(password);//密码不能有空格
        Utils.setEditTextInhibitInputSpace(password);//对密码进行过滤
        int width=getWindowManager().getDefaultDisplay().getWidth();
        int heigh=getWindowManager().getDefaultDisplay().getHeight();
        LinearLayout.LayoutParams para= (LinearLayout.LayoutParams) imageView.getLayoutParams();
        para.width=width/3;
        para.height=heigh/6;
        imageView.setLayoutParams(para);
    }

}
