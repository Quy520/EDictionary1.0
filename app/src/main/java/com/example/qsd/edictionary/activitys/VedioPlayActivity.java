package com.example.qsd.edictionary.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.qsd.edictionary.R;

import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.fragment.DetialsCourse;
import com.example.qsd.edictionary.fragment.DetialsFragment;
import com.example.qsd.edictionary.fragment.VideoFragment;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.SearchDB;
import com.example.qsd.edictionary.utils.SharedpreferencesUtils;
import com.example.qsd.edictionary.videoview.VideoSuperPlayer;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.squareup.picasso.Request;
import com.umeng.qq.handler.UmengQZoneHandler;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.umeng.socialize.utils.ContextUtil.getContext;
import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 记忆法课程视屏页面
 */
public class VedioPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> list;
    private List<String> mtab;
    private Context context;
    View view;
    PopupWindow pop;
    private TextView hide,title_tv,price_tv,Gobuy;
    private ImageView imageView,share_weixin,share_QQ,share_weibo,share_friend,share_kongjian;
    private String mp4url="http://flv2.bn.netease.com/videolib3/1612/01/jEyBQ0772/SD/jEyBQ0772-mobile.mp4";
    private String mp4_icom="http://vimg2.ws.126.net/image/snapshot/2016/12/2/5/VC6900J25.jpg";
    private String Title,VedioImageUrl,VedioUrl;
    private int type,price,courseID;
    private int UserID=2;
    private String typecourse="memory";
    private ImageView icon,play;
    private boolean isplaying;
    private VideoSuperPlayer videoSuperPlayer;
    private int studyBean,cost;
    Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                Toast.makeText(context, "订阅成功", Toast.LENGTH_SHORT).show();
                play.setClickable(true);
                price_tv.setVisibility(view.GONE);
                Gobuy.setVisibility(view.GONE);
                play.setVisibility(View.VISIBLE);
                studyBean= SearchDB.StudyBeanDb(getContext(),"studyBean");
                cost=SearchDB.CostDb(getContext(),"costStudyBean");
                studyBean=studyBean-price;
                cost=cost+price;//支付成功后的学习豆和消费学豆
                SharedpreferencesUtils.SaveStudyCode(context,studyBean,cost);
                return;
            }
            if (msg.what == 0x222) {
                Toast.makeText(context, "订阅失败，余额不足", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 0x333) {
                Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_play);
        initView();
        initOnClick();

    }

    private void initOnClick() {
        //视屏播放监听
        BitmapUtils bitmapUtils=new BitmapUtils(getContext());
        bitmapUtils.display(icon,mp4_icom);
        play.setOnClickListener(new MyOnClick(mp4url,videoSuperPlayer));
        videoSuperPlayer.setVideoPlayCallback(new MyVideoCallback(play,videoSuperPlayer));
        //分享监听
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShare();
                }
            });
        //购买监听
        Gobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subAPI(UserID,courseID,typecourse,price);
            }
        });

    }

    private void subAPI(int userID, int courseID, String typecourse, int price) {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
                .add("productID",courseID+"")
                .add("type",typecourse)
                .add("money",price+"")
                .build();
        okhttp3.Request request=new okhttp3.Request.Builder()
                .url(UrlString.URL_LOGIN+"subAPI")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string=response.body().string();
                Log.i("qsd","VediePalyActity"+string);
                CodeBean codeBean=new Gson().fromJson(string,CodeBean.class);
                String code = codeBean.getCode();
                Log.i("qsd",code+"RegisterActivity");
                if (code.equals("SUCCESS")){
                    handler1.sendEmptyMessage(0x111);//发送消息
                } else if (code.equals("NOTSUFFICIENTFUNDS")){
                    handler1.sendEmptyMessage(0x222);//发送消息
                }else{
                    handler1.sendEmptyMessage(0x333);//发送消息
                }
            }
        });

    }

    class MyOnClick implements View.OnClickListener {
        String mp4url;
        VideoSuperPlayer player;
        public MyOnClick(String mp4url, VideoSuperPlayer videoSuperPlayer) {
            this.mp4url=mp4url;
            this.player=videoSuperPlayer;
        }
        @Override
        public void onClick(View v) {
            APP.setMediaPlayerNull();
            isplaying=true;
            Log.i("qsd","打开播放器");
            videoSuperPlayer.setVisibility(View.VISIBLE);
            icon.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
            player.loadAndPlay(APP.getMediaPlayer(),mp4url,0,false);
            Log.i("qsd","mp4地址"+mp4url);

        }
    }

    class MyVideoCallback implements VideoSuperPlayer.VideoPlayCallbackImpl {
        ImageView imageView;
        VideoSuperPlayer superPlayer;
        public MyVideoCallback(ImageView player, VideoSuperPlayer videoSuperPlayer) {
            this.imageView=player;
            this.superPlayer=videoSuperPlayer;
        }
        @Override
        public void onCloseVideo() {
            isplaying=false;
            superPlayer.close();
            APP.setMediaPlayerNull();
            imageView.setVisibility(View.VISIBLE);
            superPlayer.setVisibility(View.GONE);
        }
        @Override
        public void onSwitchPageType() {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Intent intent = new Intent(new Intent(VedioPlayActivity.this,
                        FullActivity.class));
                startActivity(intent);
            }
        }
        @Override
        public void onPlayFinish() {

        }
    }

    private void showShare() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(true);
        UMVideo video = new UMVideo(mp4url);
        video.setTitle("视屏的标题：第一课时，什么叫记忆法");//视频的标题
        video.setDescription("这里加载视屏的简介描述与内容");//视频的描述
        ShareAction shareAction = new ShareAction(VedioPlayActivity.this);
        shareAction.withMedia(video).share();
        shareAction
                .setDisplayList(SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE,
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open(config);

    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            Toast.makeText(VedioPlayActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(VedioPlayActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(VedioPlayActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private void initView() {
        context=getContext();
        imageView= (ImageView) findViewById(R.id.vedio_share);
        list=new ArrayList<>();
        mtab=new ArrayList<>();
        videoSuperPlayer= (VideoSuperPlayer)findViewById(R.id.video);
        icon= (ImageView)findViewById(R.id.video_icon);
        play= (ImageView) findViewById(R.id.vedio_play);
        viewPager= (ViewPager) findViewById(R.id.vp_vedioplay);
        tabLayout= (TabLayout) findViewById(R.id.vedioplay);
        title_tv= (TextView) findViewById(R.id.txt_title);
        price_tv= (TextView) findViewById(R.id.memory_price);
        Gobuy= (TextView) findViewById(R.id.textView3);
        Intent intent=getIntent();
        Title = intent.getStringExtra("courseTitle");
        VedioImageUrl=intent.getStringExtra("courseImageUrl");
        VedioUrl=intent.getStringExtra("courseVideo");
        courseID=intent.getIntExtra("courseID",0);
        type=intent.getIntExtra("coursePayStatus",0);
        price=intent.getIntExtra("coursePrice",0);
        title_tv.setText(Title);
        Log.i("qsd","查看记忆法传递过来的数值"+type+"--"+courseID+"--"+price+"");
        if (type==1){
            play.setVisibility(View.VISIBLE);
            price_tv.setVisibility(View.GONE);
            Gobuy.setVisibility(View.GONE);
            mp4url=VedioUrl;
            mp4_icom=VedioImageUrl;
        }else{
            play.setClickable(false);
            play.setVisibility(View.GONE);
            mp4url=VedioUrl;
            mp4_icom=VedioImageUrl;
            price_tv.setText("需花费"+price+"学豆");

        }
        list.add(new DetialsFragment());
        list.add(new DetialsCourse());
        Log.i("qsd","VedioActivity"+list.size()+"");
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
            @Override
            public int getCount() {
                return list.size();
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        mtab.add("本节说明");
        mtab.add("其他课程");
        for(int i=0;i<mtab.size();i++){
            TabLayout.Tab tab1=tabLayout.getTabAt(i);
            tab1.setText(mtab.get(i));
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result","onActivityResult");
    }
    @Override
    public void onDestroy(){
        APP.setMediaPlayerNull();
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_weixin:
                Toast.makeText(this, "分享到微信", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_qq:
                Toast.makeText(this, "分享到QQ", Toast.LENGTH_SHORT).show();
                UMImage image = new UMImage(VedioPlayActivity.this, "imageurl");//网络图片
                new ShareAction(VedioPlayActivity.this).withText("hello")
                        .withMedia(image)
                        .withTargetUrl(mp4url)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.share_weibo:
                Toast.makeText(this, "分享到微博", Toast.LENGTH_SHORT).show();
                new     ShareAction(VedioPlayActivity.this).setPlatform(SHARE_MEDIA.SINA)
                        .withText("hello")
                        .setCallback(umShareListener)
                        .share();


                break;
            case R.id.share_weixinfriend:
                Toast.makeText(this, "分享到朋友圈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_kongjian:
                Toast.makeText(this, "分享到空间", Toast.LENGTH_SHORT).show();
                UmengQZoneHandler hander=new UmengQZoneHandler();

                new     ShareAction(VedioPlayActivity.this).setPlatform(SHARE_MEDIA.SINA)
                        .withText("hello")
                        .setCallback(umShareListener)
                        .share();
                break;

        }

    }
}
