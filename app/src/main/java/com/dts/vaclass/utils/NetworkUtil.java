package com.dts.vaclass.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

/**
 * Created by zs on 2018/3/6.
 */

public class NetworkUtil {

    public static final int NETTYPE_WIFI=0x01;
    public static final int NETTYPE_CMWAP=0x02;
    public static final int NETTYPE_CMNET=0x03;

    /**
     * 检测网路是否可用
     */
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=null;
        if (cm != null) {
            networkInfo=cm.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isAvailable();
    }

    /**
     * 获取当前网络状态
     */
    public static int getNetworkType(Context context){
        int netType=0;
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=null;
        if (cm != null) {
            networkInfo=cm.getActiveNetworkInfo();
        }
        if (networkInfo == null) {
            return netType;
        }
        int nType=networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo=networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType=NETTYPE_CMNET;
                }else {
                    netType=NETTYPE_CMWAP;
                }
            }
        }else if (nType == ConnectivityManager.TYPE_WIFI){
            netType=NETTYPE_WIFI;
        }

        return netType;
    }

}
