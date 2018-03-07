package com.dts.vaclass.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.EventLogTags;
import android.util.Log;

/**
 * Created by zs on 2018/2/8.
 */

public class FingerPrintApiUtils {

    private static final String TAG = "FingerPrintApiUtils";

    private static volatile FingerPrintApiUtils instance=null;
    public FingerPrintApiUtils(){}
    public  static synchronized FingerPrintApiUtils getInstance(){
        if(instance==null){
            synchronized(FingerPrintApiUtils.class){
                if(instance==null){
                    instance=new FingerPrintApiUtils();
                }
            }
        }
        return instance;
    }

    private FingerprintManagerCompat mManagerCompat=null;
    private FingerprintManager mFingerprintManager=null;

    public void init(Context context){
        Log.i(TAG, "init: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "init: >= 23");
            mFingerprintManager= (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            mFingerprintManager.authenticate(null,null,0,new MyCallBack(),null);
        }else {
            Log.i(TAG, "init: < 23");
            mManagerCompat=FingerprintManagerCompat.from(context);
            mManagerCompat.authenticate(null,0,null,new MyCallBackCompat(),null);
        }
    }


    class MyCallBackCompat extends FingerprintManagerCompat.AuthenticationCallback{

        private static final String TAG = "MyCallBackCompat";

//        public MyCallBackCompat() {
//            super();
//        }

        //当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            Log.i(TAG, "onAuthenticationError: "+errString);

        }

        //当指纹验证失败的时候回调此函数，失败之后允许多次尝试，失败次数过多会停止一段时间，然后再停止sensor工作
        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            Log.i(TAG, "onAuthenticationHelp: "+helpString);
        }

        //当指纹验证成功后的时候回调此函数，然后再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Log.i(TAG, "onAuthenticationSucceeded: 验证成功");
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Log.i(TAG, "onAuthenticationFailed: 验证失败");
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    class MyCallBack extends FingerprintManager.AuthenticationCallback{
        private static final String TAG = "MyCallBack";
//        public MyCallBack() {
//            super();
//        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            Log.i(TAG, "onAuthenticationError: "+errString);
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            Log.i(TAG, "onAuthenticationHelp: "+helpString);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Log.i(TAG, "onAuthenticationSucceeded: 验证成功");
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Log.i(TAG, "onAuthenticationFailed: 验证失败");
        }
    }

    

}
