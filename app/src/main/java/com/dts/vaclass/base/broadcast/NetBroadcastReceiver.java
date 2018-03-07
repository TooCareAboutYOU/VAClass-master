package com.dts.vaclass.base.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.dts.vaclass.base.BaseActivity;
import com.dts.vaclass.base.service.ProcessService;
import com.dts.vaclass.utils.NetworkUtil;

/**
 * Created by zs on 2018/3/6.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION="com.example.broadcastreciver.test";

    @Override
    public void onReceive(Context context, Intent intent) {
        String str=intent.getExtras().getString("sendMsg");
        Log.i(BaseActivity.TAG, "onReceive: 1\t\t"+str);
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean networkState= NetworkUtil.isNetworkConnected(context);
            Log.i(BaseActivity.TAG, "onReceive: 2");
            if (BaseActivity.netEvent !=null) {
                BaseActivity.netEvent.onNetChange(networkState);
                Log.i(BaseActivity.TAG, "onReceive: 3\t\t"+networkState);
            }
        }
    }

    public interface NetChangeListener{
        void onNetChange(boolean networkState);
    }
}
