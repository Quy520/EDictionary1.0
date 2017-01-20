package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.APP;
import com.example.qsd.edictionary.activitys.CourseVedioActivity;
import com.example.qsd.edictionary.activitys.FullActivity;
import com.example.qsd.edictionary.adapter.CourseVedioAdapter;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.bean.CourseVedioBean;
import com.example.qsd.edictionary.bean.WordsBean;
import com.example.qsd.edictionary.costomProgressDialog.CustomProgressDialog;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.videoview.VideoSuperPlayer;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

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
 * 一订阅视屏页面
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    static SwipeRefreshLayout refreshLayout;
    private Button sub_words,sub_vedio;
    private Context context;
    static RecyclerView recyclerView;
    static LinearLayout linearLayout;
    static   CourseVedioBean vedioBean;
    static int userID=2;
    private  String Code="NOVIDE0";
    static Activity activity;
    private SharedPreferences sharedPreferences;
    static List<CourseVedioBean.DataBean> data;
    static CourseVedioAdapter vedioAdapter;
    static CustomProgressDialog customProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity=getActivity();
        customProgressDialog=new CustomProgressDialog(activity,"数据加载中....请稍后",R.drawable.donghua_frame);
        return inflater.inflate(R.layout.fragment_video,container,false);
    }
    @Override
    public void onViewCreated(View  view , @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);

        initView(view);
        refreshLayout.setVisibility(View.GONE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    initData(userID);
                    customProgressDialog.show();
            }
        });

    }
    static void clearData() {
        data.clear();
    }
    static void initData(int userID) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(vedioAdapter);
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"subscribedVideoAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.i("qsd1","一订阅视屏页面1"+s);
                vedioBean =new Gson().fromJson(s,CourseVedioBean.class);
                 data = vedioBean.getData();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vedioAdapter.setList(data);
                        vedioAdapter.notifyDataSetChanged();
                        customProgressDialog.dismiss();
                        refreshLayout.setRefreshing(false);
                    }
                });

            }

        });

    }
    private void initView(View view) {
        context=getContext();
        data=new ArrayList<>();
        vedioAdapter=new CourseVedioAdapter(activity,data);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.sub_swipe);
        linearLayout= (LinearLayout) view.findViewById(R.id.sub_linear);
        recyclerView= (RecyclerView) view.findViewById(R.id.sub_fragment_vedio);
//        sub_words= (Button) view.findViewById(R.id.sub_word);
//        sub_vedio= (Button) view.findViewById(R.id.sub_view);

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
                case 2:
                    clearData();
                    initData(userID);
                    linearLayout.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                    customProgressDialog.show();
                    break;
            }

        }
    };


}
