package com.example.qsd.edictionary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

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
        String extr = Environment.getExternalStorageDirectory().toString();
        File f = new File(extr+"/"+ pic_pathload);
//        if (f.exists()){
//            Log.i("qsd","saveMyBitmap存在");
//        }else{
//            f.mkdirs();// 创建文件夹
//        }
//        //String fileName = "/storage/sdcard0/"+pic_pathload;
//        File file=new File(f,pic_pathload);
//       // File f = new File( "storage/sdcard0/"+pic_pathload);
//        try {
//            file.createNewFile();
//            Log.i("createNewFile","生成文件成功"+file.getName());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        FileOutputStream fOut = null;
        try {
             fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 250, fOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static  Bitmap getphoto(String pic_pathload){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize = 20;
        Bitmap bitmap = BitmapFactory.decodeFile(pic_pathload,options);
        return bitmap;
    }
}
