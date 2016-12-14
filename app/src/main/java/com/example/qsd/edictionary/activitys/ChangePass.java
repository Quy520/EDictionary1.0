package com.example.qsd.edictionary.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
//我的里面的更新密码
public class ChangePass extends AppCompatActivity {
    private EditText oldpass,newpass;
    private TextView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        initView();
    }

    private void initView() {
        oldpass= (EditText) findViewById(R.id.old_pass);
        newpass= (EditText) findViewById(R.id.new_pass);
        button= (TextView) findViewById(R.id.pass_commit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangePass.this, "提交", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
