package com.zt.recordmanager.contract;




public class QueryContract {
    public interface View {
        void scanLabel_Fail(String msg);

        void scanLabel_Success(String tag);
    }

    public interface Presenter {
        void scanLabel();
    }

    public interface Model {

    }
}
