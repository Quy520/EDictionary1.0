package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.utils.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayGameActivity extends AppCompatActivity {
    private TextView textView1,textView2,textView3;
    private List<String > strings;
    private Timer executeSchedule;
    private Button time,grade,NO;
    private int i=0;
    private int j=0;
    private double T;
    private int Time;
    private ArrayList<String> list;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x111) {
                if (i==1){
                    textView1.setVisibility(View.INVISIBLE);
                    textView2.setText(strings.get(i-1));//0
                    textView3.setText(strings.get(i));//1
                }else if(i==20){
                    textView1.setText(strings.get(i-2));
                    textView2.setText(strings.get(i-1));
                    textView3.setVisibility(View.INVISIBLE);
                }else{
                    textView1.setVisibility(View.VISIBLE);
                    textView1.setText(strings.get(i-2));
                    textView2.setText(strings.get(i-1));
                    textView3.setText(strings.get(i));
                }

                return;
            }
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        list=new ArrayList<>();
        initView();
        for (int i=0;i<20;i++){
                    String random = RandomStringUtils.random(2, "1234567890");
                    strings.add(random);
                    Log.i("qsd","随机数："+random);
        }
        final Intent intent=getIntent();
        int time = intent.getIntExtra("time", 1000);
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(time*1000,1000);
        Log.i("qsd","传过来的数据"+time);
         T=(double) time/20.00;
        Log.i("qsd","每个需要多长事件"+T);
         Time=(int)(T*1000);
        Log.i("qsd","时间计算"+Time);
        int grade_text=intent.getIntExtra("grade",0);
        myCountDownTimer.start();
        grade.setText("等级"+grade_text);
        executeSchedule.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0x111;
                if (i<20){
                    i++;
                    handler.sendMessage(message);
                }else{
                    executeSchedule.cancel();
                    Log.i("qsd","任务取消"+i);
                    if (j<=9&&j>0){
                        Intent intent2=new Intent(PlayGameActivity.this,GamejieguoActivity.class);
                        intent2.putStringArrayListExtra("image", list);
                        startActivity(intent2);
                        finish();
                    }else if (j==0){
                        Intent intent1=new Intent(PlayGameActivity.this,Game_SuccessActivity.class);
                        startActivity(intent1);
                        finish();
                    }

                }
                Log.i("qsd","执行次数"+i);
            }
        }, 0,Time);
        onClick();

    }
    private void onClick() {
        NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 j++;
                String text = textView2.getText().toString();
                list.add(text);
                if (j>9){
                    Intent intent1=new Intent(PlayGameActivity.this,Game_Over.class);
                    startActivity(intent1);
                    finish();
                }
                Log.i("qsd","忘记的单词"+text);
            }
        });

    }

    private void initView() {
        strings=new ArrayList<>();
        executeSchedule = new Timer();
        time= (Button) findViewById(R.id.button_game);
        grade= (Button) findViewById(R.id.button_grade);
        NO= (Button) findViewById(R.id.game_NO);
        textView1= (TextView) findViewById(R.id.first);
        textView2= (TextView) findViewById(R.id.sencond);
        textView3= (TextView) findViewById(R.id.third);

    }
    @Override
    public void onDestroy(){
       executeSchedule.cancel();
        super.onDestroy();

    }
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            time.setText(l/1000+"s");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            time.setText("时间结束");

        }
    }


}
