package com.example.qsd.edictionary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by QSD on 2017/1/12.
 */

public class SharedpreferencesUtils {
    //保存学习豆和消费学习豆
    public static void SaveStudyCode(Context context,int studyBean,int cost){
        SharedPreferences  sharedPreferences=context.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor userinfo = sharedPreferences.edit();
        userinfo.putInt("studyBean",studyBean)//学习豆
                .putInt("costStudyBean",cost)
                .commit();
    }


}
