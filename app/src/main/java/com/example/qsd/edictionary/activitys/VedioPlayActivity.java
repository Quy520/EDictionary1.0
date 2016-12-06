package com.example.qsd.edictionary.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.example.qsd.edictionary.R;

import com.example.qsd.edictionary.fragment.DetialsCourse;
import com.example.qsd.edictionary.fragment.DetialsFragment;

import java.util.ArrayList;
import java.util.List;

public class VedioPlayActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> list;
    private List<String> mtab;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_play);
        initView();
        initOnClick();

    }

    private void initOnClick() {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
    }

    private void initView() {
        imageView= (ImageView) findViewById(R.id.vedio_share);
       list=new ArrayList<>();
        mtab=new ArrayList<>();
        viewPager= (ViewPager) findViewById(R.id.vp_vedioplay);
        tabLayout= (TabLayout) findViewById(R.id.vedioplay);
        list.add(new DetialsFragment());
        list.add(new DetialsCourse());
        Log.i("qsd",list.size()+"");
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
        tabLayout.setupWithViewPager(viewPager);
        mtab.add("解释");
        mtab.add("相关单词");
        for(int i=0;i<mtab.size();i++){
            TabLayout.Tab tab1=tabLayout.getTabAt(i);
            tab1.setText(mtab.get(i));
        }


    }
}
