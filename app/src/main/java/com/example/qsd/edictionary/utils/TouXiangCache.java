package com.example.qsd.edictionary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/18.
 */
public class TouXiangCache {
    // TODO: 2015/11/18 保存图片

    public static void saveMyBitmap(Bitmap mBitmap, String pic_pathload)  {
        File f = new File( "storage/sdcard0/"+pic_pathload);

        FileOutputStream fOut = null;
        try {
             fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 2015/11/18 得到图片
    public static  Bitmap getphoto(String pic_pathload){
        Bitmap bitmap = BitmapFactory.decodeFile(pic_pathload);
        return bitmap;
    }
}
