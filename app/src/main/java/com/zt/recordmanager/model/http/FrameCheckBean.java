package com.zt.recordmanager.model.http;

public class FrameCheckBean {
    private String FrameId;
    private String FrameNo;

    public FrameCheckBean() {
    }

    public FrameCheckBean(String frameId) {
        FrameId = frameId;
    }

    public FrameCheckBean(String frameId, String frameNo) {
        FrameId = frameId;
        FrameNo = frameNo;
    }

    public String getFrameId() {
        return FrameId;
    }

    public void setFrameId(String frameId) {
        FrameId = frameId;
    }

    public String getFrameNo() {
        return FrameNo;
    }

    public void setFrameNo(String frameNo) {
        FrameNo = frameNo;
    }
}
