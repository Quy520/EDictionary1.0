package com.example.qsd.edictionary.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.qsd.edictionary.R;

import com.example.qsd.edictionary.fragment.DetialsCourse;
import com.example.qsd.edictionary.fragment.DetialsFragment;
import com.example.qsd.edictionary.fragment.VideoFragment;
import com.example.qsd.edictionary.videoview.VideoSuperPlayer;
import com.lidroid.xutils.BitmapUtils;
import com.umeng.qq.handler.UmengQZoneHandler;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.ArrayList;
import java.util.List;

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
    View view;
    PopupWindow pop;
    private TextView hide;
    private ImageView imageView,share_weixin,share_QQ,share_weibo,share_friend,share_kongjian;
    private String mp4url="http://flv2.bn.netease.com/videolib3/1612/01/jEyBQ0772/SD/jEyBQ0772-mobile.mp4";
    private String mp4_icom="http://vimg2.ws.126.net/image/snapshot/2016/12/2/5/VC6900J25.jpg";
    private ImageView icon,play;
    private boolean isplaying;
    private VideoSuperPlayer videoSuperPlayer;
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
//                    pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT, true);
//                    pop.showAtLocation(hide, Gravity.BOTTOM,0,0);
//                    pop.setAnimationStyle(R.style.MenuAnimationFade);
//                    pop.setFocusable(true);
//                    pop.setTouchable(true);
//                    pop.setBackgroundDrawable(new BitmapDrawable());

                }
            });
//        share_weixin.setOnClickListener(this);
//        share_QQ.setOnClickListener(this);
//        share_weibo.setOnClickListener(this);
//        share_friend.setOnClickListener(this);
//        share_kongjian.setOnClickListener(this);

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
            if (((Activity) context).getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                Log.i("qsd","VideoplayActivity"+((Activity) context).getRequestedOrientation());
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
        //video.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");//视频的缩略图
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
        imageView= (ImageView) findViewById(R.id.vedio_share);
       list=new ArrayList<>();
        mtab=new ArrayList<>();
        videoSuperPlayer= (VideoSuperPlayer)findViewById(R.id.video);
        icon= (ImageView)findViewById(R.id.video_icon);
        play= (ImageView)findViewById(R.id.vedio_play);
        //LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件 - 即弹窗的界面
//        view = inflater.inflate(R.layout.menu_share, null);
//        hide= (TextView) findViewById(R.id.hide);
//        share_weixin= (ImageView)view.findViewById(R.id.share_weixin);
//        share_QQ= (ImageView) view.findViewById(R.id.share_qq);
//        share_weibo= (ImageView) view.findViewById(R.id.share_weibo);
//        share_friend= (ImageView) view.findViewById(R.id.share_weixinfriend);
//        share_kongjian= (ImageView) view.findViewById(R.id.share_kongjian);
        viewPager= (ViewPager) findViewById(R.id.vp_vedioplay);
        tabLayout= (TabLayout) findViewById(R.id.vedioplay);
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
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result","onActivityResult");
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
