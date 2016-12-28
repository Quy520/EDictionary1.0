package com.example.qsd.edictionary.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.bean.LoginBean;
import com.example.qsd.edictionary.broadcastReceiver.SMSBroadcastReceiver;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText phone,code,pw,study;
    private Button register,check;
    private SMSBroadcastReceiver mSMSBroadcastReceiver;
    private CheckBox box;
    String p,password;
    String c,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
        initOnClick();
    }
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                // 短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功",
                            Toast.LENGTH_SHORT).show();
                    Log.i("qsd","验证成功"+"register=======");
                    Register(p,password,s);//注册


                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码已经发送",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                ((Throwable) data).printStackTrace();
                Toast.makeText(RegisterActivity.this, "验证码错误",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
    Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                Log.i("qsd","是否请求1"+"register请求数据=======");
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();

            }
            if (msg.what == 0x112) {
                Log.i("qsd","是否请求2"+"register请求数据=======");
                Toast.makeText(RegisterActivity.this, "手机号码重复", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x113) {
                Log.i("qsd","是否请求3"+"register请求数据=======");
                Toast.makeText(RegisterActivity.this, "互学码不存在", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x114) {
                Toast.makeText(RegisterActivity.this, "参数不规范", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x115) {
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                return;
            }

        }

    };


    public void initOnClick() {
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p=phone.getText().toString();
                if (TextUtils.isEmpty(p))
                {
                    Toast.makeText(RegisterActivity.this, "手机不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isnumber(p)){
                    Toast.makeText(RegisterActivity.this, "手机号填写有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                myCountDownTimer.start();
                SMSSDK.getVerificationCode("86", p);
                mSMSBroadcastReceiver = new SMSBroadcastReceiver();
                mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSBroadcastReceiver.MessageListener() {
                    public void OnReceived(String message) {
                        code.setText(getDynamicPassword(message));// 截取6位验证码
                        Log.i("qsd","register"+getDynamicPassword(message));
                        code.setSelection(getDynamicPassword(message).length());
                        Log.i("qsd","register"+getDynamicPassword(message).length());

                    }
                });
                Toast.makeText(RegisterActivity.this, "请查收短信", Toast.LENGTH_SHORT).show();


            }


        });
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //设置为明文显示
                    Log.i("qsd","1"+333);
                    pw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //设置为密文显示
                    pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c=code.getText().toString();//手机验证码
                password=pw.getText().toString();//账号密码
                s=study.getText().toString();//学习密码
                if (TextUtils.isEmpty(c))
                {
                    Toast.makeText(RegisterActivity.this, "您的验证码没有填写", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(s))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("您的互学码没填，好友不会享受打折优惠")
                            .setMessage("确认继续吗？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SMSSDK.submitVerificationCode("86", p,c);
                                    Log.i("qsd","是否验证成功"+"register=======");

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }else {
                     SMSSDK.submitVerificationCode("86", p,c);
                }



            }
        });
    }

    private void Register(String p, String password, String s) {
        Log.i("qsd","是否请求1"+"LoginActivity请求数据");
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userName",p)
                .add("password",password)
                .add("studyCode",s)
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"registerAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string=response.body().string();
                CodeBean codeBean=new Gson().fromJson(string,CodeBean.class);
                String code = codeBean.getCode();
                Log.i("qsd",code+"RegisterActivity");
                if (code.equals("SUCCESS")){
                    handler1.sendEmptyMessage(0x111);//发送消息
                }
                if (code.equals("MOBILE_REPEAT")){
                    //Toast.makeText(RegisterActivity.this, "MOBILE_REPEAT", Toast.LENGTH_SHORT).show();
                    handler1.sendEmptyMessage(0x112);//发送消息
                }
                if (code.equals("CODE_INEXISTENCE")){
                    handler1.sendEmptyMessage(0x113);//发送消息
                }
                if (code.equals("PARAERR")){
                    handler1.sendEmptyMessage(0x114);//发送消息
                }
                if (code.equals("REGISTER_ERR")){
                    handler1.sendEmptyMessage(0x115);//发送消息
                }


            }
        });
    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            check.setClickable(false);
            check.setText(l/1000+"s");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            check.setText("重新获取验证码");
            //设置可点击
            check.setClickable(true);
        }
    }
    private void initView() {
        register= (Button) findViewById(R.id.register);
        box= (CheckBox) findViewById(R.id.imageView);
        check= (Button) findViewById(R.id.register_Check);
        phone= (EditText) findViewById(R.id.register_phone);
        code= (EditText) findViewById(R.id.register_code);
        pw= (EditText) findViewById(R.id.register_pass);
        study= (EditText) findViewById(R.id.register_study);
        Utils.setEditTextInhibitInputSpeChat(pw);//密码不能有空格
        Utils.setEditTextInhibitInputSpace(pw);//对密码进行过滤

    }
    public String getDynamicPassword(String str) {
        // 6是验证码的位数一般为六位
        Pattern continuousNumberPattern = Pattern.compile("(?<![0-9])([0-9]{"
                + 6 + "})(?![0-9])");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while (m.find()) {
            System.out.print(m.group());
            dynamicPassword = m.group();
        }

        return dynamicPassword;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
