package com.example.qsd.edictionary.activitys;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;

import com.example.qsd.edictionary.adapter.SubAdapter;
import com.example.qsd.edictionary.bean.CodeBean;
import com.example.qsd.edictionary.fragment.IntroduceFragment;
import com.example.qsd.edictionary.fragment.MemoryFragment;
import com.example.qsd.edictionary.fragment.OtherCourseFragment;
import com.example.qsd.edictionary.fragment.VideoFragment;
import com.example.qsd.edictionary.fragment.WordListFragment;

import com.example.qsd.edictionary.fragment.words_VedioFragment;
import com.example.qsd.edictionary.urlAPI.UrlString;
import com.example.qsd.edictionary.utils.SearchDB;
import com.example.qsd.edictionary.utils.SharedpreferencesUtils;
import com.example.qsd.edictionary.videoview.VideoSuperPlayer;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

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

/**
 * 每个年级想洗视屏页面
 */
public class CourseVedioActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> list;
    private List<String> mtab;
    private ImageView imageView,play,icon;
    private TextView price,GoBuy;
    View view;
    private Context context;
    private boolean isplaying;
    private String mp4url = "http://flv2.bn.netease.com/videolib3/1612/01/jEyBQ0772/SD/jEyBQ0772-mobile.mp4";
    private String mp4_icom = "http://vimg2.ws.126.net/image/snapshot/2016/12/2/5/VC6900J25.jpg";
    private VideoSuperPlayer videoSuperPlayer;
    private  String videoImageUrl,videoUrl;
    private int productType,videoPrice,courseID ;
    private int userID=2;
    private String typecourse="k12";
    private int studyBean,cost;
    private SharedPreferences sharedPreferences;
    private Message message1,message2;
    Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                Toast.makeText(context, "订阅成功", Toast.LENGTH_SHORT).show();
                play.setClickable(true);
                price.setVisibility(view.GONE);
                GoBuy.setVisibility(view.GONE);
                play.setVisibility(View.VISIBLE);
                 message1=new Message();
                message1.what=1;
                words_VedioFragment.refresh.sendMessage(message1);
                message2=new Message();
                message2.what=2;
                VideoFragment.refresh.sendMessage(message2);
                studyBean= SearchDB.StudyBeanDb(context,"studyBean");
                cost=SearchDB.CostDb(context,"costStudyBean");
                studyBean=studyBean-videoPrice;
                cost=cost+videoPrice;//支付成功后的学习豆和消费学豆
                SharedpreferencesUtils.SaveStudyCode(context,studyBean,cost);
                sharedPreferences=context.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences. Editor userinfo = sharedPreferences.edit();
                userinfo.putString("SUCCESS","SUCCESS").commit();
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
        setContentView(R.layout.activity_course_vedio);
        initView();
        initOnClick();

    }

    private void initOnClick() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();

            }
        });

        BitmapUtils bitmapUtils=new BitmapUtils(getContext());
        bitmapUtils.display(icon,mp4_icom);
        play.setOnClickListener(new MyOnClick(mp4url,videoSuperPlayer));
        videoSuperPlayer.setVideoPlayCallback(new MyVideoCallback(play,videoSuperPlayer));
        GoBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subAPI(userID,courseID,typecourse,videoPrice);
            }
        });


    }

    private void subAPI(int userID, int courseID, String typecourse, int videoPrice) {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody
                .Builder()
                .add("userID",userID+"")
                .add("productID",courseID+"")
                .add("type",typecourse)
                .add("money",videoPrice+"")
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
                Intent intent = new Intent(new Intent(CourseVedioActivity.this,
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
        //video.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");//视频的缩略图
        video.setDescription("这里加载视屏的简介描述与内容");//视频的描述
        ShareAction shareAction = new ShareAction(CourseVedioActivity.this);
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
            Log.d("plat", "platform" + platform);

            Toast.makeText(CourseVedioActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(CourseVedioActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(CourseVedioActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private void initView() {
        context=getContext();
        imageView = (ImageView) findViewById(R.id.coursvedio_share);
        list = new ArrayList<>();
        mtab = new ArrayList<>();
        videoSuperPlayer= (VideoSuperPlayer) findViewById(R.id.video);
        viewPager = (ViewPager) findViewById(R.id.coursevp_vedioplay);
        tabLayout = (TabLayout) findViewById(R.id.cours_vedioplay);
        icon= (ImageView) findViewById(R.id.video_icon);
        play= (ImageView) findViewById(R.id.vedio_play);
        price= (TextView) findViewById(R.id.worddetail_price);
        GoBuy= (TextView) findViewById(R.id.textView3);
        Intent intent=getIntent();
        videoImageUrl = intent.getStringExtra("videoImageUrl");
        videoUrl=intent.getStringExtra("videoUrl");
        productType = intent.getIntExtra("productType", 0);
        videoPrice= intent.getIntExtra("videoPrice", 0);
        courseID=intent.getIntExtra("videoID",0);
        mp4url=videoUrl;
        mp4_icom=videoImageUrl;
        Log.i("qsd","年纪课程传递数据"+productType+"价格"+videoPrice+"ID"+courseID);
        if (productType==1){
            play.setVisibility(View.VISIBLE);
            price.setVisibility(View.GONE);
            GoBuy.setVisibility(View.GONE);
        }else{
            play.setClickable(false);
            play.setVisibility(View.GONE);
            price.setText("需花费"+videoPrice+"学豆");
        }
        list.add(new IntroduceFragment());
        list.add(new WordListFragment());
        list.add(new OtherCourseFragment());
        Log.i("qsd", "courseActivity" + list.size() + "");
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
        mtab.add("本节单词");
        mtab.add("其他课程");
        for (int i = 0; i < mtab.size(); i++) {
            TabLayout.Tab tab1 = tabLayout.getTabAt(i);
            tab1.setText(mtab.get(i));
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");
    }
    @Override
    public void onDestroy(){
        APP.setMediaPlayerNull();
        super.onDestroy();

    }
}

