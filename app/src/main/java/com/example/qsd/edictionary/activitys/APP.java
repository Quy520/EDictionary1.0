package com.example.qsd.edictionary.activitys;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import com.example.qsd.edictionary.utils.AppConfig;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * 全局
 *
 * */
public class APP extends Application {
    public static MediaPlayer mediaPlayer;
    public static Context mContext;
    public static AppConfig appConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        mContext = getApplicationContext();
        appConfig = new AppConfig(mContext);
        PlatformConfig.setQQZone("1105869116","ilDAejHB0cXzR47t");
        PlatformConfig.setSinaWeibo("1292322940", "c1ad238284f47072b0caaf27d4d3afb3");
        

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