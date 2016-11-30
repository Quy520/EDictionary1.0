package com.example.qsd.edictionary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by QSD on 2016/11/25.
 */

public class SubAdapter extends FragmentPagerAdapter {
    private List<Fragment > list;
    public SubAdapter(FragmentManager fm ,List<Fragment > Fragmetlist) {
        super(fm);
        this.list=Fragmetlist;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
