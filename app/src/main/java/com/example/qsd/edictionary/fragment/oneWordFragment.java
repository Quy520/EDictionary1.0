package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.WordsVedioPlayActivity;

/**
 *年纪每个单元单词fragmen页面
 */
public class oneWordFragment extends Fragment {
    private  View view;
    private RelativeLayout relativeLayout;
    private Activity activity;

    public oneWordFragment() {
        // Required empty public constructor
    }
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
        view=inflater.inflate(R.layout.fragment_one_word,null,false);
        activity=getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {
        relativeLayout= (RelativeLayout) view.findViewById(R.id.word_item);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, WordsVedioPlayActivity.class);
                startActivity(intent);
            }
        });
    }


}
