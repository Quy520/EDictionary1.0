package com.example.qsd.edictionary.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.UserinfoActivity;

/**
 * 我的界面
 */
public class MineFragment extends Fragment implements View.OnClickListener {
        View view;
        RelativeLayout name,study,game,code,notice,about,setting;

        public MineFragment() {

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
            return view;
        }

        private void initView(View view) {
            name= (RelativeLayout) view.findViewById(R.id.mine_user);
            study= (RelativeLayout) view.findViewById(R.id.mine_study);
            game= (RelativeLayout) view.findViewById(R.id.mine_game);
            code= (RelativeLayout) view.findViewById(R.id.mine_code);
            notice= (RelativeLayout) view.findViewById(R.id.mine_notice);
            about= (RelativeLayout) view.findViewById(R.id.mine_about);
            setting= (RelativeLayout) view.findViewById(R.id.mine_setting);
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

                    break;
                case R.id.mine_game:
                    Toast.makeText(getActivity(), "游戏", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_code:
                    Toast.makeText(getActivity(), "学习", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_notice:
                    Toast.makeText(getActivity(), "通知", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_about:
                    Toast.makeText(getActivity(), "关于", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_setting:
                    Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }
