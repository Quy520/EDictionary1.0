package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.example.qsd.edictionary.costomProgressDialog.CustomProgressDialog;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.SearchDB;
import com.example.qsd.edictionary.utils.SharedpreferencesUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    static Activity activity;
    static RecyclerView recyclerView;
    static  LinearLayoutManager linearLayoutManager;
    static RelativeLayout relativeLayout;
    static MemoryAdapter memoryAdapter;
    static Button button;
    static List<MemoryDownBean.DataBean> DownBeanData;
    static List<MemoryUpBean.DataBean> Updata;
    static int userID=2;
    static int payStudyBean=0;
    static String type="memory";
    static String Title;
    static TextView textView;
    static SwipeRefreshLayout refreshLayout;
    static CustomProgressDialog customProgressDialog;
    static SharedPreferences sharedPreferences;
    static int studyBean,cost;

   static Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x111) {
                Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
                studyBean= SearchDB.StudyBeanDb(activity,"studyBean");
                cost=SearchDB.CostDb(activity,"costStudyBean");
                studyBean=studyBean-payStudyBean;
                cost=cost+payStudyBean;//支付成功后的学习豆和消费学豆
                SharedpreferencesUtils.SaveStudyCode(activity,studyBean,cost);
                clearData();
                customProgressDialog.show();
                initData();
                relativeLayout.setVisibility(View.GONE);
                return;
            }
            if (msg.what == 0x222) {
                Toast.makeText(activity, "您已经购买", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x333) {
                Toast.makeText(activity, "余额不足，请去充值", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x444) {
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

        customProgressDialog=new CustomProgressDialog(activity,"数据加载中....请稍后",R.drawable.donghua_frame);

    }

    static void initData() {
        //memoryAdapter=new MemoryAdapter(activity,DownBeanData,Updata);
        //先获取记忆法课程
        payStudyBean=0;
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
                Log.i("qsd1","MemoryFrgament"+string);

                for (int i=0;i<DownBeanData.size();i++){
                    if (DownBeanData.get(i).getCoursePayStatus()==0) {
                        int coursePrice = DownBeanData.get(i).getCoursePrice();
                        payStudyBean = coursePrice + payStudyBean;
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (payStudyBean!=0){
                            textView.setText("一次性订阅记忆法所有课程仅需"+payStudyBean+"学豆");
                        }else{
                            relativeLayout.setVisibility(View.GONE);
                        }
                    }
                });

                LoadDown();//轮播图
            }
        });

    }

    static void LoadDown() {
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
               Log.i("qsd1","主线程更新1 记忆法课程的数据"+string);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        memoryAdapter.setList(DownBeanData,Updata);
                        memoryAdapter.notifyDataSetChanged();
                        customProgressDialog.dismiss();
                        refreshLayout.setRefreshing(false);

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
        //refreshLayout.setColorSchemeColors(Color.BLUE, Color.GRAY);
       // refreshLayout.setProgressBackgroundColorSchemeColor(Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                customProgressDialog.show();
                initData();
            }
        });

       initData();
    }

    static void clearData() {
        DownBeanData.clear();
        payStudyBean=0;
        Updata.clear();
    }

    private void initView(View view) {
        DownBeanData=new ArrayList<>();
        Updata=new ArrayList<>();
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.memory_swipe);
        relativeLayout= (RelativeLayout) view.findViewById(R.id.memore_sub);
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
                }
                else if(code.equals("NOPAY")){
                    handler.sendEmptyMessage(0x222);
                }
                else if(code.equals("NOTSUFFICIENTFUNDS")){
                    handler.sendEmptyMessage(0x333);
                }else{
                    handler.sendEmptyMessage(0x444);
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
                 Title = DownBeanData.get(i-1).getCourseName();
                int type=DownBeanData.get(i-1).getCoursePayStatus();//是否购买
                int price=DownBeanData.get(i-1).getCoursePrice();//购买的价格
                String  VedioImageurl=DownBeanData.get(i-1).getCourseImageUrl();//视屏图片
                String  Vediogeurl=DownBeanData.get(i-1).getCourseVideo();//视屏的url
                intent.putExtra("courseTitle",Title);
                intent.putExtra("courseID",i);
                intent.putExtra("coursePayStatus",type);
                intent.putExtra("coursePrice",price);
                intent.putExtra("courseImageUrl",VedioImageurl);
                intent.putExtra("courseVideo",Vediogeurl);
                startActivity(intent);
            }
        });

    }
    public static Handler refresh=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what){
                case 1:
                    clearData();
                    customProgressDialog.show();
                    initData();
                    break;
            }

        }
    };
}
