package com.example.qsd.edictionary.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.AboutActivity;
import com.example.qsd.edictionary.activitys.FankuiActivity;
import com.example.qsd.edictionary.activitys.GameActivity;
import com.example.qsd.edictionary.activitys.NoticeActivity;
import com.example.qsd.edictionary.activitys.RechargeActivity;
import com.example.qsd.edictionary.activitys.SettingActivity;
import com.example.qsd.edictionary.activitys.StudyCodeActivity;
import com.example.qsd.edictionary.activitys.UserinfoActivity;
import com.example.qsd.edictionary.utils.SearchDB;
import com.example.qsd.edictionary.utils.TouXiangCache;

import java.util.List;

/**
 * 我的界面
 */
public class MineFragment extends Fragment implements View.OnClickListener {
        View view;
        LinearLayout linearLayout;
        RelativeLayout name,study,game,code,notice,about,fankui,setting;

        private ImageView imageView;
        private TextView studycode,costcode;
        private Context context;
        private int studyBean,costStudyBean;
        private static final String IMAGE_FILE_NAME = "head_image.jpg";

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
            //获取头像
            String pic_path = SearchDB.TouXiangDb(getContext(), IMAGE_FILE_NAME);
            if (pic_path!=null){
                Log.i("qsd","pic_path"+pic_path);
                Bitmap getphoto = TouXiangCache.getphoto("storage/sdcard0/"+ pic_path);
                imageView.setImageBitmap(getphoto);
            }
//            if (APP.appConfig.getNightModeSwitch()) {
//               // getContext().setTheme(R.style.Theme_setting_night);
//                isNight = true;
//                changeSkinMode(isNight);
//            } else {
//               // getContext().setTheme(R.style.Theme_setting_day);
//                isNight = false;
//                changeSkinMode(isNight);
//            }
            return view;
        }
//    private void changeSkinMode(boolean isNight) {
//        //changeMainActivity(isNight);
//        allTextViewList = ViewUtil.getAllTextView(ViewUtil.getAllChildView(linearLayout));
//        int textColor = 0;
//        if (isNight) {
//            linearLayout.setBackgroundColor(getResources().getColor(R.color.bg_night));
//            textColor = getResources().getColor(R.color.text_night);
//        } else {
//            linearLayout.setBackgroundColor(getResources().getColor(R.color.bg_day));
//            textColor = getResources().getColor(R.color.text_day);
//        }
//        if (allTextViewList != null && textColor != 0) {
//            for (TextView textView : allTextViewList) {
//                textView.setTextColor(textColor);
//            }
//        }
//    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);
//
//    studycode.setText(studyBean);
//    costcode.setText(costStudyBean);
}
        private void initView(View view) {
            context=getActivity();
            linearLayout= (LinearLayout) view.findViewById(R.id.mine_fragemnt);
            name= (RelativeLayout) view.findViewById(R.id.mine_user);
            study= (RelativeLayout) view.findViewById(R.id.mine_study);
            game= (RelativeLayout) view.findViewById(R.id.mine_game);
            code= (RelativeLayout) view.findViewById(R.id.mine_code);
            notice= (RelativeLayout) view.findViewById(R.id.mine_notice);
            about= (RelativeLayout) view.findViewById(R.id.mine_about);
            fankui= (RelativeLayout) view.findViewById(R.id.mine_fankui);
            setting= (RelativeLayout) view.findViewById(R.id.mine_setting);
            imageView= (ImageView) view.findViewById(R.id.mine_userimage);
            studycode= (TextView) view.findViewById(R.id.my_syudycode);
            costcode= (TextView) view.findViewById(R.id.my_costcode);

            //获取保存的数值
            SharedPreferences preferences = context.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
            studyBean = preferences.getInt("studyBean", 0);
            costStudyBean = preferences.getInt("costStudyBean", 0);
            Log.i("qsd",costStudyBean+"costStudyBean");
            Log.i("qsd",studyBean+"studyBean");
            costcode.setText(costStudyBean+"");
            studycode.setText(studyBean+"");
            initOnClick();

        }

        private void initOnClick() {
            name.setOnClickListener(this);
            study.setOnClickListener(this);
            game.setOnClickListener(this);
            code.setOnClickListener(this);
            notice.setOnClickListener(this);
            about.setOnClickListener(this);
            fankui.setOnClickListener(this);
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
                case R.id.mine_fankui:
                    Toast.makeText(getActivity(), "反馈", Toast.LENGTH_SHORT).show();
                    Intent intent7=new Intent(getActivity(), FankuiActivity.class);
                    startActivity(intent7);
                    break;
                case R.id.mine_setting:
                    Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
                    Intent intent8=new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent8);

                    break;
            }

        }
    }
