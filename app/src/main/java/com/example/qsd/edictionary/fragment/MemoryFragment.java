package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.adapter.MemoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 记忆法界面
 */
public class MemoryFragment extends Fragment {
    private Activity activity;
    private RecyclerView recyclerView;
    private  LinearLayoutManager linearLayoutManager;
    private MemoryAdapter memoryAdapter;
    private List<String> urllist;

    public MemoryFragment() {

    }

    @Override
    public void onCreate(@NonNull Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        activity=getActivity();
        //下载数据
        initData();


    }

    private void initData() {
    urllist=new ArrayList<>();
        memoryAdapter=new MemoryAdapter(activity,urllist);
        urllist.add("http://i0.hdslb.com/bfs/live/dcc6a31bb329a3c07e898a663c71e1b9ace9b922.jpg");
        urllist.add("http://i0.hdslb.com/bfs/live/0ce4e43f0dddb3f1b3846d19d9472e703bd637af.jpg");
        memoryAdapter.setList(urllist);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=LayoutInflater.from(activity).inflate(R.layout.fragment_memory,container,false);
       recyclerView= (RecyclerView) view.findViewById(R.id.memory_recy);
        setAdapter();
        return view;
    }

    private void setAdapter() {
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(memoryAdapter);

    }

}
