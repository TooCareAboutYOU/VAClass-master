package com.dts.vaclass.application;

import android.app.Application;
import android.content.Context;

import com.dts.vaclass.application.initAppConfigs.ApplicationHelper;
import com.dts.vaclass.lib_subutils.SubUtils;
import com.dts.vaclass.lib_utils.util.Utils;

/**
 * Created by zs on 2018/2/8.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public static MyApplication sMyApplication;

    @Override
    public void onCreate() {
        super.onCreate();
//        ApplicationConfig.init(this,"app_demo");
//        ApplicationConfig.getInstance().initFingerPrint();


        ApplicationHelper.init(this);
        ApplicationHelper.getInstance().iniLog();

        Utils.init(this);
        SubUtils.init(this);
    }


    public static synchronized MyApplication getInstance(){ return sMyApplication; }


}
