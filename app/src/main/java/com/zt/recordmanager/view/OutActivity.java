package com.zt.recordmanager.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.zt.recordmanager.R;
import com.zt.recordmanager.contract.OutContract;
import com.zt.recordmanager.presenter.OutPresenter;
import com.zt.recordmanager.util.SoundUtil;
import com.zt.recordmanager.view.adapter.OutAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.ToastUtility;

/**
 * 出库
 */
public class OutActivity extends BaseMVPAcivity<OutContract.View, OutPresenter>
        implements OutContract.View, View.OnClickListener {

    private RecyclerView rv_list;
    private Button bt_update;
    private OutAdapter adapter;
    private List<String> tabs = new ArrayList<>();

    private SoundUtil soundUtil;

    private boolean isStart = false;

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_out;
    }

    @Override
    protected void initContentView() {
        isShowToolView(true);
        isShowBackButton(true);
        setTitle("档案出库", R.color.white);
        setToolBarBackgroundColor(R.color.win_start);
        setStatusBar(R.color.win_start);

        bt_update = findViewById(R.id.bt_update);
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OutAdapter(tabs);
        rv_list.setAdapter(adapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        soundUtil = new SoundUtil(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        bt_update.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 139 || keyCode == 280) {

            if (event.getRepeatCount() == 0) {
                scan();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void scan() {
        if (!isStart) {
            isStart = true;
            mPresenter.scanframe();
        }
    }

    @Override
    public void scanLabel_Success(String data) {
        isStart = false;
        ToastUtility.showToast(data);
        soundUtil.success();
        tabs.add(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void scanLabel_Fail(String msg) {
        isStart = false;
        ToastUtility.showToast(msg);
        soundUtil.fail();
    }

    @Override
    public void update_Success(String msg) {
        dimiss();
        ToastUtility.showToast(msg);
        tabs.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void update_Fail(String msg) {
        dimiss();
        ToastUtility.showToast(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update: {
                showLoading("正在上传");
                mPresenter.update(tabs);
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
