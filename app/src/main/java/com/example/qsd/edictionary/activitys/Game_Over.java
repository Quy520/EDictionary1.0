package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.qsd.edictionary.R;

public class Game_Over extends AppCompatActivity {
private Button Continue,Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__over);
        initView();
        initOnClick();
    }

    private void initOnClick() {
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Game_Over.this,GameGreadActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Game_Over.this,GameGreadActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void initView() {
        Continue= (Button) findViewById(R.id.gameover_continue);
        Back= (Button) findViewById(R.id.gameover_fanhui);
    }
}
