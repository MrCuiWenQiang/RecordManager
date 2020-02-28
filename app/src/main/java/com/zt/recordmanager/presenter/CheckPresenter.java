package com.zt.recordmanager.presenter;

import android.util.SparseArray;

import com.zt.recordmanager.contract.CheckContract;
import com.zt.recordmanager.util.RFIDUtil;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;

public class CheckPresenter extends BaseMVPPresenter<CheckContract.View> implements CheckContract.Presenter {


    @Override
    public void scanframe() {
        RFIDUtil.readOne(new RFIDUtil.RFIDReadOneAction() {
            @Override
            public void success(String tag) {
                if (getView() != null) {
                    getView().scanLabel_Loading("已扫描到标签,正在确定位置.");
                    // TODO: 2020/2/27 检测到标签后 检查是否是架签 再去盘点

                    getView().scanLabel_Success("位置xxxxxxxx");
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

    private SparseArray<String> datas;
    public boolean isRunScan = false;//盘点是否在运行中

    @Override
    public void scanFile() {
        if (isRunScan) {
            RFIDUtil.stop();
            isRunScan = false;
            return;
        }
        isRunScan = true;
        if (datas == null) {
            datas = new SparseArray<>();
        }

        RFIDUtil.readInventory(new RFIDUtil.RFIDReadInventoryAction() {
            @Override
            public void success(String tag) {
                synchronized (datas) {
                    if (datas.size() == 0) {
                        datas.put(0, tag);
                        if (getView() != null) {
                            getView().scanNewFile(tag);
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
                            getView().scanNewFile(tag);
                        }
                    } else {
//                    datas.setValueAt(index,tag); 不考虑修改
                    }
                }
            }

            @Override
            public void fail(String msg) {
                if (getView() != null) {
                    getView().scanNewFile_fail("扫描失败");
                }
            }
        });
    }

    @Override
    public void cleanScan() {
        if (datas != null) {
            datas.clear();
            RFIDUtil.stop();
            isRunScan = false;
        }
    }
}
