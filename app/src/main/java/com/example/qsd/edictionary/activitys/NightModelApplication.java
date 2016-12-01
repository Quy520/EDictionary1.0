package com.example.qsd.edictionary.activitys;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import com.example.qsd.edictionary.utils.AppConfig;


/**
 * 全局
 *
 * */
public class NightModelApplication extends Application {
    public static MediaPlayer mediaPlayer;
    public static Context mContext;
    public static AppConfig appConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        appConfig = new AppConfig(mContext);

    }
    public static MediaPlayer getMediaPlayer(){
        if (mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
        }
        return mediaPlayer;
    }
    public static void setMediaPlayerNull(){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

}