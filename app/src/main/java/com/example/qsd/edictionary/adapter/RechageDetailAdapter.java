package com.example.qsd.edictionary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.bean.RechagedetailBean;

import java.util.List;

/**
 * Created by QSD on 2017/1/3.
 */

public class RechageDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
   private List<RechagedetailBean.DataBean> data;
    public RechageDetailAdapter(Context context,List<RechagedetailBean.DataBean> data){
        this.context=context;
        this.data=data;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.rechagedetail_item,parent,false);
        return new FirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      FirstViewHolder firstViewHolder=(FirstViewHolder) holder;
        firstViewHolder.time.setText(data.get(position).getRechargeTime());
        firstViewHolder.money.setText(data.get(position).getRechargeMoney()+"元");
        Log.i("qsd", "是否请求1" + "RechargeDetail请求数据======="+data.get(position).getRechargeMoney());
        firstViewHolder.type.setText(data.get(position).getRechargeType());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setList(List<RechagedetailBean.DataBean> data) {
        this.data=data;
        Log.i("qsd", "是否请求1" + "RechargeDetail请求数据======="+data.size());
        notifyDataSetChanged();
    }

    private class FirstViewHolder extends RecyclerView.ViewHolder {
        private TextView time,money,type;
        public FirstViewHolder(View view) {
            super(view);
            time= (TextView) view.findViewById(R.id.rechargedetail_time);
            money= (TextView) view.findViewById(R.id.rechargedetail_money);
            type= (TextView) view.findViewById(R.id.rechargedetail_type);
        }
    }
}
