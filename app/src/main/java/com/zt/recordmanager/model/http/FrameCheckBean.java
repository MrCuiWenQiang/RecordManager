package com.zt.recordmanager.model.http;

public class FrameCheckBean {
    private String LabelID;

    public FrameCheckBean() {
    }

    public FrameCheckBean(String labelID) {
        LabelID = labelID;
    }

    public String getLabelID() {
        return LabelID;
    }

    public void setLabelID(String labelID) {
        LabelID = labelID;
    }
}
