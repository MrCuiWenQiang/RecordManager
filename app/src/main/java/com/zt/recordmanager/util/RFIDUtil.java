package com.zt.recordmanager.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.rscja.deviceapi.RFIDWithUHF;

import cn.faker.repaymodel.util.LogUtil;
import cn.faker.repaymodel.util.db.DBThreadHelper;
import cn.faker.repaymodel.util.error.ErrorUtil;

public class RFIDUtil {
    private static RFIDWithUHF mReader;
    private Handler mainHandler;
    private ReadInventoryCallBack callBack;
    private static Thread jobThread;

    /**
     * 初始化
     *
     * @param action
     */
    public synchronized final static void init(RFIDInitAction action) {
        try {
            mReader = RFIDWithUHF.getInstance();
        } catch (Exception ex) {
            ErrorUtil.showError(ex);
            action.fail("设备初始化失败");
            return;
        }
        if (mReader != null) {
            jobThread = DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {
                @Override
                protected Boolean jobContent() throws Exception {
                    return mReader.init();
                }

                @Override
                protected void jobEnd(Boolean o) {
                    if (o.booleanValue()) {
                        action.success();
                    } else {
                        action.fail("设备初始化失败");
                    }
                    DBThreadHelper.removeJob(jobThread);
                }
            });
        }
    }

    /**
     * 读取一次
     *
     * @param action
     */
    public synchronized final static void readOne(RFIDReadOneAction action) {
        if (mReader == null) {
            action.fail("设备未初始化");
            return;
        }
        String strUII = mReader.inventorySingleTag();
        if (!TextUtils.isEmpty(strUII)){
            String strEPC = mReader.convertUiiToEPC(strUII);
            if (!TextUtils.isEmpty(strEPC)){
                action.success(strEPC);
            }else {
                action.fail("未识别到标签");
            }
        } else {
            action.fail("未识别到标签");
        }
    }

    /**
     * 重复读取
     *
     * @param action
     */
    public void readInventory(RFIDReadInventoryAction action) {
        if (mReader == null) {
            action.fail("设备未初始化");
            return;
        }
        if (mReader.startInventoryTag((byte) 0, (byte) 0)) {
            if (mainHandler == null) {
                mainHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        String result = msg.obj + "";
                        String[] strs = result.split("@");
                        String tagUII = strs[0];
                        action.success(tagUII);
                    }
                };
            }
            callBack = new ReadInventoryCallBack();
            callBack.setAction(action);
            callBack.handler = mainHandler;
            jobThread = DBThreadHelper.startThreadInPool(callBack);
        } else {
            mReader.stopInventory();
        }
    }

    /**
     * 停止读取
     */
    public void stop() {
        mReader.stopInventory();
        if (callBack != null) {
            callBack.startTAG = false;
            callBack = null;
        }
    }

    public static int readPower() {
        if (mReader != null) {
            return mReader.getPower();
        } else {
            return -1;
        }
    }

    public static boolean writePower(int power) {
        if (mReader != null) {
            return mReader.setPower(power);
        } else {
            return false;
        }
    }

    public static void exit() {
        if (mReader != null) {
            mReader.free();
        }
    }

    public interface RFIDReadInventoryAction {
        void success(String tag);

        void fail(String msg);
    }

    public interface RFIDReadOneAction {
        void success(String tag);

        void fail(String msg);
    }

    public interface RFIDInitAction {
        void success();

        void fail(String msg);
    }


    private class ReadInventoryCallBack extends DBThreadHelper.ThreadCallback {

        private RFIDReadInventoryAction action;

        public Handler handler;

        public boolean startTAG = true;

        public void setAction(RFIDReadInventoryAction action) {
            this.action = action;
        }

        @Override
        protected Object jobContent() throws Exception {
            String[] res = null;
            String strTid;
            String strResult;
            while (startTAG) {
                res = mReader.readTagFromBuffer();
                if (res != null) {
                    strTid = res[0];
                    if (!strTid.equals("0000000000000000") && !strTid.equals("000000000000000000000000")) {
                        strResult = "TID:" + strTid + "\n";
                    } else {
                        strResult = "";
                    }
                    Message msg = handler.obtainMessage();
//                    msg.obj = strResult + "EPC:" + mReader.convertUiiToEPC(res[1]) + "@" + res[2];
                    msg.obj = mReader.convertUiiToEPC(res[1]) + "@" + res[2];
                    handler.sendMessage(msg);
                }
            }
            return null;
        }

        @Override
        protected void jobEnd(Object o) {
            DBThreadHelper.removeJob(jobThread);
        }


    }
}
