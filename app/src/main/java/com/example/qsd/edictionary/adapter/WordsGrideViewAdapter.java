package com.example.qsd.edictionary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.WordsBean;

import java.util.List;

/**
 * Created by QSD on 2016/12/28.
 */

public class WordsGrideViewAdapter extends BaseAdapter{
    private Context context;
    private List<WordsBean.DataBean.SectionDetailBean> sectionDetail;
    private onGridViewItemClickListener itemClickListener = null;
    public WordsGrideViewAdapter(Context context,List<WordsBean.DataBean.SectionDetailBean> sectionDetail){
        this.context=context;
        this.sectionDetail=sectionDetail;

    }
    @Override
    public int getCount() {
        return sectionDetail==null ? 0 :sectionDetail.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WordsGrideViewHolder Holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
            Holder=new WordsGrideViewHolder();
            Holder.button= (Button) convertView.findViewById(R.id.listitem_bn);
            convertView.setTag(Holder);
        }else{
            Holder= (WordsGrideViewHolder) convertView.getTag();
        }
        WordsBean.DataBean.SectionDetailBean sectionDetailBean = sectionDetail.get(position);
        Holder.button.setText(sectionDetailBean.getClassifyName());
        Holder.button.setTag(sectionDetailBean.getId()+"");
        Holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, (String) v.getTag());
            }
        });

        return convertView;
    }
    class WordsGrideViewHolder{
        private Button button;
    }
    public void setOnItemClickListener(onGridViewItemClickListener listener) {
        this.itemClickListener = listener;
        //  Log.d("ddd", itemClickListener.toString());
    }

    public  interface onGridViewItemClickListener {

        void onItemClick(View v, String tag);
    }
}
