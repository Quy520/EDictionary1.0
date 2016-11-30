package com.example.qsd.edictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qsd.edictionary.R;
import com.gc.flashview.FlashView;
import com.gc.flashview.constants.EffectConstants;

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

    public MemoryAdapter(){

    }
    public MemoryAdapter(Context context,List<String> list){
    this.mcontext=context;
        this.list=list;

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

//            case 2:
//                view= LayoutInflater.from(mcontext).inflate(R.layout.item2_memory,parent,false);
//                return new SecondViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type=getItemViewType(position);
        switch (type){
            case TYPE1:
                FirstViewHolder fholder= (FirstViewHolder) holder;
                fholder.flashView.setImageUris(list);
                fholder.flashView.setEffect(EffectConstants.DEFAULT_EFFECT);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setList(List<String> urllist) {
        this.list=urllist;
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
        private TextView textView1,textView2;
        public SecondViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image);
            textView1= (TextView) itemView.findViewById(R.id.textView);
            textView2= (TextView) itemView.findViewById(R.id.textView2);
        }
    }
}
