package com.zt.recordmanager.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zt.recordmanager.MyApplication;
import com.zt.recordmanager.R;
import com.zt.recordmanager.contract.MainContract;
import com.zt.recordmanager.model.bean.MainGridBean;
import com.zt.recordmanager.presenter.MainPresenter;
import com.zt.recordmanager.util.RFIDUtil;
import com.zt.recordmanager.view.adapter.MainGridAdapter;

import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.LogUtil;
import cn.faker.repaymodel.widget.view.BaseRecycleView;
import cn.faker.repaymodel.widget.view.SpaceGridItemDecoration;

public class MainActivity extends BaseMVPAcivity<MainContract.View, MainPresenter> implements MainContract.View {

    private RecyclerView rv_list;

    private static final String TAG = "MainActivity";

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_main;
    }

    @Override
    protected void initContentView() {
        isShowToolView(false);
        setStatusBar(R.color.win_start);

        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_list.addItemDecoration(new SpaceGridItemDecoration(40,40));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.initGrid();
        showLoading("正在初始化设备中");
        RFIDUtil.init(new RFIDUtil.RFIDInitAction() {
            @Override
            public void success() {
                dimiss();
            }

            @Override
            public void fail(String msg) {
                dimiss();
                showDialog(msg);
            }
        });
    }

    @Override
    public void settingGrid(List<MainGridBean> datas) {
        MainGridAdapter gridAdapter = new MainGridAdapter();
        gridAdapter.setDatas(datas);
        gridAdapter.setOnItemClickListener(new BaseRecycleView.OnItemClickListener<MainGridBean>() {
            @Override
            public void onItemClick(View view, MainGridBean data, int position) {
                Class c = data.getO();
                String name = data.getName();
                if (c != null) {
                    toAcitvity(c);
                } else if (name.equals("出库")) {

                } else if (name.equals("入库")) {

                }
                LogUtil.i(TAG, name);
            }
        });
        rv_list.setAdapter(gridAdapter);
    }

    @Override
    protected void onDestroy() {
        RFIDUtil.exit();
        super.onDestroy();
    }
}
