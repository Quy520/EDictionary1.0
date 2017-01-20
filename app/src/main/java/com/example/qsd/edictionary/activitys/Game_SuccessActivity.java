package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.qsd.edictionary.R;

public class Game_SuccessActivity extends AppCompatActivity {
private Button Contionue,Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__success);
        initView();
        initClick();
    }

    private void initClick() {
        Contionue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Game_SuccessActivity.this,GameGreadActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Game_SuccessActivity.this,GameGreadActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        Contionue= (Button) findViewById(R.id.game_continue);
        Back= (Button) findViewById(R.id.game_fanhui);
    }
}
