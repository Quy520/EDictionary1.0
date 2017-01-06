package com.example.qsd.edictionary.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

//我的里面的跟新昵称
public class ChangeName extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private int userId=2;
    private String name;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                SharedPreferences sharedPreferences=getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("nickname",name).commit();
                Toast.makeText(ChangeName.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (msg.what==0x222){
                Toast.makeText(ChangeName.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        editText= (EditText) findViewById(R.id.changename);

        button= (Button) findViewById(R.id.name_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editText.getText().toString();
                editText.clearFocus();
                updateNickNameAPI(userId,name);


            }
        });


    }

    private void updateNickNameAPI(int userId, String name) {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userId+"")
                .add("nickName",name)

                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"updateNickNameAPI")
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
                    handler.sendEmptyMessage(0x111);
                }else{
                    handler.sendEmptyMessage(0x222);
                }
            }
        });
    }
}
