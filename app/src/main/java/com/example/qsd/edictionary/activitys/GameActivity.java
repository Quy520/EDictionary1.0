package com.example.qsd.edictionary.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.RandomStringUtils;
import com.example.qsd.edictionary.utils.SharedpreferencesUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 益智类游戏页面
 */
public class GameActivity extends AppCompatActivity {
    private Button button;
    private int userID=2;
    private String type="game";
    private int payStudyBean=10;
    private SharedPreferences sharedPreferences;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x111) {
                sharedPreferences=getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("subgame","SUCCESS").commit();
                Intent intent=new Intent(GameActivity.this,GameGreadActivity.class);
                startActivity(intent);
                GameActivity.this.finish();
                return;
            }
            if (msg.what == 0x222) {
                Toast.makeText(GameActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x333) {
                Intent intent=new Intent(GameActivity.this,GameGreadActivity.class);
                startActivity(intent);
                GameActivity.this.finish();
                return;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        button= (Button) findViewById(R.id.play_game);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                String name = sharedPreferences.getString("subgame", "faile");
                if (name.equals("SUCCESS")){
                    Intent intent=new Intent(GameActivity.this,GameGreadActivity.class);
                    startActivity(intent);
                    GameActivity.this.finish();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this)
                            .setTitle("*确认联系*")
                            .setMessage("每次练习需要花费10个学习豆")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SubAll(userID,payStudyBean,type);

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

            }
        });



    }

    private void SubAll(int userID, int payStudyBean, String type) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody
                .Builder()
                .add("userID", userID + "")
                .add("payStudyBean", payStudyBean + "")
                .add("type", type)
                .build();
        Request request = new Request.Builder()
                .url(UrlString.URL_LOGIN + "subAllAPI")
                .post(requestBody)
                .build();
        Log.i("qsd1","订阅传递的参数"+userID+"学习"+payStudyBean+type);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                CodeBean codeBean=new Gson().fromJson(s,CodeBean.class);
                String code = codeBean.getCode();
                Log.i("qsd","游戏是否订阅成功"+code);
                if (code.equals("SUCCESS")){
                    handler.sendEmptyMessage(0x111);
                }
                else if(code.equals("NOTSUFFICIENTFUNDS")){
                    handler.sendEmptyMessage(0x222);
                }else{
                    handler.sendEmptyMessage(0x333);
                }


            }

        });

    }
}
