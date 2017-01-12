package com.example.qsd.edictionary.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.adapter.SubAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 已订阅界面页面
 */
public class SubscribeFragment extends Fragment {
    private List<Fragment> list;
    private List<String> mtab;
    View view;
    private ViewPager viewPager;
    private TabLayout tab;
    public SubscribeFragment() {
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
        view=inflater.inflate(R.layout.fragment_subscribe,null,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        list=new ArrayList<>();
        mtab=new ArrayList<>();
        viewPager= (ViewPager) view.findViewById(R.id.sub_pager);
        tab= (TabLayout) view.findViewById(R.id.tab);
        list.add(new VideoFragment());
        list.add(new WordFragment());
        Log.i("qsd",list.size()+"");
        viewPager.setAdapter(new SubAdapter(getFragmentManager(),list));
        tab.setupWithViewPager(viewPager);
        mtab.add("视频");
        mtab.add("单词");
        for(int i=0;i<mtab.size();i++){
            TabLayout.Tab tab1=tab.getTabAt(i);
            tab1.setText(mtab.get(i));
        }


    }

}
