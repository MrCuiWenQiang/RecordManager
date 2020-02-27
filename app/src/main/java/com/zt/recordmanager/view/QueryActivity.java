package com.zt.recordmanager.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.zt.recordmanager.R;
import com.zt.recordmanager.contract.QueryContract;
import com.zt.recordmanager.presenter.QueryPresenter;
import com.zt.recordmanager.util.SoundUtil;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.ToastUtility;
import cn.faker.repaymodel.widget.view.SpaceGridItemDecoration;

/**
 * 档案查询
 */
public class QueryActivity extends BaseMVPAcivity<QueryContract.View, QueryPresenter> implements QueryContract.View {

    private RelativeLayout hint_ll;
    private RecyclerView rv_list;

    private SoundUtil soundUtil;

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_query;
    }

    @Override
    protected void initContentView() {
        isShowToolView(true);
        isShowBackButton(true);
        setTitle("档案查询", R.color.white);
        setToolBarBackgroundColor(R.color.win_start);
        setStatusBar(R.color.win_start);

        hint_ll = findViewById(R.id.hint_ll);
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        soundUtil = new SoundUtil(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 139 || keyCode == 280) {

            if (event.getRepeatCount() == 0) {
                scanLabel();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void scanLabel() {
        mPresenter.scanLabel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void scanLabel_Success(String tag) {
        soundUtil.playSound(1);
        dimiss();
        ToastUtility.showToast(tag);
        hint_ll.setVisibility(View.GONE);
    }

    @Override
    public void scanLabel_Fail(String msg) {
        soundUtil.playSound(2);
        dimiss();
        ToastUtility.showToast(msg);
    }

}
