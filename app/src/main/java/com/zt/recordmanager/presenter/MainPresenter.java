package com.zt.recordmanager.presenter;

import com.zt.recordmanager.R;
import com.zt.recordmanager.contract.MainContract;
import com.zt.recordmanager.model.bean.MainGridBean;

import java.util.ArrayList;
import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;

public class MainPresenter extends BaseMVPPresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void initGrid() {
        List<MainGridBean> datas = new ArrayList<>();
        MainGridBean d1 = new MainGridBean("查询", R.mipmap.grid_query,R.color.grid_one, null);
        MainGridBean d2 = new MainGridBean("盘点", R.mipmap.grid_check,R.color.grid_two, null);
        MainGridBean d3 = new MainGridBean("出库", R.mipmap.grid_out,R.color.grid_three, null);
        MainGridBean d4 = new MainGridBean("入库", R.mipmap.grid_in,R.color.grid_five, null);
        MainGridBean d5 = new MainGridBean("设置", R.mipmap.grid_setting,R.color.grid_four, null);
        MainGridBean d6 = new MainGridBean("关于", R.mipmap.grid_about,R.color.grid_six, null);


        datas.add(d1);
        datas.add(d2);
        datas.add(d3);
        datas.add(d4);
        datas.add(d5);
        datas.add(d6);
        getView().settingGrid(datas);
    }
}
