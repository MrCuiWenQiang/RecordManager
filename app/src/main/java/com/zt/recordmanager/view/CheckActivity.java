package com.zt.recordmanager.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zt.recordmanager.R;
import com.zt.recordmanager.contract.CheckContract;
import com.zt.recordmanager.presenter.CheckPresenter;
import com.zt.recordmanager.util.SoundUtil;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.ToastUtility;

/**
 * 档案盘点
 */
public class CheckActivity extends BaseMVPAcivity<CheckContract.View, CheckPresenter> implements CheckContract.View {


    private SoundUtil soundUtil;

    private QMUITipDialog scanFrameDialog;

    private boolean isStart = false;//防止多次点按物理按键 造成多次请求 value为true时 表示请求正在处理 按键事件不调用

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

    private void scanLabel() {
        if (isStart) {
            return;
        }
        if (scanFrameDialog.isShowing()) {
            // TODO: 2020/2/28 此处扫描柜架
            isStart = true;
            mPresenter.scanframe();
        } else {
//            isStart = true;
            //此处开启或暂停盘点
            mPresenter.scanFile();
        }
    }

    // TODO: 2020/2/27 盘点前先扫描柜架标签
    private void showScanFrameDialog() {
        if (scanFrameDialog == null) {
            scanFrameDialog = new QMUITipDialog.CustomBuilder(this)
                    .setContent(R.layout.dialog_scanframe)
                    .create();
            scanFrameDialog.setCancelable(false);
            scanFrameDialog.findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanLabel();
                }
            });
            scanFrameDialog.findViewById(R.id.bt_exit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanFrameDialog.dismiss();
                    finish();
                }
            });
            scanFrameDialog.findViewById(R.id.contentWrap).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dg_back));
            scanFrameDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == 139 || keyCode == 280) {
                            if (event.getRepeatCount() == 0) {
                                scanLabel();
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
        }
        scanFrameDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void scanLabel_Loading(String msg) {
        scanFrameDialog.dismiss();
        showLoading(msg);
    }

    @Override
    public void scanLabel_Success(String data) {
        soundUtil.success();
        dimiss();
        isStart = false;
    }

    @Override
    public void scanLabel_Fail(String msg) {
        soundUtil.fail();
        dimiss();
        isStart = false;
        scanFrameDialog.show();
        ToastUtility.showToast(msg);
    }

    @Override
    public void scanNewFile(String tag) {
        ToastUtility.showToast(tag);
    }

    @Override
    public void scanNewFile_fail(String err) {
        ToastUtility.showToast(err);
    }
}
