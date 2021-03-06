package com.example.qsd.edictionary.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
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

//我的里面的更新手机号
public class ChangeNewphone extends AppCompatActivity {
    private Button button;
    private Button commit;
    private EditText phone,code,pass;
   private String phoneNumber,c,pw;
    private String oldphone="15157526460";
    private int userID=2;
    private SMSBroadcastReceiver mSMSBroadcastReceiver;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_newphone);
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
//                    Intent intent = new Intent(ChangeNewphone.this,
//                            LoginActivity.class);
//                    startActivity(intent);
                    //验证成功后开始提交
                    updataMobileApi(userID,oldphone,phoneNumber,pw);

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码已经发送",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                ((Throwable) data).printStackTrace();
                Toast.makeText(ChangeNewphone.this, "验证码错误",
                        Toast.LENGTH_SHORT).show();

            }

        }

    };

    Handler getHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                Toast.makeText(ChangeNewphone.this, "修改成功", Toast.LENGTH_SHORT).show();
                sharedPreferences=getSharedPreferences("ED", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.remove("userName")
                        .putString("userName",phoneNumber).commit();
                finish();
            }
            if (msg.what == 0x222) {
                Toast.makeText(ChangeNewphone.this, "修改失败", Toast.LENGTH_SHORT).show();

            }

        }

    } ;

    private void updataMobileApi(int userID, String oldphone, String phoneNumber, String pw) {
        Log.i("qsd1","绑定新手机"+oldphone+phoneNumber+pw);
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
               .add("oldMobile",oldphone)
                .add("newMobile",phoneNumber)
                .add("password",pw)
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"updateMobileAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.i("qsd", "是否请求1" + "ChageNewPhone请求数据======="+s);
                CodeBean codeBean=new Gson().fromJson(s,CodeBean.class);
                String code = codeBean.getCode();
                if (code.equals("SUCCESS")){
                    getHandler.sendEmptyMessage(0x111);
                }else{
                    getHandler.sendEmptyMessage(0x222);
                }
            }
        });
    }

    private void initOnClick() {
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 phoneNumber = phone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(ChangeNewphone.this, "手机不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phoneNumber.equals(oldphone)) {
                    Toast.makeText(ChangeNewphone.this, "请跟换新手机", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isnumber(phoneNumber)){
                    Toast.makeText(ChangeNewphone.this, "手机号填写有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                myCountDownTimer.start();
                SMSSDK.getVerificationCode("86", phoneNumber);
                mSMSBroadcastReceiver = new SMSBroadcastReceiver();
                mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSBroadcastReceiver.MessageListener() {
                    public void OnReceived(String message) {
                        code.setText(getDynamicPassword(message));// 截取6位验证码
                        code.setSelection(getDynamicPassword(message).length());

                    }
                });
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 c=code.getText().toString();
               pw=pass.getText().toString();
                if (TextUtils.isEmpty(c)) {
                    Toast.makeText(ChangeNewphone.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pw)) {
                    Toast.makeText(ChangeNewphone.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86", phoneNumber,c);

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

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            button.setClickable(false);
            button.setText(l/1000+"s");

        }
        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            button.setText("重新获取验证码");
            //设置可点击
            button.setClickable(true);
        }
    }

    private void initView() {
        button= (Button) findViewById(R.id.Verification);
        commit= (Button) findViewById(R.id.changephone_commit);
        phone= (EditText) findViewById(R.id.et_changephone);
        code= (EditText) findViewById(R.id.et_changecode);
        pass= (EditText) findViewById(R.id.et_changepass);
        Utils.setEditTextInhibitInputSpeChat(pass);//密码不能有空格
        Utils.setEditTextInhibitInputSpace(pass);//对密码进行过滤
    }

}
