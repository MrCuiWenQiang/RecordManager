package com.zt.recordmanager.contract;


import java.util.List;

public class OutContract {
    public interface View {
        void scanLabel_Success(String data);

        void scanLabel_Fail(String msg);

        void update_Success(String msg);

        void update_Fail(String msg);
    }

    public interface Presenter {
        void scanframe();
        void update(List<String> tabs);
    }

    public interface Model {

    }
}
