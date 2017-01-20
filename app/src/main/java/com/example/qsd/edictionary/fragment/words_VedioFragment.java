package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.CourseVedioActivity;
import com.example.qsd.edictionary.adapter.CourseVedioAdapter;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.bean.CourseVedioBean;
import com.example.qsd.edictionary.costomProgressDialog.CustomProgressDialog;
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
 * 记单词年纪的视屏fragment
 */
public class words_VedioFragment extends Fragment {
    static Activity activity;
    static int userID=2;
    static List<CourseVedioBean.DataBean> data;
    static CourseVedioAdapter vedioAdapter;
    static RecyclerView recyclerView;
    static RelativeLayout relativeLayout;
    private Button button;
    static String classifyID;
    static int payStudyBean=0;
    private String type="k12";
    static TextView textView;
    private SharedPreferences sharedPreferences;
    static SwipeRefreshLayout refreshLayout;
    static CustomProgressDialog customProgressDialog;
    //接受数据
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x111) {
                Toast.makeText(activity, "购买成功", Toast.LENGTH_SHORT).show();
                sharedPreferences=activity.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences. Editor userinfo = sharedPreferences.edit();
                userinfo.putString("SUCCESS","SUCCESS").commit();
                clearData();
                customProgressDialog.show();
                initData(userID, classifyID);
                return;
            }
            if (msg.what == 0x222) {
                Toast.makeText(activity, "购买失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };
    public static words_VedioFragment newInstance(String value) {
        words_VedioFragment fragment=new words_VedioFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY",value);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void onCreate(@NonNull Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        activity=getActivity();
        customProgressDialog=new CustomProgressDialog(activity,"数据加载中....请稍后",R.drawable.donghua_frame);
    }
    static void initData(int userID,String classifyID) {
        OkHttpClient okHttpClient=new OkHttpClient();
        Log.i("qsd",userID+"vedio单词页面传递过来的类型id"+classifyID);
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
                .add("classifyID",classifyID)
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"getCourseVideoAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("qsd1","记单词详细视屏页面1失败哦");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.i("qsd1",s+"记单词详细视屏页面1");
                CourseVedioBean vedioBean=new Gson().fromJson(s,CourseVedioBean.class);
                data = vedioBean.data;
                for (int i=0;i<data.size();i++){
                    if (data.get(i).getProductType()==0){
                    int coursePrice = data.get(i).getVideoPrice();
                    payStudyBean=coursePrice+payStudyBean;
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vedioAdapter.setList(data);
                        refreshLayout.setRefreshing(false);
                        customProgressDialog.dismiss();
                        if (payStudyBean!=0){
                            textView.setText("一次性订阅五年级所有视屏仅需"+payStudyBean+"学豆");
                        }else{
                            relativeLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_words__vedio,container,false);
    }
    @Override
    public void onViewCreated(View  view , @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        initView(view);
        if (getArguments()!=null) {
                classifyID = getArguments().getString("KEY");
                Log.i("qsd", classifyID + "vedio传递过来的类型id");
        }
        initData(userID, classifyID);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                initData(userID, classifyID);
                customProgressDialog.show();
            }
        });
        onClick();
    }

    static void clearData() {
        payStudyBean=0;
        data.clear();
    }
    private void onClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(activity)
                            .setTitle("确认订购")
                            .setMessage("如果确认将一次性订阅所有记忆法视屏？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SubAll(userID,payStudyBean,type,classifyID);
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
    private void SubAll(int userID, int payStudyBean, String type, String classifyID) {
        Log.i("qsd1","订阅传递的参数"+userID+"学习"+payStudyBean+type+classifyID);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody
                .Builder()
                .add("userID", userID + "")
                .add("payStudyBean", payStudyBean + "")
                .add("type", type)
                .add("classifyID",classifyID)
                .build();
        Request request = new Request.Builder()
                .url(UrlString.URL_LOGIN + "subAllAPI")
                .post(requestBody)
                .build();
        Log.i("qsd1","订阅传递的参数"+userID+"学习"+payStudyBean+type+classifyID);
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

    private void initView(View view) {
        data=new ArrayList<>();
        vedioAdapter=new CourseVedioAdapter(activity,data);
        button= (Button) view.findViewById(R.id.vedio_subbAllButton);
        textView= (TextView) view.findViewById(R.id.vedio_text);
        recyclerView= (RecyclerView) view.findViewById(R.id.word_vedio_recycle);
        relativeLayout= (RelativeLayout) view.findViewById(R.id.word_vedio_sub);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.wordvedio_swiper);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(vedioAdapter);
        vedioAdapter.setOnItemClickListener(new CourseVedioAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, String tag) {
                Toast.makeText(activity, "您点击了视屏"+tag, Toast.LENGTH_SHORT).show();
                int id=Integer.parseInt(tag);
                String videoImageUrl = data.get(id).getVideoImageUrl();
                String videoUrl = data.get(id).getVideoUrl();
                int productType = data.get(id).getProductType();
                int videoPrice = data.get(id).getVideoPrice();
                int videoID = data.get(id).getVideoID();
                Intent intent=new Intent(activity, CourseVedioActivity.class);
                intent.putExtra("videoImageUrl",videoImageUrl);
                intent.putExtra("videoUrl",videoUrl);
                intent.putExtra("productType",productType);
                intent.putExtra("videoPrice",videoPrice);
                intent.putExtra("videoID",videoID);
                activity.startActivity(intent);
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
                    initData(userID, classifyID);
                    customProgressDialog.show();
                    break;
            }

        }
    };

}
