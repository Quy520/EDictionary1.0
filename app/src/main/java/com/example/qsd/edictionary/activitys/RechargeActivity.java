package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.qsd.edictionary.R;

/**
 * 充值界面
 */
public class RechargeActivity extends AppCompatActivity {
    private TextView problem,hide;
    private TextView call,quxiao;
    boolean isOut, isIn;// 是否弹窗显示
    String phoneNumber="15551899606";
    PopupWindow pop;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
        initonClick();

    }

    private void initonClick() {
        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RechargeActivity.this, "打电话", Toast.LENGTH_SHORT).show();
                //pop.showAtLocation(hide, Gravity.BOTTOM, 0, 0);
                pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT, true);
                pop.showAtLocation(hide,Gravity.BOTTOM,0,0);


                pop.setAnimationStyle(R.style.MenuAnimationFade);
                pop.setFocusable(true);
               pop.setTouchable(true);
                pop.setBackgroundDrawable(new BitmapDrawable());
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        //url:统一资源定位符
                        //uri:统一资源标示符（更广）
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        //开启系统拨号器
                        startActivity(intent);
                    }
                });
                quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    pop.dismiss();
                    }
                });


            }
        });
    }



    private void initView() {
        hide= (TextView) findViewById(R.id.hideview);
        problem= (TextView) findViewById(R.id.problem);
        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件 - 即弹窗的界面
        view = inflater.inflate(R.layout.menu_phone, null);
        call= (TextView) view.findViewById(R.id.call);
        quxiao= (TextView) view.findViewById(R.id.quxiao);


    }
}
