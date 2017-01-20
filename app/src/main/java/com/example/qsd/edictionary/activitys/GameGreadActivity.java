package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.qsd.edictionary.R;

public class GameGreadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button60,button50,button40,button30,button25,button20,
            button15,button13,button10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_gread);
        initView();
        initonClick();
    }

    private void initonClick() {
        button10.setOnClickListener(this);
        button13.setOnClickListener(this);
        button15.setOnClickListener(this);
        button20.setOnClickListener(this);
        button25.setOnClickListener(this);
        button30.setOnClickListener(this);
        button40.setOnClickListener(this);
        button50.setOnClickListener(this);
        button60.setOnClickListener(this);
    }

    private void initView() {
        button10= (Button) findViewById(R.id.button10);
        button13= (Button) findViewById(R.id.button13);
        button15= (Button) findViewById(R.id.button15);
        button20= (Button) findViewById(R.id.button20);
        button25= (Button) findViewById(R.id.button25);
        button30= (Button) findViewById(R.id.button30);
        button40= (Button) findViewById(R.id.button40);
        button50= (Button) findViewById(R.id.button50);
        button60= (Button) findViewById(R.id.button60);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button10:
                Intent intent=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent.putExtra("time",10);
                intent.putExtra("grade",9);
                startActivity(intent);
                finish();
                break;
            case R.id.button13:
                Intent intent2=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent2.putExtra("time",13);
                intent2.putExtra("grade",8);
                startActivity(intent2);
                finish();
                break;
            case R.id.button15:
                Intent intent3=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent3.putExtra("time",15);
                intent3.putExtra("grade",7);
                startActivity(intent3);
                finish();
                break;
            case R.id.button20:
                Intent intent4=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent4.putExtra("time",20);
                intent4.putExtra("grade",6);
                startActivity(intent4);
                finish();
                break;
            case R.id.button25:
                Intent intent5=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent5.putExtra("time",25);
                intent5.putExtra("grade",5);
                startActivity(intent5);
                finish();
                break;
            case R.id.button30:
                Intent intent6=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent6.putExtra("time",30);
                intent6.putExtra("grade",4);
                startActivity(intent6);
                finish();
                break;
            case R.id.button40:
                Intent intent7=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent7.putExtra("time",40);
                intent7.putExtra("grade",3);
                startActivity(intent7);
                finish();
                break;
            case R.id.button50:
                Intent intent8=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent8.putExtra("time",50);
                intent8.putExtra("grade",2);
                startActivity(intent8);
                finish();
                break;
            case R.id.button60:
                Intent intent9=new Intent(GameGreadActivity.this,PlayGameActivity.class);
                intent9.putExtra("time",60);
                intent9.putExtra("grade",1);
                startActivity(intent9);
                finish();
                break;


        }
    }
}
