package com.zt.recordmanager.contract;


public class CheckContract {
    public interface View {
        void scanLabel_Loading(String msg);

        void scanLabel_Success(String data);

        void scanLabel_Fail(String msg);

        void scanNewFile(String tag,int number);

        void scanNewFile_fail(String err);

        void update_success(String msg);
        void update_Fail(String msg);
    }

    public interface Presenter {
        void scanframe();
        void scanFile();
        void cleanScan();
        void updateData();
    }

    public interface Model {

    }
}
