package com.example.qsd.edictionary.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.utils.Utils;

public class ChangeNewphone extends AppCompatActivity {
    private Button button;
    private TextView commit;
    private EditText phone,code,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_newphone);
        initView();
        initOnClick();
    }

    private void initOnClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString().trim();
                if (Utils.isnumber(phoneNumber)) {
                    Toast.makeText(ChangeNewphone.this, "已发送验证码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangeNewphone.this, "亲 ~,别闹,你手机号输错了", Toast.LENGTH_SHORT).show();
                }
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangeNewphone.this, "提交", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        button= (Button) findViewById(R.id.Verification);
        commit= (TextView) findViewById(R.id.phone_commit);
        phone= (EditText) findViewById(R.id.et_phone);
        code= (EditText) findViewById(R.id.et_code);
        pass= (EditText) findViewById(R.id.et_pass);
    }

}
