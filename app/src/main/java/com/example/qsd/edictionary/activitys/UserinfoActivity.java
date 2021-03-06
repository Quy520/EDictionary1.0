package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.utils.PermisionUtils;
import com.example.qsd.edictionary.utils.SearchDB;
import com.example.qsd.edictionary.utils.TouXiangCache;


/**
 * 用户修改信息界面
 */
public class UserinfoActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout info_image,info_phone,info_pass,info_name;
    LinearLayout linearLayout;
    private TextView tv_name,phone;
    private ImageView head;
    private String pic_path,name,phone_,extr;
    private static final String IMAGE_FILE_NAME = "head_image.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermisionUtils.verifyStoragePermissions(this);
        extr = Environment.getExternalStorageDirectory().toString();
        name=SearchDB.createDb(this,"nickname");
        Log.i("qsd","name"+name);
        phone_=SearchDB.createPh(this,"userName");
        pic_path=SearchDB.TouXiangDb(this,IMAGE_FILE_NAME);
        setContentView(R.layout.activity_userinfo);
        initView();
        if (pic_path!=null){
            Log.i("qsd","pic_path3"+pic_path);
            Bitmap getphoto = TouXiangCache.getphoto(pic_path);
            head.setImageBitmap(getphoto);
        }
        initOnClick();
    }

    private void initOnClick() {
        info_name.setOnClickListener(this);
        info_phone.setOnClickListener(this);
        info_pass.setOnClickListener(this);
        info_image.setOnClickListener(this);

    }

    private void initView() {
        linearLayout= (LinearLayout) findViewById(R.id.activity_userinfo);
        info_phone= (RelativeLayout) findViewById(R.id.info_user);//
        info_pass= (RelativeLayout) findViewById(R.id.info_pass);
        info_image= (RelativeLayout) findViewById(R.id.info_image);
        info_name= (RelativeLayout) findViewById(R.id.info_name);
        head= (ImageView) findViewById(R.id.image_head);
        tv_name= (TextView) findViewById(R.id.name);
        phone= (TextView) findViewById(R.id.userinfo);

        tv_name.setText(name);
        phone.setText(phone_);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_user:
                Toast.makeText(this, "手机号码修改", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,ChangeNewphone.class);
                startActivity(intent);

                break;
            case R.id.info_pass:
                Toast.makeText(this, "密码修改", Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(this,ChangePass.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.info_image:
                Toast.makeText(this, "图片修改", Toast.LENGTH_SHORT).show();
                Intent intent3=new Intent(this,PhotoChange.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.info_name:
                Toast.makeText(this, "昵称修改", Toast.LENGTH_SHORT).show();
                Intent intent4=new Intent(this,ChangeName.class);
                startActivity(intent4);
                finish();
                break;

        }

    }
}
