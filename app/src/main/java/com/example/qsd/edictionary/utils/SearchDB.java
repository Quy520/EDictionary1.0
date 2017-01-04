package com.example.qsd.edictionary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * Created by Administrator on 2015/11/16.
 */
public class SearchDB {

    public static String createDb(Context context, String user_Name) {

        SharedPreferences preferences = context.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
        String name = preferences.getString("nickname", "qsd");
        return name;

    }
    public static String createPh(Context context, String user_Name) {

        SharedPreferences preferences = context.getSharedPreferences("ED", Context.MODE_PRIVATE);
        String name = preferences.getString("userName", "15551111111");
        return name;

    }

    public static String TouXiangDb(Context context, String pic_Path) {
        String pic_Pathload = null;
        SharedPreferences preferences = context.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String pic_path = preferences.getString("pic_path", null);
        if (pic_path != null) {
            pic_Pathload = pic_path;
            String pic_pathload = "storage/sdcard0/image" + pic_Pathload;
            return pic_pathload;
        }
        return pic_Pathload;

    }

    //删除数据
    public static void removeDb(SharedPreferences preferences) {
        preferences.edit().remove("user_Name").commit();
    }
}
