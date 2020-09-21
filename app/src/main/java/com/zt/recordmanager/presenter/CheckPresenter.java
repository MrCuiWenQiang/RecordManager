package com.zt.recordmanager.presenter;

import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;

import com.zt.recordmanager.constant.Url;
import com.zt.recordmanager.contract.CheckContract;
import com.zt.recordmanager.model.http.FrameCheckBean;
import com.zt.recordmanager.model.http.FrameRFIDBean;
import com.zt.recordmanager.util.RFIDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;
import cn.faker.repaymodel.net.json.JsonUtil;
import cn.faker.repaymodel.net.okhttp3.HttpHelper;
import cn.faker.repaymodel.net.okhttp3.callback.HttpCallback;
import cn.faker.repaymodel.net.okhttp3.callback.HttpResponseCallback;
import cn.faker.repaymodel.util.db.DBThreadHelper;

public class CheckPresenter extends BaseMVPPresenter<CheckContract.View> implements CheckContract.Presenter {

    private String frameRFID;
    private List<String> server_tabs;

    @Override
    public void scanframe() {
        RFIDUtil.readOne(new RFIDUtil.RFIDReadOneAction() {
            @Override
            public void success(String tag) {
                if (getView() != null) {
                    getView().scanLabel_Loading("已扫描到标签,正在确定位置.");
                }
                    HttpHelper.get(Url.GETFRAMEID, new HttpCallback() {
                        @Override
                        public void onSuccess(String result) {
                            List<FrameRFIDBean> frames = JsonUtil.fromList(result, FrameRFIDBean.class);
                            if (frames == null || frames.size() <= 0) {
                                if (getView() != null) {
                                    getView().scanLabel_Fail("服务端未有架签信息,请先添加");
                                }
                                return;
                            }
                            server_tabs = new ArrayList<>();
                            for (FrameRFIDBean item : frames) {
                                server_tabs.add(item.getNumber());
                            }
                            ratio(tag);
                        }

                        @Override
                        public void onFailed(int status, String message) {
                            if (getView() != null) {
                                getView().scanLabel_Fail(message);
                            }
                        }
                    });

            }

            @Override
            public void fail(String msg) {
                if (getView() != null) {
                    getView().scanLabel_Fail(msg);
                }
            }
        });

    }

    private void ratio(String tag) {
        for (String item : server_tabs) {
            if (tag.equals(item)) {
                frameRFID = tag;
                break;
            }
        }
        if (TextUtils.isEmpty(frameRFID)) {
            if (getView() != null) {
                getView().scanLabel_Fail("该标签不是架签");
            }
        } else {
            if (getView() != null) {
                getView().scanLabel_Success("标签是架签");
            }
        }
    }

    private SparseArray<String> datas;
    public boolean isRunScan = false;//盘点是否在运行中
    private RFIDUtil rfidUtil = new RFIDUtil();

    @Override
    public void scanFile() {
        if (isRunScan) {
            rfidUtil.stop();
            isRunScan = false;
            return;
        }
        isRunScan = true;
        if (datas == null) {
            datas = new SparseArray<>();
        }

        rfidUtil.readInventory(new RFIDUtil.RFIDReadInventoryAction() {
            @Override
            public void success(String tag) {
                if (!tag.startsWith("E2E2")){
                    return;
                }

                if (datas.size() <= 0) {
                    datas.put(0, tag);
                    if (getView() != null) {
                        getView().scanNewFile(tag, datas.size());
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
                        getView().scanNewFile(tag, datas.size());
                    }
                } else {
//                    datas.setValueAt(index,tag); 不考虑修改
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
            isRunScan = false;
        }
        if (rfidUtil != null) {
            rfidUtil.stop();
        }
    }

    @Override
    public void updateData() {
        if (TextUtils.isEmpty(frameRFID)) {
            getView().update_Fail("请先扫描架签");
            return;
        }
        if (datas == null || datas.size() <= 0) {
            getView().update_Fail("没有需要上传的档案");
            return;
        }
        List<FrameCheckBean> updatas = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            String item = datas.valueAt(i);
            updatas.add(new FrameCheckBean(item,item));
        }
        updatas.add(new FrameCheckBean(frameRFID,frameRFID));

        HttpHelper.post(Url.POSTRFID,updatas , new HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (getView() != null) {
                    getView().update_success(data);
                }
            }

            @Override
            public void onFailed(int status, String message) {
                if (getView() != null) {
                    getView().update_Fail(message);
                }
            }
        });
    }


}
