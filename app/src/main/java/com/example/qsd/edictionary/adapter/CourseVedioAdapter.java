package com.example.qsd.edictionary.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.CourseVedioBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by QSD on 2016/12/29.
 */

public class CourseVedioAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private Context context;
   private List<CourseVedioBean.DataBean> vedioBeanData;
    private onRecyclerViewItemClickListener itemClickListener = null;
    public CourseVedioAdapter(Context context,List<CourseVedioBean.DataBean> data){
        this.context=context;
        this.vedioBeanData=data;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.item_vedio,parent,false);
        return new FirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FirstViewHolder fholder= (FirstViewHolder) holder;
        fholder.price.setText(vedioBeanData.get(position).getVideoPrice()+"学习豆");
        fholder.time.setText("时长"+vedioBeanData.get(position).getVideoTime()+"共"+vedioBeanData.get(position).getVideoWordNum()+"个单词");
        fholder.title.setText(vedioBeanData.get(position).getVideoName());
        Log.i("qsd",vedioBeanData.get(position).getVideoPrice()+"单词视屏价格获取123");
        fholder.relativeLayout.setTag(position+"");//以视屏id作为标记
        Picasso.with(context)
                .load(vedioBeanData.get(position).getVideoImageUrl())
                .placeholder(R.mipmap.defule)
                .config(Bitmap.Config.RGB_565)
                .into(fholder.imageView);

        fholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v,(String)v.getTag());
            }
        });

    }

    @Override
    public int getItemCount() {

        return  vedioBeanData==null ? 0 :vedioBeanData.size();
    }

    public void setList(List<CourseVedioBean.DataBean> data) {
        this.vedioBeanData=data;
        Log.i("qsd",vedioBeanData.size()+"长度");
            notifyDataSetChanged();

    }
    private class FirstViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title,time;
        private Button price;
        private RelativeLayout relativeLayout;

        public FirstViewHolder(View view) {
            super(view);
            relativeLayout= (RelativeLayout) view.findViewById(R.id.coursevedio_item);
            imageView= (ImageView) view.findViewById(R.id.coursevedio_im);
            title= (TextView) view.findViewById(R.id.coursevedio_detail);
            time= (TextView) view.findViewById(R.id.coursevedio_time);
            price= (Button) view.findViewById(R.id.coursevedio_button);

        }
    }
    public void setOnItemClickListener(onRecyclerViewItemClickListener listener) {
        this.itemClickListener = listener;
        //  Log.d("ddd", itemClickListener.toString());
    }

    public  interface onRecyclerViewItemClickListener {

        void onItemClick(View v, String tag);
    }


}
