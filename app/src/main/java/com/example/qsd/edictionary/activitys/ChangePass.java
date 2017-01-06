package com.example.qsd.edictionary.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//我的里面的更新密码
public class ChangePass extends AppCompatActivity {
    private EditText oldpass,newpass;
    private TextView button;
    private String oldpw,newpw,username;
    private SharedPreferences sharedPreferences;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                Toast.makeText(ChangePass.this, "修改成功，请重新登陆", Toast.LENGTH_SHORT).show();
                sharedPreferences=getSharedPreferences("ED", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.remove("password").commit();
                Intent intent = new Intent(ChangePass.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();

            }
            if (msg.what == 0x222) {
                Toast.makeText(ChangePass.this, "修改失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        initView();
    }

    private void initView() {
        oldpass= (EditText) findViewById(R.id.old_pass);
        newpass= (EditText) findViewById(R.id.new_pass);
        button= (TextView) findViewById(R.id.pass_commit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username="15551899606";
                oldpw=oldpass.getText().toString();
                newpw=newpass.getText().toString();
                Toast.makeText(ChangePass.this, "提交", Toast.LENGTH_SHORT).show();
                changepass(username,oldpw,newpw);
            }
        });
    }


    private void changepass(String username,String oldpw, String newpw) {
        Log.i("qsd","是否请求1"+"ForgetActivity");
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userName",username)
                .add("oldPassword",oldpw)
                .add("newPassword",newpw)
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"updatePwdAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.i("qsd","修改密码"+s);
                CodeBean codeBean=new Gson().fromJson(s,CodeBean.class);
                String code = codeBean.getCode();
                Log.i("qsd","修改密码"+code);
                if (code.equals("SUCCESS")){
                    handler.sendEmptyMessage(0x111);//发送消息
                }
                if (code.equals("PARAERR")){
                    handler.sendEmptyMessage(0x222);//发送消息
                }
                if (code.equals("USERR")){
                    handler.sendEmptyMessage(0x333);//发送消息
                }
            }
        });
    }

}
