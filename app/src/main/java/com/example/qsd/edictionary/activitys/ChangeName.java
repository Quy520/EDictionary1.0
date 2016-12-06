package com.example.qsd.edictionary.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;

public class ChangeName extends AppCompatActivity {
    private EditText editText;
    private TextView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        editText= (EditText) findViewById(R.id.changename);
        button= (TextView) findViewById(R.id.name_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=editText.getText().toString();
//                Intent intent=new Intent(ChangeName.this,UserinfoActivity.class);
//                intent.putExtra("name",name);
                SharedPreferences sharedPreferences=getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("userName",name).commit();
                Toast.makeText(ChangeName.this, "修改成功", Toast.LENGTH_SHORT).show();
               // startActivity(intent);
                finish();

            }
        });


    }
}
