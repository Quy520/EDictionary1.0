package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.WordsDetailsActivity;
import com.example.qsd.edictionary.adapter.WordsAdapter;

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
        return view;
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
