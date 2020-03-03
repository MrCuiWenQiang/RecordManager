package com.zt.recordmanager.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zt.recordmanager.R;

import java.util.List;

public class OutAdapter extends RecyclerView.Adapter<OutAdapter.OutViewHolder> {

    private List<String> tabs;

    public OutAdapter(List<String> tabs) {
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public OutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_out,viewGroup,false);
        return new OutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutViewHolder outViewHolder, int i) {
        outViewHolder.tv_tab.setText(tabs.get(i));
    }

    @Override
    public int getItemCount() {
        return tabs == null ? 0 : tabs.size();
    }

    protected class OutViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tab;
        public OutViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tab = itemView.findViewById(R.id.tv_tab);
        }
    }
}
