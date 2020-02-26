package com.zt.recordmanager.contract;


import com.zt.recordmanager.model.bean.MainGridBean;

import java.util.List;

public class MainContract {
    public interface View {
        void settingGrid(List<MainGridBean> datas);
    }

    public interface Presenter {
        void initGrid();
    }

    public interface Model {

    }
}
