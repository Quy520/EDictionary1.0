package com.example.qsd.edictionary.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.qsd.edictionary.R;

public class RechargeMoneyActivity extends Activity {
    private TextView study,money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s = getIntent().getExtras().getString("study");
        String m = getIntent().getExtras().getString("money");
        setContentView(R.layout.activity_recharge_money);
        initView();
        study.setText(s);
        money.setText(m);
    }

    private void initView() {
        study= (TextView) findViewById(R.id.study);
        money= (TextView) findViewById(R.id.money_charge);
    }
}
