package com.example.qsd.edictionary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qsd.edictionary.fragment.MemoryFragment;
import com.example.qsd.edictionary.fragment.MineFragment;
import com.example.qsd.edictionary.fragment.SubscribeFragment;
import com.example.qsd.edictionary.fragment.WrodsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentlist = null;
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private long timeMillis;
    public static MainActivity mMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainActivity = this;
        initData();//填充数据
        initView();//填充布局
    }

    private void initView() {
        if(fragmentlist ==null){
            return;
        }
        viewPager= (ViewPager) findViewById(R.id.viewpage_main);
        radioGroup= (RadioGroup) findViewById(R.id.radiogroup_main);
        viewPager.setOffscreenPageLimit(4);//预加载界面个数
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentlist.get(position);
            }

            @Override
            public int getCount() {
                return fragmentlist.size();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_memory:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_words:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_subscribe:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_mine:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
        //默认初始状态
        radioGroup.check(R.id.rb_memory);

    }

    private void initData() {
        fragmentlist=new ArrayList<>();
        fragmentlist.add(new MemoryFragment());
        fragmentlist.add(new WrodsFragment());
        fragmentlist.add(new SubscribeFragment());
        fragmentlist.add(new MineFragment());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-timeMillis>2000){
                Toast.makeText(this, "在按一次退出程序", Toast.LENGTH_SHORT).show();
                timeMillis=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return  true;
        }
        return super.onKeyDown(keyCode,event);
    }

    public void changeSkinMode(boolean isNight) {

       // changeFragmentMode(isNight);
    }

}
