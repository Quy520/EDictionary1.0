package com.example.qsd.edictionary.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * Created by QSD on 2016/12/3.
 */

public class NightModeUtils {
    //调节亮度
//    public static void setBrightness(Activity activity , float brightnessValue)
//    {
//        Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
//        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessValue);
//        activity.getContentResolver().notifyChange(uri, null);null
//    }
    public static void saveBrightness(ContentResolver resolver, int brightness) {
        Uri uri = android.provider.Settings.System
                .getUriFor("screen_brightness");
        android.provider.Settings.System.putInt(resolver, "screen_brightness",
                brightness);
        // resolver.registerContentObserver(uri, true, myContentObserver);
        resolver.notifyChange(uri, null);
    }

    //获取系统亮度值
    public static int getSystemBrightness(Context context)
    {
        int brightnessValue = -1;
        try
        {
            brightnessValue = Settings.System.
                    getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return brightnessValue;
    }


    /**
     * 是否打开自动调节亮度
     * @param
     * @return
     */
    public static boolean isAutoBrightness(Activity activity) {
        boolean autoBrightness = false;
        ContentResolver contentResolver = activity.getContentResolver();

            try {
                autoBrightness = Settings.System.getInt(contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

        return autoBrightness;
    }

    /* 停止自动调节亮度
    * @param activity
    */
    public static void closeAutoBrightness(Activity activity)
    {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Uri uri = android.provider.Settings.System.getUriFor("screen_brightness");
        activity.getContentResolver().notifyChange(uri, null);
    }

    /**
     * d打开自动调节亮度
     * @param activity
     */
    public static void openAutoBrightness(Activity activity)
    {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        Uri uri = android.provider.Settings.System.getUriFor("screen_brightness");
        activity.getContentResolver().notifyChange(uri, null);
    }

}
