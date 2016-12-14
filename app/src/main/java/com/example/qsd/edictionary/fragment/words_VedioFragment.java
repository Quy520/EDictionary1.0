package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.CourseVedioActivity;

/**
 * 记单词年纪的视屏fragment
 */
public class words_VedioFragment extends Fragment {
    private Activity activity;
    private View view;
    private RelativeLayout relativeLayout;

    public words_VedioFragment() {
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
        view=inflater.inflate(R.layout.fragment_words__vedio,null,false);
        activity=getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {
        relativeLayout= (RelativeLayout) view.findViewById(R.id.vedio_item);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, CourseVedioActivity.class);
                startActivity(intent);
            }
        });

    }


}
