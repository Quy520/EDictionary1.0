package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.WordsVedioPlayActivity;
import com.example.qsd.edictionary.adapter.GetWordsAdapter;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.bean.CourseVedioBean;
import com.example.qsd.edictionary.bean.GetWordsBean;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.google.gson.Gson;

import org.w3c.dom.Text;

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
 *年纪每个单元单词fragmen页面
 */
public class oneWordFragment extends Fragment {
    private  View view;
    private Button button;
    private TextView textView;
    private Activity activity;
    private RecyclerView recyclerView;
    private GetWordsAdapter getwordsAdapter;
    private String classifyID=null;
    private int userID=2;
    private int payStudyBean=0;
    private int coursePrice=0;
   private int payStudyBean1=0;
    private String type="words";
    private List<GetWordsBean.DataBean> data;
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

    //接受数据
    public static oneWordFragment newInstance(String value) {
        oneWordFragment fragment=new oneWordFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY",value);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments()!=null){
            if (getArguments().getString("KEY")!= null && getArguments().getString("KEY").length() != 0){
                classifyID = getArguments().getString("KEY");
                Log.i("qsd",classifyID+"传递过来的类型id");
                Log.i("qsd",classifyID.length()+"传递过来的类型id");
            }
        }
    }

    @Override
    public void onCreate(@NonNull Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        activity=getActivity();
    }
    private void initData(int userID, String classifyID) {

        OkHttpClient okHttpClient=new OkHttpClient();
        Log.i("qsd",userID+"单词传递过来的类型id"+classifyID);
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
                .add("classifyID",5+"")
                .build();

        Request request=new Request.Builder()
                .url(UrlString.URL_LOGIN+"getWordsAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("qsd2","记单词详细单词页面1失败哦");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.i("qsd2",s+"记单词单词详细数据");
                GetWordsBean getWordsBean=new Gson().fromJson(s,GetWordsBean.class);
                data = getWordsBean.getData();
                Log.i("qsd2",data.size()+"记单词单词详细数据");
                for (int i=0;i<data.size();i++){
                    for (int j=0;j<data.get(i).getWordData().size();j++) {
                        coursePrice = data.get(i).getWordData().get(i).getWordPrice();
                        payStudyBean1 = coursePrice+payStudyBean1;
                    }
                    payStudyBean=payStudyBean1+payStudyBean;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("一次性订五年级所有单词仅需"+payStudyBean+"学豆");
                    }
                });
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getwordsAdapter.setList(data);
                    }
                });
            }

        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_one_word,container,false);
    }

    @Override
    public void onViewCreated(View  view , @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        initView(view);

        onClick();
        initData(userID,classifyID);


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
                .add("payStudyBean", 80 + "")
                .add("type", type)
                .add("classifyID",5+"")
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
        getwordsAdapter=new GetWordsAdapter(activity,data);
        button= (Button) view.findViewById(R.id.words_subbAllButton);
        textView= (TextView) view.findViewById(R.id.words_text);
        recyclerView= (RecyclerView) view.findViewById(R.id.onewords_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(getwordsAdapter);

    }


}
