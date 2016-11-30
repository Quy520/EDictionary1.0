package com.example.qsd.edictionary.activitys;

import android.app.Application;
import android.content.Context;

import com.example.qsd.edictionary.utils.AppConfig;


/**
 * 全局
 *
 * */
public class NightModelApplication extends Application {

    public static Context mContext;
    public static AppConfig appConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        appConfig = new AppConfig(mContext);

    }
}