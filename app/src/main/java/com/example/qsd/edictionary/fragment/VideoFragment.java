package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.APP;
import com.example.qsd.edictionary.activitys.FullActivity;
import com.example.qsd.edictionary.videoview.VideoSuperPlayer;
import com.lidroid.xutils.BitmapUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    private View view;
    private VideoSuperPlayer videoSuperPlayer;
    private ViewPager viewPager;
    private ImageView icon,play;
    private boolean isplaying;
    private Button sub_words,sub_vedio;
    private String mp4url="http://flv2.bn.netease.com/videolib3/1612/01/jEyBQ0772/SD/jEyBQ0772-mobile.mp4";
    private String mp4_icom="http://vimg2.ws.126.net/image/snapshot/2016/12/2/5/VC6900J25.jpg";
    private Context context;
    private FragmentManager manager;
    private FragmentTransaction ft;
    private LinearLayout linearLayout;


    public VideoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            if (view==null){
                view=inFlater(inflater);
            }
            return view;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private View inFlater(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.fragment_video,null,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        context=getContext();
        videoSuperPlayer= (VideoSuperPlayer) view.findViewById(R.id.video);
        icon= (ImageView) view.findViewById(R.id.video_icon);
        play= (ImageView) view.findViewById(R.id.vedio_play);
        sub_words= (Button) view.findViewById(R.id.sub_word);
        sub_vedio= (Button) view.findViewById(R.id.sub_view);
        linearLayout= (LinearLayout) view.findViewById(R.id.fragment_contenr);
        viewPager= (ViewPager) view.findViewById(R.id.viewpage_main);
        manager = getFragmentManager();
        BitmapUtils bitmapUtils=new BitmapUtils(getContext());
        bitmapUtils.display(icon,mp4_icom);
        play.setOnClickListener(new MyOnClick(mp4url,videoSuperPlayer));
        videoSuperPlayer.setVideoPlayCallback(new MyVideoCallback(play,videoSuperPlayer));

        sub_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager =getFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.viewpage_main,new WrodsFragment());
                fragmentTransaction.addToBackStack(null);
                //提交修改
                fragmentTransaction.commit();
            }
        });

        sub_vedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);

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

      public class MyVideoCallback implements VideoSuperPlayer.VideoPlayCallbackImpl {
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
                Log.i("qsd","VideoFragemt"+((Activity) context).getRequestedOrientation());
                Intent intent = new Intent(new Intent(context,
                        FullActivity.class));
                context.startActivity(intent);
            }
        }

        @Override
        public void onPlayFinish() {

        }
    }
    @Override
    public void onDestroy(){
        APP.setMediaPlayerNull();
        super.onDestroy();
    }
}
