package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;

import java.io.File;
//头像的修改
public class PhotoChange extends AppCompatActivity {
    private ImageView headImage=null;
    private Button button1,button2;
    //头像文件
    private static final String IMAGE_FILE_NAME="head_image.jpg";
    //请求识别码
    private static final int CODE_GALLERY_REQUEST=0xa0;
    private static final int CODE_CAMERA_REQUEST=0xa1;
    private static final int CODE_RESULT_REQUEST=0xa2;
    //裁剪后的图片宽高
    private static int output_x=100;
    private static int output_y=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);
        initView();

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

    public void choseHeadImageFromGallery() {


    }

    public void choseHeadImageFromCapture() {
        Intent intentfromCaputure=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断储存卡是否可用，并且保存照片
        if (hasSDcard()){
            intentfromCaputure.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME)));
        }
        startActivityForResult(intentfromCaputure,CODE_CAMERA_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==RESULT_CANCELED){
            Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode){
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;
            case CODE_CAMERA_REQUEST:
               if (hasSDcard()){
                   File file=new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
                   cropRawPhoto(Uri.fromFile(file));
               }else{
                   Toast.makeText(this, "没有SD卡", Toast.LENGTH_SHORT).show();
               }
                break;
            case CODE_RESULT_REQUEST:
               if (intent!=null){
                   setImageToHeadView(intent);
               }
                break;
        }
        super.onActivityResult(requestCode,resultCode,intent);

    }
//提取保存的图片后，设置头像
    private void setImageToHeadView(Intent intent) {

    }

    private void cropRawPhoto(Uri uri) {
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //设置宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪的宽高
        intent.putExtra("outputX",output_x);
        intent.putExtra("outputY",output_y);
        intent.putExtra("return_data",true);
        startActivityForResult(intent,CODE_RESULT_REQUEST);
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
