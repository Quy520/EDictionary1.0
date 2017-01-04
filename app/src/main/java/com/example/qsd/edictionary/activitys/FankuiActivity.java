package com.example.qsd.edictionary.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
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

//我的里面的反馈界面
public class FankuiActivity extends Activity {
    private Button button;
    private EditText editText;
    private String content;
    private int userID=2;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                Toast.makeText(FankuiActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);
        initView();

    }

    private void initView() {
        editText= (EditText) findViewById(R.id.add_content);
        button= (Button) findViewById(R.id.fankui_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content=editText.getText().toString();
               fankui(userID,content);
            }
        });
    }
    private void fankui(int userID, String content) {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
                .add("content",content)
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"fedBackAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.i("qsd", "是否请求1" + "register请求数据======="+s);
               CodeBean codeBean=new Gson().fromJson(s,CodeBean.class);
                String code = codeBean.getCode();
                if (code.equals("SUCCESS")){
                    handler.sendEmptyMessage(0x111);
                }
            }
        });


    }
}
