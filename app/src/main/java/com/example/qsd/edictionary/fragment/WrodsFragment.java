package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qsd.edictionary.MainActivity;
import com.example.qsd.edictionary.R;

import com.example.qsd.edictionary.adapter.WordsAdapter;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.bean.WordsBean;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.APPManager;
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
 * 记单词界面
 */
public class WrodsFragment extends Fragment {
   View view;
    private Activity activity;
    private  List<WordsBean.DataBean> wordsBeanData;
    private WordsAdapter wordsAdapter;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(@NonNull Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        activity=getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wrods,container,false);
    }

    @Override
    public void onViewCreated(View  view , @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        initView(view);
        initData();

    }
    private void initData() {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .build();
        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"getReciteWordsAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.i("qsd1",s+"记单词页面1");
                WordsBean wordsBean=new Gson().fromJson(s,WordsBean.class);
                wordsBeanData = wordsBean.getData();
                Log.i("qsd",wordsBeanData+"记单词页面2");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wordsAdapter.setList(wordsBeanData);
                    }
                });

            }

        });

    }
    private void initView(View view) {
        wordsBeanData=new ArrayList<>();
        wordsAdapter=new WordsAdapter(activity,wordsBeanData);
        recyclerView= (RecyclerView) view.findViewById(R.id.words_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(wordsAdapter);

    }


}
