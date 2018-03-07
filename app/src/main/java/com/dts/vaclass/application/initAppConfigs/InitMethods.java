package com.dts.vaclass.application.initAppConfigs;

/**
 * 初始化所有方法的顶层接口
 * Created by zs on 2018/2/26.
 */

public interface InitMethods {

    //初始化网络库
    InitMethods initNetWork();

    //初始化数据库
    InitMethods initDataBase();

    //初始化图片库
    InitMethods initImageLoader();

    //初始化分享
    InitMethods initShare();

    //初始化推送
    InitMethods initPush();

    //初始化第三方登录
    InitMethods initLogin();

    //初始化Log库
    InitMethods iniLog();


}
