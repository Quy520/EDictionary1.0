package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.qsd.edictionary.activitys.LoginActivity;
import com.example.qsd.edictionary.activitys.SettingActivity;
import com.example.qsd.edictionary.activitys.WordsDetailsActivity;
import com.example.qsd.edictionary.adapter.WordsAdapter;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.APPManager;
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
 * 记单词界面
 */
public class WrodsFragment extends Fragment {
   View view;
    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            if (view==null){
                view=inFlater(inflater);
            }
            return view;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private View inFlater(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.fragment_wrods,null,false);
        initView(view);
        initData();
        return view;
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
                Log.i("qqq",s);
            }

        });

    }

    private void initView(View view) {
        button= (Button) view.findViewById(R.id.testbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), WordsDetailsActivity.class);
                startActivity(intent);
            }
        });

    }


}
