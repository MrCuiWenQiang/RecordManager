package com.zt.recordmanager.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.zt.recordmanager.R;
import com.zt.recordmanager.contract.SettingContract;
import com.zt.recordmanager.presenter.SettingPresenter;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.ToastUtility;

public class SettingActivity extends BaseMVPAcivity<SettingContract.View, SettingPresenter>
        implements SettingContract.View, View.OnClickListener {

    private TextView tv_power;
    private Button btnSetPower;
    private Button btnSetHttp;
    private String[] values;
    private int value = -1;

    private EditText et_ip;
    private EditText et_port;

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_setting;
    }

    @Override
    protected void initContentView() {
        isShowToolView(true);
        isShowBackButton(true);
        setTitle("系统设置", R.color.white);
        setToolBarBackgroundColor(R.color.win_start);
        setStatusBar(R.color.win_start);

        tv_power = findViewById(R.id.tv_power);
        btnSetPower = findViewById(R.id.btnSetPower);
        btnSetHttp = findViewById(R.id.btnSetHttp);
        et_ip = findViewById(R.id.et_ip);
        et_port = findViewById(R.id.et_port);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_power.setOnClickListener(this);
        btnSetPower.setOnClickListener(this);
        btnSetHttp.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void readPower_Success(int power) {
        tv_power.setText(String.valueOf(power));
    }

    @Override
    public void settingPower_success(String msg) {
        ToastUtility.showToast(msg);
    }

    @Override
    public void fail(String msg) {
        ToastUtility.showToast(msg);
    }

    @Override
    public void getHttpSettingSuccess(String ip, String port) {
        et_ip.setText(ip);
        et_port.setText(port);
    }

    @Override
    public void settingSetting_success(String msg) {
        QMUIKeyboardHelper.hideKeyboard(et_port);
        ToastUtility.showToast(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_power: {
                showSelectDialog();
                break;
            }
            case R.id.btnSetPower: {
                mPresenter.settingPower(value);
                break;
            } case R.id.btnSetHttp: {
                mPresenter.settingHttpSetting(getEditTextValue(et_ip),getEditTextValue(et_port));
                break;
            }
        }
    }

    private void showSelectDialog() {
        if (values == null) {
            values = getResources().getStringArray(R.array.arrayPower);
        }
        showListDialog("功率选择", values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sel = values[which];
                value = Integer.parseInt(sel);
                tv_power.setText(sel);
                dialog.dismiss();
            }
        });
    }
}
