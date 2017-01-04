package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 互学码界面
 */
public class StudyCodeActivity extends AppCompatActivity {
    private Button code,send;
    private TextView copy;
    private ListView listView;
    private List<String > infolist;
    private String studycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_code);
        initView();
        initData();
        setAdapter();
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String copycody=code.getText().toString().trim();
                Toast.makeText(StudyCodeActivity.this, copycody+"已复制", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter() {
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,infolist);
        listView.setAdapter(adapter);
    }

    private void initData() {
        infolist=new ArrayList<>();
        Intent intent=getIntent();
        String stringValue=intent.getStringExtra("code");
        code.setText(stringValue);
        infolist.add("用户132*****123，加入易词典，你获得了5个学豆");
        infolist.add("用户182*****129，加入易词典，你获得了5个学豆");
        infolist.add("用户131*****123，加入易词典，你获得了5个学豆");
        infolist.add("用户130*****123，加入易词典，你获得了5个学豆");
        infolist.add("用户132*****123，加入易词典，你获得了5个学豆");
        infolist.add("用户182*****129，加入易词典，你获得了5个学豆");
        infolist.add("用户131*****123，加入易词典，你获得了5个学豆");
        infolist.add("用户130*****123，加入易词典，你获得了5个学豆");
        infolist.add("用户132*****123，加入易词典，你获得了5个学豆");
        infolist.add("用户182*****129，加入易词典，你获得了5个学豆");
        infolist.add("用户131*****123，加入易词典，你获得了5个学豆");
        infolist.add("用户130*****123，加入易词典，你获得了5个学豆");
    }

    private void initView() {
        code= (Button) findViewById(R.id.button_code);
        send= (Button) findViewById(R.id.send_button);
        copy= (TextView) findViewById(R.id.copy);
        listView= (ListView) findViewById(R.id.listview);

    }
}
