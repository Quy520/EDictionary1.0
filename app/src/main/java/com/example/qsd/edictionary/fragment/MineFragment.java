package com.example.qsd.edictionary.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.AboutActivity;
import com.example.qsd.edictionary.activitys.GameActivity;
import com.example.qsd.edictionary.activitys.NightModelApplication;
import com.example.qsd.edictionary.activitys.NoticeActivity;
import com.example.qsd.edictionary.activitys.RechargeActivity;
import com.example.qsd.edictionary.activitys.SettingActivity;
import com.example.qsd.edictionary.activitys.StudyCodeActivity;
import com.example.qsd.edictionary.activitys.UserinfoActivity;
import com.example.qsd.edictionary.utils.ViewUtil;

import java.util.List;

/**
 * 我的界面
 */
public class MineFragment extends Fragment implements View.OnClickListener {
        View view;
        LinearLayout linearLayout;
        RelativeLayout name,study,game,code,notice,about,setting;
        private List<TextView>  allTextViewList;
        boolean isNight;

        public MineFragment() {

        }
    @Override
    public void  onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);



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
            view=inflater.inflate(R.layout.fragment_mine,null,false);
            initView(view);
            if (NightModelApplication.appConfig.getNightModeSwitch()) {
               // getContext().setTheme(R.style.Theme_setting_night);
                isNight = true;
                changeSkinMode(isNight);

            } else {
               // getContext().setTheme(R.style.Theme_setting_day);
                isNight = false;
                changeSkinMode(isNight);
            }
            return view;
        }
    private void changeSkinMode(boolean isNight) {
        //changeMainActivity(isNight);
        allTextViewList = ViewUtil.getAllTextView(ViewUtil.getAllChildView(linearLayout));
        int textColor = 0;
        if (isNight) {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.bg_night));
            textColor = getResources().getColor(R.color.text_night);
        } else {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.bg_day));
            textColor = getResources().getColor(R.color.text_day);
        }
        if (allTextViewList != null && textColor != 0) {
            for (TextView textView : allTextViewList) {
                textView.setTextColor(textColor);
            }
        }
    }
        private void initView(View view) {
            linearLayout= (LinearLayout) view.findViewById(R.id.mine_fragemnt);
            name= (RelativeLayout) view.findViewById(R.id.mine_user);
            study= (RelativeLayout) view.findViewById(R.id.mine_study);
            game= (RelativeLayout) view.findViewById(R.id.mine_game);
            code= (RelativeLayout) view.findViewById(R.id.mine_code);
            notice= (RelativeLayout) view.findViewById(R.id.mine_notice);
            about= (RelativeLayout) view.findViewById(R.id.mine_about);
            setting= (RelativeLayout) view.findViewById(R.id.mine_setting);
           // allTextViewList = ViewUtil.getAllTextView(ViewUtil.getAllChildView(linearLayout));
            initOnClick();

        }

        private void initOnClick() {
            name.setOnClickListener(this);
            study.setOnClickListener(this);
            game.setOnClickListener(this);
            code.setOnClickListener(this);
            notice.setOnClickListener(this);
            about.setOnClickListener(this);
            setting.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mine_user:
                    Toast.makeText(getActivity(), "用户信息", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), UserinfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mine_study:
                    Toast.makeText(getActivity(), "学习豆", Toast.LENGTH_SHORT).show();
                    Intent intent2=new Intent(getActivity(), RechargeActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.mine_game:
                    Toast.makeText(getActivity(), "游戏", Toast.LENGTH_SHORT).show();
                    Intent intent3=new Intent(getActivity(), GameActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.mine_code:
                    Toast.makeText(getActivity(), "学习", Toast.LENGTH_SHORT).show();
                    Intent intent4=new Intent(getActivity(), StudyCodeActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.mine_notice:
                    Toast.makeText(getActivity(), "通知", Toast.LENGTH_SHORT).show();
                    Intent intent5=new Intent(getActivity(), NoticeActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.mine_about:
                    Toast.makeText(getActivity(), "关于", Toast.LENGTH_SHORT).show();
                    Intent intent6=new Intent(getActivity(), AboutActivity.class);
                    startActivity(intent6);
                    break;
                case R.id.mine_setting:
                    Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
                    Intent intent7=new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent7);
                    getActivity().finish();
                    break;
            }

        }
    }
