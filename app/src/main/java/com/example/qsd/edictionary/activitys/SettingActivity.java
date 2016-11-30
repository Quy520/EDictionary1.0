package com.example.qsd.edictionary.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.qsd.edictionary.MainActivity;
import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private Switch night,notice,down;
    private List<TextView> allTextViewList;
    LinearLayout layout,linearLayout;
    private boolean isNight=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NightModelApplication.appConfig.getNightModeSwitch()) {
            this.setTheme(R.style.Theme_setting_night);
            isNight = true;
        } else {
            this.setTheme(R.style.Theme_setting_day);
            isNight = false;
        }

        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        night= (Switch) findViewById(R.id.night_switch);
        notice= (Switch) findViewById(R.id.notice_switch);
        down= (Switch) findViewById(R.id.dowm_switch);
        linearLayout= (LinearLayout) findViewById(R.id.activity_setting);
        layout= (LinearLayout) findViewById(R.id.setting_clear);
        night.setChecked(isNight);
        night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NightModelApplication.appConfig.setNightModeSwitch(isChecked);
                changeSkinMode(isChecked);
            }
        });
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
            textColor = getResources().getColor(R.color.text_night);
        }
        if (allTextViewList != null && textColor != 0) {
            for (TextView textView : allTextViewList) {
                textView.setTextColor(textColor);
            }
        }

    }

}
