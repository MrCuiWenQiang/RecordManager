package com.zt.recordmanager;


import cn.faker.repaymodel.BasicApplication;
import cn.faker.repaymodel.net.okhttp3.HttpHelper;
import cn.faker.repaymodel.util.LogUtil;
import cn.faker.repaymodel.util.PreferencesUtility;
import cn.faker.repaymodel.util.ToastUtility;

public class MyApplication extends BasicApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtility.setToast(getApplicationContext());
        LogUtil.isShow = true;
        HttpHelper.init();
        PreferencesUtility.setPreferencesUtility(getApplicationContext(),"Manager");
    }

}
