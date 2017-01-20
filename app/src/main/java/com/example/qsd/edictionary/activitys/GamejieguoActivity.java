package com.example.qsd.edictionary.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.qsd.edictionary.R;

import java.util.ArrayList;

public class GamejieguoActivity extends AppCompatActivity {
    private int i=3;
    private int[] images=new int[]{R.mipmap.image00,R.mipmap.image1,R.mipmap.image2,R.mipmap.image3,R.mipmap.image4,R.mipmap.image5,R.mipmap.image6,R.mipmap.image7,R.mipmap.image8,R.mipmap.image9,R.mipmap.image10,
            R.mipmap.image11,R.mipmap.image12,R.mipmap.image13,R.mipmap.image14,R.mipmap.image15,R.mipmap.image16,R.mipmap.image17,R.mipmap.image18,R.mipmap.image19,R.mipmap.image20,
            R.mipmap.image21,R.mipmap.image22,R.mipmap.image23,R.mipmap.image24,R.mipmap.image25,R.mipmap.image26,R.mipmap.image27,R.mipmap.image28,R.mipmap.image29,R.mipmap.image30,
            R.mipmap.image31,R.mipmap.image32,R.mipmap.image33,R.mipmap.image34,R.mipmap.image35,R.mipmap.image36,R.mipmap.image37,R.mipmap.image38,R.mipmap.image39,R.mipmap.image40,
            R.mipmap.image41,R.mipmap.image42,R.mipmap.image43,R.mipmap.image44,R.mipmap.image45,R.mipmap.image46,R.mipmap.image47,R.mipmap.image48,R.mipmap.image49,R.mipmap.image50,
            R.mipmap.image51,R.mipmap.image52,R.mipmap.image53,R.mipmap.image54,R.mipmap.image55,R.mipmap.image56,R.mipmap.image57,R.mipmap.image58,R.mipmap.image59,R.mipmap.image60,
            R.mipmap.image61,R.mipmap.image62,R.mipmap.image63,R.mipmap.image64,R.mipmap.image65,R.mipmap.image66,R.mipmap.image67,R.mipmap.image68,R.mipmap.image69,R.mipmap.image70,
            R.mipmap.image71,R.mipmap.image72,R.mipmap.image73,R.mipmap.image74,R.mipmap.image75,R.mipmap.image76,R.mipmap.image77,R.mipmap.image78,R.mipmap.image79,R.mipmap.image80,
            R.mipmap.image81,R.mipmap.image82,R.mipmap.image83,R.mipmap.image84,R.mipmap.image85,R.mipmap.image86,R.mipmap.image87,R.mipmap.image88,R.mipmap.image89,R.mipmap.image90,
            R.mipmap.image91,R.mipmap.image92,R.mipmap.image93,R.mipmap.image94,R.mipmap.image95,R.mipmap.image96,R.mipmap.image97,R.mipmap.image98,R.mipmap.image99};
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;
    private ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamejieguo);
        initView();
        Intent intent=getIntent();
        arrayList= intent.getStringArrayListExtra("image");
        if (arrayList.size()==1){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
        }else if (arrayList.size()==2){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
            imageView2.setImageResource(images[Integer.parseInt(arrayList.get(1))]);
        }else if (arrayList.size()==3){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
            imageView2.setImageResource(images[Integer.parseInt(arrayList.get(1))]);
            imageView3.setImageResource(images[Integer.parseInt(arrayList.get(2))]);

        }else if (arrayList.size()==4){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
            imageView2.setImageResource(images[Integer.parseInt(arrayList.get(1))]);
            imageView3.setImageResource(images[Integer.parseInt(arrayList.get(2))]);
            imageView4.setImageResource(images[Integer.parseInt(arrayList.get(3))]);
        }else if (arrayList.size()==5){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
            imageView2.setImageResource(images[Integer.parseInt(arrayList.get(1))]);
            imageView3.setImageResource(images[Integer.parseInt(arrayList.get(2))]);
            imageView4.setImageResource(images[Integer.parseInt(arrayList.get(3))]);
            imageView5.setImageResource(images[Integer.parseInt(arrayList.get(4))]);

        }else if (arrayList.size()==6){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
            imageView2.setImageResource(images[Integer.parseInt(arrayList.get(1))]);
            imageView3.setImageResource(images[Integer.parseInt(arrayList.get(2))]);
            imageView4.setImageResource(images[Integer.parseInt(arrayList.get(3))]);
            imageView5.setImageResource(images[Integer.parseInt(arrayList.get(4))]);
            imageView6.setImageResource(images[Integer.parseInt(arrayList.get(5))]);
        }else if (arrayList.size()==7){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
            imageView2.setImageResource(images[Integer.parseInt(arrayList.get(1))]);
            imageView3.setImageResource(images[Integer.parseInt(arrayList.get(2))]);
            imageView4.setImageResource(images[Integer.parseInt(arrayList.get(3))]);
            imageView5.setImageResource(images[Integer.parseInt(arrayList.get(4))]);
            imageView6.setImageResource(images[Integer.parseInt(arrayList.get(5))]);
            imageView7.setImageResource(images[Integer.parseInt(arrayList.get(6))]);
        }else if (arrayList.size()==8){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
            imageView2.setImageResource(images[Integer.parseInt(arrayList.get(1))]);
            imageView3.setImageResource(images[Integer.parseInt(arrayList.get(2))]);
            imageView4.setImageResource(images[Integer.parseInt(arrayList.get(3))]);
            imageView5.setImageResource(images[Integer.parseInt(arrayList.get(4))]);
            imageView6.setImageResource(images[Integer.parseInt(arrayList.get(5))]);
            imageView7.setImageResource(images[Integer.parseInt(arrayList.get(6))]);
            imageView8.setImageResource(images[Integer.parseInt(arrayList.get(7))]);
        }else if (arrayList.size()==9){
            imageView1.setImageResource(images[Integer.parseInt(arrayList.get(0))]);
            imageView2.setImageResource(images[Integer.parseInt(arrayList.get(1))]);
            imageView3.setImageResource(images[Integer.parseInt(arrayList.get(2))]);
            imageView4.setImageResource(images[Integer.parseInt(arrayList.get(3))]);
            imageView5.setImageResource(images[Integer.parseInt(arrayList.get(4))]);
            imageView6.setImageResource(images[Integer.parseInt(arrayList.get(5))]);
            imageView7.setImageResource(images[Integer.parseInt(arrayList.get(6))]);
            imageView8.setImageResource(images[Integer.parseInt(arrayList.get(7))]);
            imageView9.setImageResource(images[Integer.parseInt(arrayList.get(8))]);

        }


    }

    private void initView() {
        imageView1= (ImageView) findViewById(R.id.game1);
        imageView2= (ImageView) findViewById(R.id.game2);
        imageView3= (ImageView) findViewById(R.id.game3);
        imageView4= (ImageView) findViewById(R.id.game4);
        imageView5= (ImageView) findViewById(R.id.game5);
        imageView6= (ImageView) findViewById(R.id.game6);
        imageView7= (ImageView) findViewById(R.id.game7);
        imageView8= (ImageView) findViewById(R.id.game8);
        imageView9= (ImageView) findViewById(R.id.game9);

    }


}
