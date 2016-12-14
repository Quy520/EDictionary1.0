package com.example.qsd.edictionary.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by QSD on 2016/12/6.
 */

public class VedioplayAdapter extends PagerAdapter {
    private Context context;
    private List<Fragment> list;
    public VedioplayAdapter(Context context, List<Fragment > Fragmetlist) {
        this.context=context;
        this.list=Fragmetlist;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
