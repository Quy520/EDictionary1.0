package com.example.qsd.edictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
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
import com.example.qsd.edictionary.bean.MemoryDownBean;
import com.example.qsd.edictionary.bean.MemoryUpBean;
import com.gc.flashview.FlashView;
import com.gc.flashview.constants.EffectConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QSD on 2016/11/29.
 */

public class MemoryAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private static final int TYPE1=1;
    private static final int TYPE2=2;
    private Context mcontext;
    private List<String> list;
    private List<MemoryDownBean.DataBean> DownBeanData;
    private List<MemoryUpBean.DataBean> Updata;
    private onRecyclerViewItemClickListener itemClickListener = null;
    private Handler handler=new Handler();
    public MemoryAdapter(Context context,List<MemoryDownBean.DataBean> DownBeanData,List<MemoryUpBean.DataBean> Updata){
        this.mcontext=context;
        this.DownBeanData=DownBeanData;
        this.Updata=Updata;

    }

    @Override
    public int getItemViewType(int position){
        if (position==0){
            return TYPE1;
        }else {
            return TYPE2;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 1:
                 view= LayoutInflater.from(mcontext).inflate(R.layout.item_memory,parent,false);
                return new FirstViewHolder(view);

            case 2:
                view= LayoutInflater.from(mcontext).inflate(R.layout.item2_memory,parent,false);
                return new SecondViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type=getItemViewType(position);
        switch (type){
            case TYPE1:
                list=new ArrayList<>();
                FirstViewHolder fholder= (FirstViewHolder) holder;
                for (int i=0;i<Updata.size();i++){
                    list.add(Updata.get(i).getTopImageUrl());
                }
                fholder.flashView.setImageUris(list);
                fholder.flashView.setEffect(EffectConstants.DEFAULT_EFFECT);
                break;
            case TYPE2:
                SecondViewHolder secondViewHolder= (SecondViewHolder) holder;
                MemoryDownBean.DataBean dataBean = DownBeanData.get(position-1);
                secondViewHolder.textView1.setText(dataBean.getCourseName());
                secondViewHolder.textView2.setText(dataBean.getCourseInstructions());
                secondViewHolder.button.setText(dataBean.getCoursePrice()+"学习豆");
                secondViewHolder.itemView.setTag(dataBean.getCourseID()+"");//这是标识视屏id
                Picasso.with(mcontext)
                        .load(dataBean.getCourseImageUrl())
                        .placeholder(R.mipmap.defule)
                        .config(Bitmap.Config.RGB_565)
                        .into(secondViewHolder.imageView);
                secondViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null){
                            itemClickListener.onItemClick(v,(String)v.getTag());
                        }
                    }
                });


                break;
            default:
                break;
        }

    }


    @Override
    public int getItemCount() {
        return DownBeanData.size()+1;

    }


    public void setList(List<MemoryDownBean.DataBean> DownBeanData,List<MemoryUpBean.DataBean> Updata){
        this.DownBeanData=DownBeanData;
        this.Updata=Updata;
        Log.i("qsd1","bannerAPI"+Updata+"==="+DownBeanData);
        notifyDataSetChanged();


    }

    private class FirstViewHolder extends RecyclerView.ViewHolder{
        private FlashView flashView;
        public FirstViewHolder(View itemView) {
            super(itemView);
            flashView= (FlashView) itemView.findViewById(R.id.flashview_memory);
        }
    }
    private class SecondViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private RelativeLayout relativeLayout;
        private Button button;
        private TextView textView1,textView2;
        public SecondViewHolder(View itemView) {
            super(itemView);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.memory_item);
            button= (Button) itemView.findViewById(R.id.memory_button);
            imageView= (ImageView) itemView.findViewById(R.id.image);//图片
            textView1= (TextView) itemView.findViewById(R.id.textView);//介绍
            textView2= (TextView) itemView.findViewById(R.id.textView2);//详细描述
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
