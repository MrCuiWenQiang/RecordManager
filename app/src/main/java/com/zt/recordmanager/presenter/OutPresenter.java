package com.zt.recordmanager.presenter;

import android.util.SparseArray;

import com.zt.recordmanager.contract.AboutContract;
import com.zt.recordmanager.contract.OutContract;
import com.zt.recordmanager.util.RFIDUtil;

import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;

public class OutPresenter extends BaseMVPPresenter<OutContract.View> implements OutContract.Presenter {
    private SparseArray<String> datas = new SparseArray<>();
    @Override
    public void scanframe() {
        RFIDUtil.readOne(new RFIDUtil.RFIDReadOneAction() {
            @Override
            public void success(String tag) {
                if (datas.size() == 0) {
                    datas.put(0, tag);
                    if (getView() != null) {
                        getView().scanLabel_Success(tag);
                    }
                    return;
                }
                int index = -1;
                int size = datas.size();
                for (int i = 0; i < size; i++) {
                    String value = datas.valueAt(i);
                    if (tag.equals(value)) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    datas.put(size, tag);
                    if (getView() != null) {
                        getView().scanLabel_Success(tag);
                    }
                } else {
//                    datas.setValueAt(index,tag); 不考虑修改
                    if (getView() != null) {
                        getView().scanLabel_Fail("未扫描到标签");
                    }
                }
            }

            @Override
            public void fail(String msg) {
                if (getView() != null) {
                    getView().scanLabel_Fail(msg);
                }
            }
        });
    }

    @Override
    public void update(List<String> tabs) {
        if (tabs==null||tabs.size()<=0){
            getView().update_Fail("暂无数据上传!");
            return;
        }
        datas.clear();
        getView().update_Success("上传成功");
    }

}
