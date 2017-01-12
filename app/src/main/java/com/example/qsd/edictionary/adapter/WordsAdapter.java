package com.example.qsd.edictionary.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.VedioPlayActivity;
import com.example.qsd.edictionary.activitys.WordsDetailsActivity;
import com.example.qsd.edictionary.bean.MemoryDownBean;
import com.example.qsd.edictionary.bean.MemoryUpBean;
import com.example.qsd.edictionary.bean.WordsBean;

import java.util.List;


import static com.example.qsd.edictionary.adapter.WordsGrideViewAdapter.*;


/**
 * Created by QSD on 2016/11/29.
 */

public class WordsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Context mcontext;
    private List<WordsBean.DataBean> wordsBeanData;
    private List<WordsBean.DataBean.SectionDetailBean> sectionDetail;
    private WordsGrideViewAdapter wordsGrideViewAdapter;

    public WordsAdapter(Context context, List<WordsBean.DataBean> wordsBeanData){
        this.mcontext=context;
        this.wordsBeanData=wordsBeanData;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.item_words,parent,false);
        return new FirstViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FirstViewHolder fholder= (FirstViewHolder) holder;
        for (int i=0;i<wordsBeanData.size();i++){
            String sectionName = wordsBeanData.get(i).getSectionName();
            fholder.textView.setText(sectionName);
        }
        sectionDetail = wordsBeanData.get(position).getSectionDetail();
        wordsGrideViewAdapter=new WordsGrideViewAdapter(mcontext,sectionDetail);
        fholder.gridView.setAdapter(wordsGrideViewAdapter);
        wordsGrideViewAdapter.setOnItemClickListener(new onGridViewItemClickListener() {
            @Override
            public void onItemClick(View v, String tag) {
                Toast.makeText(mcontext, "您点击了"+tag, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mcontext, WordsDetailsActivity.class);
                intent.putExtra("buttonKey",tag);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wordsBeanData.size();
    }

    public void setList(List<WordsBean.DataBean> wordsBeanData) {
        this.wordsBeanData=wordsBeanData;
        notifyDataSetChanged();


    }

    private class FirstViewHolder extends RecyclerView.ViewHolder {
        private TextView  textView;
        private GridView gridView;
        public FirstViewHolder(View view) {
            super(view);
            textView= (TextView) view.findViewById(R.id.itemwords_tv);
            gridView= (GridView) view.findViewById(R.id.itemwords_gv);
        }
    }
}
