package com.zt.recordmanager.contract;



public class SettingContract {
    public interface View {
        void readPower_Success(int power);
        void settingPower_success(String msg);
        void fail(String msg);
        void getHttpSettingSuccess(String ip,String port);

        void settingSetting_success(String msg);
    }

    public interface Presenter {
        void readPower();
        void getHttpSetting();
        void settingPower(int number);
        void settingHttpSetting(String ip_address,String port);
    }

    public interface Model {

    }
}
