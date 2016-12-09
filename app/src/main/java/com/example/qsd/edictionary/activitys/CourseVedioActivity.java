package com.example.qsd.edictionary.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;

import android.widget.Toast;

import com.example.qsd.edictionary.R;

import com.example.qsd.edictionary.fragment.IntroduceFragment;
import com.example.qsd.edictionary.fragment.OtherCourseFragment;
import com.example.qsd.edictionary.fragment.WordListFragment;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.ArrayList;
import java.util.List;

public class CourseVedioActivity extends Activity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> list;
    private List<String> mtab;
    private ImageView imageView;
    View view;
    private String mp4url = "http://flv2.bn.netease.com/videolib3/1612/01/jEyBQ0772/SD/jEyBQ0772-mobile.mp4";
    private String mp4_icom = "http://vimg2.ws.126.net/image/snapshot/2016/12/2/5/VC6900J25.jpg";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        imageView = (ImageView) findViewById(R.id.vedio_share);
        list = new ArrayList<>();
        mtab = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.vp_vedioplay);
        tabLayout = (TabLayout) findViewById(R.id.vedioplay);
        list.add(new IntroduceFragment());
        list.add(new WordListFragment());
        list.add(new OtherCourseFragment());
        Log.i("qsd", "courseActivity" + list.size() + "");

//        viewPager.setAdapter(new FragmentPagerAdapter(()) {
//
//            @Override
//            public Fragment getItem(int position) {
//                return list.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//        });
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





}

