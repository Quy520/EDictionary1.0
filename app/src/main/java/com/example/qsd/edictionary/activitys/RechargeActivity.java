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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.qsd.edictionary.R;

/**
 * 充值界面
 */
public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView problem,hide;
    private TextView call,quxiao,money_detail;
    boolean isOut, isIn;// 是否弹窗显示
    String phoneNumber="15551899606";
    PopupWindow pop;
    View view;
    private RelativeLayout money10,money50,money100,money500,money1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
        initonClick();

    }

    private void initonClick() {
        money_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RechargeActivity.this,RechargeDetail.class);
                startActivity(intent);
            }
        });
        money10.setOnClickListener(this);
        money50.setOnClickListener(this);
        money100.setOnClickListener(this);
        money500.setOnClickListener(this);
        money1000.setOnClickListener(this);


        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RechargeActivity.this, "打电话", Toast.LENGTH_SHORT).show();
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
        money_detail= (TextView) findViewById(R.id.money_detail);
        money10= (RelativeLayout) findViewById(R.id.money_10);
        money50= (RelativeLayout) findViewById(R.id.money_50);
        money100= (RelativeLayout) findViewById(R.id.money_100);
        money500= (RelativeLayout) findViewById(R.id.money_50);
        money1000= (RelativeLayout) findViewById(R.id.money_1000);




        hide= (TextView) findViewById(R.id.hideview);
        problem= (TextView) findViewById(R.id.problem);
        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件 - 即弹窗的界面
        view = inflater.inflate(R.layout.menu_phone, null);
        call= (TextView) view.findViewById(R.id.call);
        quxiao= (TextView) view.findViewById(R.id.quxiao);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.money_10:
                Intent intent=new Intent(RechargeActivity.this,RechargeMoneyActivity.class);
                Bundle b = new Bundle();
                b.putString("study", "10学习豆,");
                b.putString("money","10元");
                intent.putExtras( b );
                startActivity(intent);
                break;
            case R.id.money_50:
                Intent intent2=new Intent(RechargeActivity.this,RechargeMoneyActivity.class);
                Bundle b2 = new Bundle();
                b2.putString("study", "50学习豆");
                b2.putString("money","50元");
                intent2.putExtras(b2);
                startActivity(intent2);
                break;
            case R.id.money_100:
                Intent intent3=new Intent(RechargeActivity.this,RechargeMoneyActivity.class);
                Bundle b3 = new Bundle();
                b3.putString("study", "100学习豆,");
                b3.putString("money","100元");
                intent3.putExtras(b3);
                startActivity(intent3);
                break;
            case R.id.money_500:
                Intent intent4=new Intent(RechargeActivity.this,RechargeMoneyActivity.class);
                Bundle b4 = new Bundle();
                b4.putString("study", "500学习豆,");
                b4.putString("money","500元");
                intent4.putExtras(b4);
                startActivity(intent4);
                break;
        }

    }
}
