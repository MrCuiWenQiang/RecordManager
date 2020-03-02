package com.zt.recordmanager.presenter;


import android.text.TextUtils;

import com.zt.recordmanager.contract.SettingContract;
import com.zt.recordmanager.util.HttpUtil;
import com.zt.recordmanager.util.RFIDUtil;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;
import cn.faker.repaymodel.util.PreferencesUtility;
import cn.faker.repaymodel.util.error.ErrorUtil;

public class SettingPresenter extends BaseMVPPresenter<SettingContract.View> implements SettingContract.Presenter {

    @Override
    public void attachView(SettingContract.View view) {
        super.attachView(view);
        readPower();
        getHttpSetting();
    }

    @Override
    public void readPower() {
        int power = RFIDUtil.readPower();
        if (power != -1) {
            if (getView() != null) {
                getView().readPower_Success(power);
            }
        } else {
            if (getView() != null) {
                getView().fail("设备功率读取失败!");
            }
        }

    }

    @Override
    public void getHttpSetting() {
        getView().getHttpSettingSuccess(HttpUtil.getHttpSetting_ip(),String.valueOf(HttpUtil.getHttpSetting_port()));
    }

    @Override
    public void settingPower(int number) {
        if (number<=0) {
            getView().fail("功率不能为空!");
            return;
        }
        int power = -1;
        try {
            power = Integer.valueOf(number);
        } catch (Exception e) {
            ErrorUtil.showError(e);
            power = -1;
        }
        if (power==-1){
            getView().fail("功率设置失败!");
            return;
        }else {
            boolean status = RFIDUtil.writePower(power);
            if (status){
                getView().settingPower_success("功率设置成功!");
            }else {
                getView().fail("功率设置失败!");
            }
        }

    }

    @Override
    public void settingHttpSetting(String ip_address, String port) {
        if (TextUtils.isEmpty(ip_address)||TextUtils.isEmpty(port)){
            getView().fail("请填写完整信息!");
            return;
        }
        int portNumber = Integer.valueOf(port);
        if (portNumber<=0){
            getView().fail("请填写完整信息!");
            return;
        }
        HttpUtil.settingHttpSetting(ip_address,portNumber);
        getView().settingSetting_success("设置成功");
    }
}
