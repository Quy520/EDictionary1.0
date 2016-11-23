package com.example.qsd.edictionary.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

/**
 * Created by QSD on 2016/11/22.
 */

public class MypageAdapter extends PagerAdapter {
    private Context context;
    private List<View> mlist;
    public MypageAdapter(Context context,List<View> mlist){
        this.context=context;
        this.mlist=mlist;

    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return  view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position){
        View view=mlist.get(position);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((View)object);
    }


}
