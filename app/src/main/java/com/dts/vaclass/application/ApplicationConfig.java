package com.dts.vaclass.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;

import com.dts.vaclass.utils.SharedPreferencesHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zs on 2018/2/8.
 */

public class ApplicationConfig {

    private static final String HAS_FINGERPRINT_API = "hasFingerprintApi";
    private static final String SETTINGS = "Settings";

    public static void init(Context context,String name){
        SharedPreferencesHelper.init(context,name, MODE_PRIVATE);
    }

    private static volatile ApplicationConfig instance=null;
    public ApplicationConfig(){}
    public  static synchronized ApplicationConfig getInstance(){
        if(instance==null){
            synchronized(ApplicationConfig.class){
                if(instance==null){
                    instance=new ApplicationConfig();
                }
            }
        }
        return instance;
    }

    //指纹
    public void initFingerPrint(){
        //检测是否存在该值，不必每次都通过反射来检查
        if (SharedPreferencesHelper.mSharedPreferences.contains(HAS_FINGERPRINT_API)) {
            return;
        }
        try {
            //tongue反射判断是否存在
            Class.forName("android.hardware.fingerprint.FingerprintManager");
            SharedPreferencesHelper.getInstance().setBooleanValue(HAS_FINGERPRINT_API,true);
        } catch (ClassNotFoundException e) {
            SharedPreferencesHelper.getInstance().setBooleanValue(HAS_FINGERPRINT_API,false);
            e.printStackTrace();
        }


    }

}
