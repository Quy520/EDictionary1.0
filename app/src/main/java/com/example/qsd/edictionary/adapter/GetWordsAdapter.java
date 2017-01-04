package com.example.qsd.edictionary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.GetWordsBean;

import java.util.List;

/**
 * Created by QSD on 2016/12/29.
 */

public class GetWordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GetWordsBean.DataBean> data;
    List<GetWordsBean.DataBean.WordDataBean> wordData;
    public GetWordsAdapter(Context context,List<GetWordsBean.DataBean> data){
        this.context=context;
        this.data=data;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.item_oneword,parent,false);
        return new FirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FirstViewHolder fholder= (FirstViewHolder) holder;
        fholder.title.setText(data.get(position).getTitle());
        wordData = data.get(position).getWordData();
        fholder.listView.setAdapter(new ListAdapter(context,wordData));



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setList( List<GetWordsBean.DataBean> data) {
        this.data=data;
        notifyDataSetChanged();
    }

    private class FirstViewHolder extends RecyclerView.ViewHolder {
        private ListView listView;
        private TextView title;
        public FirstViewHolder(View view) {
            super(view);
            title= (TextView) view.findViewById(R.id.words_unit);
            listView= (ListView) view.findViewById(R.id.onewords_listview);


        }
    }
}
