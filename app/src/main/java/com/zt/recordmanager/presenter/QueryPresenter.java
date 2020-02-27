package com.zt.recordmanager.presenter;

import com.zt.recordmanager.contract.QueryContract;
import com.zt.recordmanager.util.RFIDUtil;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;

public class QueryPresenter extends BaseMVPPresenter<QueryContract.View> implements QueryContract.Presenter {

    private boolean isStart = false;

    @Override
    public void scanLabel() {
        if (isStart) {
            return;
        }
        isStart = true;
        RFIDUtil.readOne(new RFIDUtil.RFIDReadOneAction() {
            @Override
            public void success(String tag) {
                isStart = false;
                if (getView() != null) {
                    getView().scanLabel_Success(tag);
                    // TODO: 2020/2/27 检测到标签后 检查是否是架签 再去请求搜索
                }
            }

            @Override
            public void fail(String msg) {
                isStart = false;
                if (getView() != null) {
                    getView().scanLabel_Fail(msg);
                }
            }
        });
    }
}
