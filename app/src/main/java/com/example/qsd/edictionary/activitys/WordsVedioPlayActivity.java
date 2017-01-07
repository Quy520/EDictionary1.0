package com.example.qsd.edictionary.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import com.example.qsd.edictionary.fragment.ExplainFragment;
import com.example.qsd.edictionary.fragment.IntroduceFragment;
import com.example.qsd.edictionary.fragment.OtherCourseFragment;
import com.example.qsd.edictionary.fragment.WordListFragment;
import com.example.qsd.edictionary.videoview.VideoSuperPlayer;
import com.lidroid.xutils.BitmapUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.ContextUtil.getContext;
/**
 * 每个单词的视屏页面
 */

public class WordsVedioPlayActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> list;
    private List<String> mtab;
    private boolean isplaying;
    private ImageView imageView,play,icon;
    private Context context;
    View view;
    private String mp4url = "http://flv2.bn.netease.com/videolib3/1612/01/jEyBQ0772/SD/jEyBQ0772-mobile.mp4";
    private String mp4_icom = "http://vimg2.ws.126.net/image/snapshot/2016/12/2/5/VC6900J25.jpg";
    private String stringValue;
    private TextView price,GoBuy;
    private ExplainFragment explainFragment;
    private VideoSuperPlayer videoSuperPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_vedio_play);
        Intent intent=getIntent();
        stringValue=intent.getStringExtra("DETAILS");
        Log.i("qsd1","获取单词传过来的detail"+stringValue);
        explainFragment=ExplainFragment.newInstance(stringValue);
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
                Intent intent = new Intent(new Intent(WordsVedioPlayActivity.this,
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
        ShareAction shareAction = new ShareAction(WordsVedioPlayActivity.this);
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

            Toast.makeText(WordsVedioPlayActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(WordsVedioPlayActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(WordsVedioPlayActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private void initView() {
        context=getContext();
        imageView = (ImageView) findViewById(R.id.vedio_share);
        list = new ArrayList<>();
        mtab = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.vp_vedioplay);
        tabLayout = (TabLayout) findViewById(R.id.wordsvedioplay);
        videoSuperPlayer= (VideoSuperPlayer) findViewById(R.id.video);
        icon= (ImageView) findViewById(R.id.video_icon);
        play= (ImageView) findViewById(R.id.vedio_play);
        price= (TextView) findViewById(R.id.worddetail_price);
        GoBuy= (TextView) findViewById(R.id.textView3);

        list.add(explainFragment);
        list.add(new WordListFragment());
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
        mtab.add("释义");
        mtab.add("相关单词");
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
