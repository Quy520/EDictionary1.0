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
import com.example.qsd.edictionary.adapter.WordsAdapter;

/**
 * 记单词界面
 */
public class WrodsFragment extends Fragment {
    private Activity activity;
    private RecyclerView recyclerView;
    private  LinearLayoutManager linearLayoutManager;
    private WordsAdapter wordsAdapter;


@Override
public void onCreate(@NonNull Bundle saveInstanceState){
    super.onCreate(saveInstanceState);
    activity=getActivity();
    wordsAdapter=new WordsAdapter();


}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=LayoutInflater.from(activity).inflate(R.layout.fragment_wrods,container,false);

        return view;
    }




}
