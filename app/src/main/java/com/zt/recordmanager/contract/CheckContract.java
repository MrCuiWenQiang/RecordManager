package com.zt.recordmanager.contract;


public class CheckContract {
    public interface View {
        void scanLabel_Loading(String msg);

        void scanLabel_Success(String data);

        void scanLabel_Fail(String msg);

        void scanNewFile(String tag);

        void scanNewFile_fail(String err);
    }

    public interface Presenter {
        void scanframe();
        void scanFile();
        void cleanScan();
    }

    public interface Model {

    }
}
