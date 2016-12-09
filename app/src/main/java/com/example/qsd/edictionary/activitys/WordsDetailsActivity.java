package com.example.qsd.edictionary.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.fragment.VideoFragment;
import com.example.qsd.edictionary.fragment.WordFragment;
import com.example.qsd.edictionary.fragment.oneWordFragment;
import com.example.qsd.edictionary.fragment.words_VedioFragment;

import java.util.ArrayList;
import java.util.List;

public class WordsDetailsActivity extends AppCompatActivity {
    private List<Fragment> list;
    private List<String> mtab;
    View view;
    private ViewPager viewPager;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_details);
        initView();
    }

    private void initView() {
        list=new ArrayList<>();
        mtab=new ArrayList<>();
        viewPager= (ViewPager)findViewById(R.id.wordsdetails_pager);
        tab= (TabLayout)findViewById(R.id.tab);
        list.add(new words_VedioFragment());
        list.add(new oneWordFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        tab.setupWithViewPager(viewPager);
        mtab.add("视屏");
        mtab.add("每个单词");
        for(int i=0;i<mtab.size();i++){
            TabLayout.Tab tab1=tab.getTabAt(i);
            tab1.setText(mtab.get(i));
        }
    }
}
