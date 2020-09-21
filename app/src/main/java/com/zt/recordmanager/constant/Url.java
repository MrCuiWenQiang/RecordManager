package com.zt.recordmanager.constant;

import com.zt.recordmanager.util.HttpUtil;

public class Url {
    private static final String baseUrl = HttpUtil.getHttpSetting();
    public static final String ip = "http://192.168.1.10";
    public static final int port = 8016;

    public static final String GETFRAMEID = baseUrl + "/GetFrameId";
    public static final String POSTRFID = baseUrl + "/PostFrameModel";
}
