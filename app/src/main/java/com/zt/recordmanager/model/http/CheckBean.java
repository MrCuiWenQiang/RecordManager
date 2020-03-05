package com.zt.recordmanager.model.http;

import android.util.SparseArray;

import java.util.List;

public class CheckBean {
    private String FrameID;
    private List<FrameCheckBean> Detail;

    public CheckBean() {
    }

    public CheckBean(String frameID, List<FrameCheckBean> detail) {
        FrameID = frameID;
        Detail = detail;
    }

    public String getFrameID() {
        return FrameID;
    }

    public void setFrameID(String frameID) {
        FrameID = frameID;
    }

    public List<FrameCheckBean> getDetail() {
        return Detail;
    }

    public void setDetail(List<FrameCheckBean> detail) {
        Detail = detail;
    }
}
