package com.zt.recordmanager.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zt.recordmanager.R;
import com.zt.recordmanager.model.bean.MainGridBean;

import java.util.List;

import cn.faker.repaymodel.widget.view.BaseRecycleView;

public class MainGridAdapter extends RecyclerView.Adapter<MainGridAdapter.ViewHolder> {

    private List<MainGridBean> datas;
    private BaseRecycleView.OnItemClickListener onItemClickListener;

    public void setDatas(List<MainGridBean> datas) {
        this.datas = datas;
    }

    public void setOnItemClickListener(BaseRecycleView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_grid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MainGridBean data = datas.get(i);
        viewHolder.tv_name.setText(data.getName());
        viewHolder.iv_icon.setBackgroundResource(data.getImage());
        viewHolder.ll_father.setBackgroundColor(data.getColor());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, data, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        CardView ll_father;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            ll_father = itemView.findViewById(R.id.ll_father);
        }
    }
}
