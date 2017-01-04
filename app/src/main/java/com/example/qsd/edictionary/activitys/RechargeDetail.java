package com.example.qsd.edictionary.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.adapter.RechageDetailAdapter;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.bean.RechagedetailBean;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 充值明细
 */
public class RechargeDetail extends Activity {
    private TextView textView;
    private  LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private int userID=2;
    List<RechagedetailBean.DataBean> data;
    Handler handler=new Handler();
    private RechageDetailAdapter rechageDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_detail);
        initView();
        initData(userID);
    }

    private void initData(int userID) {

        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"getPayDetailAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String s=response.body().string();
                Log.i("qsd", "是否请求1" + "RechargeDetail请求数据======="+s);
                RechagedetailBean rechagedetailBean=new Gson().fromJson(s,RechagedetailBean.class);
                data = rechagedetailBean.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rechageDetailAdapter.setList(data);
                    }
                });

            }
        });
    }

    private void initView() {
        data=new ArrayList<>();
        textView= (TextView) findViewById(R.id.rechagedetail_tv);
        recyclerView= (RecyclerView) findViewById(R.id.rechagedetail_recy);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        rechageDetailAdapter=new RechageDetailAdapter(this,data);
        recyclerView.setAdapter(rechageDetailAdapter);

    }
}
