package com.zt.recordmanager.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zt.recordmanager.R;
import com.zt.recordmanager.contract.CheckContract;
import com.zt.recordmanager.presenter.CheckPresenter;
import com.zt.recordmanager.util.SoundUtil;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;

/**
 * 档案盘点
 */
public class CheckActivity extends BaseMVPAcivity<CheckContract.View, CheckPresenter> implements CheckContract.View {


    private SoundUtil soundUtil;

    private  QMUITipDialog scanFrameDialog;

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_check;
    }

    @Override
    protected void initContentView() {
        isShowToolView(true);
        isShowBackButton(true);
        setTitle("档案盘点", R.color.white);
        setToolBarBackgroundColor(R.color.win_start);
        setStatusBar(R.color.win_start);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        soundUtil = new SoundUtil(this);
        showScanFrameDialog();
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
    }

    // TODO: 2020/2/27 盘点前先扫描柜架标签
    private void showScanFrameDialog() {
        if (scanFrameDialog==null){
            scanFrameDialog = new QMUITipDialog.CustomBuilder(this)
                    .setContent(R.layout.dialog_scanframe)
                    .create();
            scanFrameDialog.setCancelable(false);
            scanFrameDialog.findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            scanFrameDialog.findViewById(R.id.bt_exit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanFrameDialog.dismiss();
                    finish();
                }
            });
        }
        scanFrameDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
