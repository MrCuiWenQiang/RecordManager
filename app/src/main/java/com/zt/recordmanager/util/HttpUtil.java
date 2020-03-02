package com.zt.recordmanager.util;

import android.text.TextUtils;

import cn.faker.repaymodel.util.PreferencesUtility;

public class HttpUtil {
    private final static String ip_address = "192.168.0.1";
    private final static String IPKEY = "IPKEY";
    private final static String PORTKEY = "PORTKEY";
    private final static int port = 8090;

    public static void settingHttpSetting(String ip_address, int port) {
        PreferencesUtility.setPreferencesField(IPKEY, ip_address);
        PreferencesUtility.setPreferencesField(PORTKEY, port);
    }

    public static String getHttpSetting() {
        return getHttpSetting_ip() + ":" + getHttpSetting_port();
    }

    public static String getHttpSetting_ip() {
        String nowIp = PreferencesUtility.getPreferencesAsString(IPKEY);
        if (TextUtils.isEmpty(nowIp)) {
            return ip_address;
        } else {
            return nowIp;
        }
    }

    public static int getHttpSetting_port() {
        int nowPort = PreferencesUtility.getPreferencesAsInt(PORTKEY);
        if (nowPort <= 0) {
            return port;
        } else {
            return nowPort;
        }
    }
}
