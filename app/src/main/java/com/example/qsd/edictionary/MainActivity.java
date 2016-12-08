package com.example.qsd.edictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.broadcastReceiver.NetReceiver;
import com.example.qsd.edictionary.fragment.MemoryFragment;
import com.example.qsd.edictionary.fragment.MineFragment;
import com.example.qsd.edictionary.fragment.SubscribeFragment;
import com.example.qsd.edictionary.fragment.WrodsFragment;
import com.example.qsd.edictionary.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentlist = null;
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private long timeMillis;
    public static MainActivity mMainActivity;
    private boolean isNight;
    private List<TextView>  allTextViewList;
    private NetReceiver netReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetState();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        netReceiver=new NetReceiver();
        registerReceiver(netReceiver,intentFilter);

        mMainActivity = this;
        initData();//填充数据
        initView();//填充布局
    }

    /**
     * 检查网络是否连接
     */
    private void checkNetState() {
        if(!NetUtils.isNetWork(this)){
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setTitle("网络状态提醒")
                    .setMessage("当前网络状态不可以，是否打开网络设置？？")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (android.os.Build.VERSION.SDK_INT>10){
                                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                            }else {
                                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                            }
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
        }
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
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(netReceiver);
    }


}
