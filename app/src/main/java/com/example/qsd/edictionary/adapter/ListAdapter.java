package com.example.qsd.edictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.GetWordsBean;

import java.util.List;

/**
 * Created by QSD on 2016/12/31.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<GetWordsBean.DataBean.WordDataBean> wordData;
    private onListViewItemClickListener itemClickListener;
    public ListAdapter(Context context,List<GetWordsBean.DataBean.WordDataBean> wordData){
        this.context=context;
        this.wordData=wordData;

    }
    @Override
    public int getCount() {
        return wordData.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.list_itemwords,parent,false);
            Holder=new WordsGrideViewHolder();
           Holder.button= (Button) convertView.findViewById(R.id.oneword_price);
            Holder.textView= (TextView) convertView.findViewById(R.id.onewords_word);
            Holder.relativeLayout= (RelativeLayout) convertView.findViewById(R.id.ietm_words);
            convertView.setTag(Holder);

        }else{
            Holder= (WordsGrideViewHolder) convertView.getTag();
        }
        Holder.textView.setText(wordData.get(position).getWord());
        Holder.button.setText(wordData.get(position).getWordPrice()+"");
        Holder.relativeLayout.setTag(wordData.get(position).getWordID());
        Holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v,(String)v.getTag());
            }
        });
        return convertView;
    }

    private class WordsGrideViewHolder {
        private RelativeLayout relativeLayout;
        private TextView textView;
        private Button button;
    }

    public void setOnItemClickListener(onListViewItemClickListener listener) {
        this.itemClickListener = listener;
        //  Log.d("ddd", itemClickListener.toString());
    }

    public  interface onListViewItemClickListener {

        void onItemClick(View v, String tag);
    }
}
