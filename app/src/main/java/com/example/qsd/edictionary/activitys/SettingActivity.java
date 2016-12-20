package com.example.qsd.edictionary.activitys;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.utils.NightModeUtils;

import java.util.List;
//我的里面的设置界面
public class SettingActivity extends AppCompatActivity {
    private Switch night,notice,down;
    private List<TextView> allTextViewList;
    LinearLayout layout,linearLayout;
    private boolean isNight=false;
    private Button button;
    //ContentResolver resolver=getContentResolver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (APP.appConfig.getNightModeSwitch()) {
            isNight=true;
        } else {
            isNight = false;
        }
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        button= (Button) findViewById(R.id.out);
        night= (Switch) findViewById(R.id.night_switch);
        notice= (Switch) findViewById(R.id.notice_switch);
        down= (Switch) findViewById(R.id.dowm_switch);

        layout= (LinearLayout) findViewById(R.id.setting_clear);

        night.setChecked(isNight);

        night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                APP.appConfig.setNightModeSwitch(isChecked);
                changeSkinMode(isChecked);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("提示")
                        .setMessage("确认退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(SettingActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

    private void changeSkinMode(boolean isChecked) {
        int Sbright= NightModeUtils.getSystemBrightness(this);
        if (isChecked){
            Log.i("qsd","系统亮度=="+Sbright);
            boolean open=NightModeUtils.isAutoBrightness(this);
            if (open){
                NightModeUtils.closeAutoBrightness(this);
                Log.i("qsd","关闭自动调节模式"+open);
               // NightModeUtils.setBrightness(this,0.0f);
                NightModeUtils.saveBrightness(getContentResolver(),0);
                isNight=isChecked;
            }else{
                Log.i("qsd","settingActivity"+open);
               // NightModeUtils.setBrightness(this,0.0f);
                NightModeUtils.saveBrightness(getContentResolver(),0);
                isNight=isChecked;
            }
            APP.appConfig.setNightModeSwitch(isNight);

        }else{
            Log.i("qsd","settingActivity"+"12314");
            NightModeUtils.openAutoBrightness(this);
            //NightModeUtils.setBrightness(this,Sbright);
            NightModeUtils.saveBrightness(getContentResolver(),200);
            isNight=isChecked;
            APP.appConfig.setNightModeSwitch(isNight);

        }
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
//
//    }

}
