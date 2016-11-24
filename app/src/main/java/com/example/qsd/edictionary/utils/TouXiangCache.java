package com.example.qsd.edictionary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by QSD on 2016/11/24.
 */

public class TouXiangCache {
    public static void saveMyBitmap(Bitmap bitmap,String pic_path){
        File file=new File("storage/sdcard0/"+pic_path);
        FileOutputStream fileOutputStream=null;
        try{
            fileOutputStream=new FileOutputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
        try {
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap getphoto(String pic_path){
        Bitmap bitmap= BitmapFactory.decodeFile(pic_path);
        return bitmap;
    }
}
