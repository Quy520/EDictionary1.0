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
import com.example.qsd.edictionary.broadcastReceiver.SMSBroadcastReceiver;
import com.example.qsd.edictionary.utils.Utils;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText phone,code,pw,study;
    private Button register,check;
    private SMSBroadcastReceiver mSMSBroadcastReceiver;
    private CheckBox box;
    String p;
    String c;
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
                    Intent intent = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
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
                myCountDownTimer.start();
                SMSSDK.getVerificationCode("86", p);
                mSMSBroadcastReceiver = new SMSBroadcastReceiver();
                mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSBroadcastReceiver.MessageListener() {
                    public void OnReceived(String message) {
                        code.setText(getDynamicPassword(message));// 截取6位验证码
                        code.setSelection(getDynamicPassword(message).length());

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
                String password=pw.getText().toString();//账号密码
                String s=study.getText().toString();//学习密码
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
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
                SMSSDK.submitVerificationCode("86", p,c);



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
