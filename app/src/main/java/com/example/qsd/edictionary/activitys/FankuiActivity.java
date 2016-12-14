package com.example.qsd.edictionary.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.example.qsd.edictionary.R;
//我的里面的反馈界面
public class FankuiActivity extends Activity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);
        initView();

    }

    private void initView() {
        button= (Button) findViewById(R.id.fankui_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FankuiActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
