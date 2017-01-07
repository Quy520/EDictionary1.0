package com.example.qsd.edictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.WordsVedioPlayActivity;
import com.example.qsd.edictionary.bean.GetWordsBean;

import java.util.List;

/**
 * Created by QSD on 2016/12/29.
 */

public class GetWordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GetWordsBean.DataBean> data;
   private List<GetWordsBean.DataBean.WordDataBean> wordData;
    private String detail;
    private ListAdapter listAdapter;
    private int heigh;

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
        for (int i=0;i<wordData.size();i++){
            detail=wordData.get(i).getWordDetail();
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
       heigh= wm.getDefaultDisplay().getHeight();
        listAdapter=new ListAdapter(context,wordData);
        ViewGroup.LayoutParams layoutParams=fholder.listView.getLayoutParams();
        layoutParams.height=wordData.size()*heigh/12;
        fholder.listView.setLayoutParams(layoutParams);
        fholder.listView.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(new ListAdapter.onListViewItemClickListener() {
            @Override
            public void onItemClick(View v, String tag) {
                Log.i("qsd","listView单词的传值"+detail);
                Intent intent=new Intent(context, WordsVedioPlayActivity.class);
                intent.putExtra("DETAILS",detail);
                context.startActivity(intent);
                Toast.makeText(context, "您点击了"+tag, Toast.LENGTH_SHORT).show();
            }
        });




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
