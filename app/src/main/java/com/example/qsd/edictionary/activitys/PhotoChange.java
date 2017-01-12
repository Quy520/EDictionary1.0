package com.example.qsd.edictionary.activitys;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.utils.PermisionUtils;
import com.example.qsd.edictionary.utils.SearchDB;
import com.example.qsd.edictionary.utils.TouXiangCache;

import java.io.File;
import java.io.FileNotFoundException;

//头像的修改
public class PhotoChange extends AppCompatActivity {
    private ImageView headImage;
    private Button button1,button2;
    Bitmap bp;
    private static final String IMAGE_FILE_NAME = "head_image.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    private String pic_path,extr;
    //创建一个以当前时间为名称的文件
    File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取头像
        PermisionUtils.verifyStoragePermissions(this);//动态添加权限
        extr = Environment.getExternalStorageDirectory().getAbsolutePath();
        pic_path=SearchDB.TouXiangDb(this,IMAGE_FILE_NAME);
        setContentView(R.layout.activity_photo_change);
        initView();
        if (pic_path!=null){
            Bitmap getphoto = TouXiangCache.getphoto( pic_path);
            headImage.setImageBitmap(getphoto);
        }

    }


    private void initView() {
        headImage= (ImageView) findViewById(R.id.userimage);
        button1= (Button) findViewById(R.id.from_phone);
        button2= (Button) findViewById(R.id.takephono);
        //从图库选择
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });
        //从相机拍照
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromCapture();
            }
        });

    }
    //从相册选
    public void choseHeadImageFromGallery() {
        Log.i("qsd","从相册选");
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CODE_GALLERY_REQUEST);
    }

    public void choseHeadImageFromCapture() {
        Log.i("qsd","拍照");
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(tempFile));
            Log.i("qsd","保存图片地址"+tempFile);
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==RESULT_CANCELED){
            Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode){
            case CODE_GALLERY_REQUEST:
                if (intent != null)
                cropRawPhoto(intent.getData(),250);
                break;
            case CODE_CAMERA_REQUEST:
                if (hasSDcard()){
                    Toast.makeText(this, "正在调用相机", Toast.LENGTH_SHORT).show();
                    cropRawPhoto(Uri.fromFile(tempFile),250);
                }else{
                    Toast.makeText(this, "没有SD卡", Toast.LENGTH_SHORT).show();
                }
                break;
            case CODE_RESULT_REQUEST:
                if (intent!=null){
                    Log.i("qsd","进行保存");
                    setImageToHeadView(intent);
                }
                break;

        }
        super.onActivityResult(requestCode,resultCode,intent);

    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri,int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    //提取保存的图片后，设置头像

    private void setImageToHeadView(Intent intent) {
        Bundle bundle=intent.getExtras();
        Log.i("qsd",bundle.getParcelable("data")+"1111222");
        if (bundle!=null){
            final Bitmap photo=bundle.getParcelable("data");
            headImage.setImageBitmap(photo);
            bp=photo;
            TouXiangCache.saveMyBitmap(bp,pic_path);
            PermisionUtils.verifyStoragePermissions(this);
            String touxiang = SearchDB.TouXiangDb(this, pic_path);//
            if (touxiang==null){
                SharedPreferences sharedPreferences=this.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("pic_path",pic_path).commit();
            }
            Log.i("qsd","bitMappath-----"+touxiang);

        }

    }
    private boolean hasSDcard() {
        String state= Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else {
            return false;
        }

    }
}