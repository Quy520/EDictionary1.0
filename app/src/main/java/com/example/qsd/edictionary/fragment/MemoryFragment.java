package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.MainActivity;
import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.LoginActivity;
import com.example.qsd.edictionary.activitys.RegisterActivity;
import com.example.qsd.edictionary.activitys.VedioPlayActivity;
import com.example.qsd.edictionary.activitys.WordsDetailsActivity;
import com.example.qsd.edictionary.adapter.MemoryAdapter;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.bean.MemoryDownBean;
import com.example.qsd.edictionary.bean.MemoryUpBean;
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
 * 记忆法界面
 */
public class MemoryFragment extends Fragment {
    private Activity activity;
    private RecyclerView recyclerView;
    private  LinearLayoutManager linearLayoutManager;
    private MemoryAdapter memoryAdapter;
    private Button button;
    private List<MemoryDownBean.DataBean> DownBeanData;
    private List<MemoryUpBean.DataBean> Updata;
    private int userID=2;
    private int payStudyBean=0;
    private String type="memory";
    private String Title;
    private TextView textView;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x111) {
                Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x222) {
                Toast.makeText(activity, "购买失败", Toast.LENGTH_SHORT).show();
                return;
            }

        }
    };
    public MemoryFragment() {

    }

    @Override
    public void onCreate(@NonNull Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        activity=getActivity();
        //下载数据
        //initData();
    }

    private void initData() {
        //memoryAdapter=new MemoryAdapter(activity,DownBeanData,Updata);
        //先获取记忆法课程
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID","2")
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"courseAPI")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string=response.body().string();
                MemoryDownBean DownBean=new Gson().fromJson(string,MemoryDownBean.class);//轮播图片
                 DownBeanData = DownBean.getData();
                Log.i("qsd1","bannerAPI"+DownBeanData.get(1).getCourseImageUrl());
                for (int i=0;i<DownBeanData.size();i++){
                    int coursePrice = DownBeanData.get(i).getCoursePrice();

                    payStudyBean=coursePrice+payStudyBean;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("一次性订阅记忆法所有课程仅需"+payStudyBean+"学豆");
                    }
                });

                LoadDown();//轮播图
            }
        });

    }

    private void LoadDown() {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"bannerAPI")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("qsd1","主线程更新2"+"memory页面下载失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string=response.body().string();
                MemoryUpBean UpBean=new Gson().fromJson(string,MemoryUpBean.class);//轮播图片
                 Updata = UpBean.getData();
                        Log.i("qsd1","bannerAPI"+Updata.get(1).getLinkUrl());
                        Log.i("qsd1","主线程更新1 记忆法课程的数据"+DownBeanData.size()+Updata.size());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        memoryAdapter.setList(DownBeanData,Updata);
                    }
                });


            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return LayoutInflater.from(activity).inflate(R.layout.fragment_memory,container,false);
    }
    @Override
    public void onViewCreated(View  view , @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);

        initView(view);
       initData();
    }

    private void initView(View view) {
        DownBeanData=new ArrayList<>();
        Updata=new ArrayList<>();
        memoryAdapter=new MemoryAdapter(activity,DownBeanData,Updata);
        recyclerView= (RecyclerView) view.findViewById(R.id.memory_recy);
        button= (Button) view.findViewById(R.id.memory_subbAllButton);
        textView= (TextView) view.findViewById(R.id.memory_text);
        setAdapter();
        initOnClick();//全部订阅视屏监听
    }



    private void initOnClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity)
                        .setTitle("确认订购")
                        .setMessage("如果确认将一次性订阅所有记忆法视屏？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                Log.i("qsd","是否订阅成功"+s);
                CodeBean codeBean=new Gson().fromJson(s,CodeBean.class);
                String code = codeBean.getCode();
                Log.i("qsd","是否订阅成功"+code);
                if (code.equals("SUCCESS")){
                   handler.sendEmptyMessage(0x111);
                }else{
                    handler.sendEmptyMessage(0x222);
                }


            }

        });
    }

    private void setAdapter() {
        linearLayoutManager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(memoryAdapter);
        memoryAdapter.setOnItemClickListener(new MemoryAdapter.onRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(activity, "您点击了"+data, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), VedioPlayActivity.class);
                int i = Integer.parseInt(data);
                 Title = DownBeanData.get(i).getCourseName();
                intent.putExtra("courseTitle",Title);
                startActivity(intent);
            }
        });

    }

}
