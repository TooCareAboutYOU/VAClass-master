package com.dts.vaclass.application.initAppConfigs;

import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * https://mp.weixin.qq.com/s/2z6HKBPETkMLiA2nlXJ3ig
 */

public class ApplicationHelper implements InitMethods {

    private static final String TAG = "ApplicationHelper";

    private static Context mContext;
    private static volatile ApplicationHelper instance=null;
    public ApplicationHelper(){}
    public  static synchronized ApplicationHelper getInstance(){
        if(instance==null){
            synchronized(ApplicationHelper.class){
                if(instance==null){
                    instance=new ApplicationHelper();
                }
            }
        }
        return instance;
    }

    public static void init(Context context){
        mContext=context;
    }


    @Override
    public InitMethods initNetWork() {
        return null;
    }

    @Override
    public InitMethods initDataBase() {
        return null;
    }

    @Override
    public InitMethods initImageLoader() {
        return null;
    }

    @Override
    public InitMethods initShare() {
        return null;
    }

    @Override
    public InitMethods initPush() {
        return null;
    }

    @Override
    public InitMethods initLogin() {
        return null;
    }

    @Override
    public InitMethods iniLog() {
        Logger.init(TAG)//自定义日志TAG
              .logLevel(LogLevel.FULL);//测试阶段设置日志输出
        Logger.e("初始化成功");
        return null;
    }


}
