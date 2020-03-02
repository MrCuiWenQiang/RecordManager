package com.zt.recordmanager.view;

import android.os.Bundle;
import android.widget.TextView;

import com.zt.recordmanager.R;
import com.zt.recordmanager.contract.AboutContract;
import com.zt.recordmanager.presenter.AboutPresenter;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.CustomUtility;

/**
 * 关于我们
 */
public class AboutActivity extends BaseMVPAcivity<AboutContract.View, AboutPresenter> implements AboutContract.View {

    private TextView tv_version;

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_about;
    }

    @Override
    protected void initContentView() {
//        isShowToolView(false);
        setBackBackground(R.mipmap.fanhui_black);
//        setStatusBar(R.color.white);
        isShowCut(false);
        isShowBackButton(true);

        tv_version = findViewById(R.id.tv_version);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String version = CustomUtility.getPackageVersion(getApplicationContext());
        tv_version.setText("V"+version);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
