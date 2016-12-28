package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.broadcastReceiver.SMSBroadcastReceiver;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 忘记密码界面
 */
public class ForgetActivity extends AppCompatActivity {
    private EditText phone,code,pw;
    private SMSBroadcastReceiver mSMSBroadcastReceiver;
    private Button check,commit;
    String p,c,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
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
                    forgetpassWord(p,password);//忘记密码的api

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码已经发送",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                ((Throwable) data).printStackTrace();
                Toast.makeText(ForgetActivity.this, "验证码错误",
                        Toast.LENGTH_SHORT).show();

            }


        }

    };
    Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                Toast.makeText(ForgetActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgetActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
            if (msg.what == 0x222) {
                Toast.makeText(ForgetActivity.this, "参数错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x333) {
                Toast.makeText(ForgetActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    private void initOnClick() {
          final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
            check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 p=phone.getText().toString();//注册手机号

                if (TextUtils.isEmpty(p))
                {
                    Toast.makeText(ForgetActivity.this, "手机不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isnumber(p)){
                    Toast.makeText(ForgetActivity.this, "手机号填写有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                myCountDownTimer.start();
                SMSSDK.getVerificationCode("86", p);//发送验证码
                mSMSBroadcastReceiver = new SMSBroadcastReceiver();
                mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSBroadcastReceiver.MessageListener() {
                    public void OnReceived(String message) {
                        code.setText(getDynamicPassword(message));// 截取6位验证码
                        code.setSelection(getDynamicPassword(message).length());

                    }
                });
                Toast.makeText(ForgetActivity.this, "请查收短信", Toast.LENGTH_SHORT).show();

            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 c=code.getText().toString();
                password=pw.getText().toString();
                if (TextUtils.isEmpty(c))
                {
                    Toast.makeText(ForgetActivity.this, "您的验证码没有填写", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(ForgetActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86", p,c);//验证验证码





            }
        });



    }

    private void forgetpassWord(String p, String password) {
        Log.i("qsd","是否请求1"+"ForgetActivity");
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userName",p)
                .add("password",password)
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"forgetPwdAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                CodeBean codeBean=new Gson().fromJson(s,CodeBean.class);
                String code = codeBean.getCode();
                if (code.equals("SUCCESS")){
                    handler1.sendEmptyMessage(0x111);//发送消息
                }
                if (code.equals("PARAERR")){
                    handler1.sendEmptyMessage(0x222);//发送消息
                }
                if (code.equals("USERR")){
                    handler1.sendEmptyMessage(0x333);//发送消息
                }
            }
        });

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

    private void initView() {
        phone= (EditText) findViewById(R.id.forget_phone);
        code= (EditText) findViewById(R.id.forget_code);
        pw= (EditText) findViewById(R.id.forget_pass);
        check= (Button) findViewById(R.id.forget_check);
        commit= (Button) findViewById(R.id.commit);
        Utils.setEditTextInhibitInputSpeChat(pw);//密码不能有空格
        Utils.setEditTextInhibitInputSpace(pw);//对密码进行过滤
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
}
