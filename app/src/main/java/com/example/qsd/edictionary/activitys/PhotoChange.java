package com.example.qsd.edictionary.activitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.os.Parcelable;
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
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 2;
    private static int output_Y = 2;
    private String pic_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取头像
        PermisionUtils.verifyStoragePermissions(this);
        pic_path=SearchDB.TouXiangDb(this,IMAGE_FILE_NAME);
        if (pic_path!=null){
            Log.i("qsd","pic_path"+pic_path);
            Bitmap getphoto = TouXiangCache.getphoto("storage/sdcard0/image"+ pic_path);
            headImage.setImageBitmap(getphoto);
        }
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
        if (hasSDcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }
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
                Toast.makeText(this, "正在跳转相册", Toast.LENGTH_SHORT).show();
                cropRawPhoto(intent.getData());
                break;
            case CODE_CAMERA_REQUEST:
                if (hasSDcard()){
                    Toast.makeText(this, "正在调用相机", Toast.LENGTH_SHORT).show();
                    File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                    //Bitmap data = intent.getParcelableExtra("data");
                    //startActivityForResult(intent, CODE_RESULT_REQUEST);
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
            case 3:
                Bitmap photo1 = intent.getParcelableExtra("data");
                if(photo1!=null){
                    headImage.setImageBitmap(photo1);
                }
        }
        super.onActivityResult(requestCode,resultCode,intent);

    }

//    private void doCropPhoto(Bitmap data) {
//        Intent intent = getCropImageIntent(data);
//        startActivityForResult(intent, 3);
//    }
    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }



    //提取保存的图片后，设置头像
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setImageToHeadView(Intent intent) {
        Bundle bundle=intent.getExtras();
        Log.i("qsd",bundle.getParcelable("data")+"1111222");
        if (bundle!=null){
            final Bitmap photo=bundle.getParcelable("data");
            headImage.setImageBitmap(photo);
            bp=photo;
            pic_path=IMAGE_FILE_NAME;
            //把图片保存到sd卡中
            PermisionUtils.verifyStoragePermissions(this);
            TouXiangCache.saveMyBitmap(bp,pic_path);
            Log.i("qsd","保存成功1");
            String touxiang = SearchDB.TouXiangDb(this, pic_path);
            if (touxiang==null){
                Log.i("qsd","保存成功2");
                SharedPreferences sharedPreferences=this.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("pic_path",pic_path).commit();
            }



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