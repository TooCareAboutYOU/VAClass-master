package com.dts.vaclass.base.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 绑定服务
 */

public class BindService extends Service {

    private static final String TAG = "BindService";


    private LocalBinder mBinder=new LocalBinder();
    private boolean quit=false;
    private Thread mThread;
    private int count=0;

    /**
     * 创建Binder对象，返回给客户端调用，提供数据交换的接口
     */
    public class LocalBinder extends Binder{
        // 声明一个方法，getService（提供客户端调用）
        public BindService getService(){
            Log.i(TAG,"获取对象");
            // 返回当前对象BindService，这样客户端调用Service的公共方法
            return BindService.this;
        }
    }

    /**
     * 把mBinder类返回客户端
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "BindService ---> onBind: ");
        return mBinder;
    }

    /**
     * 解除绑定时调用
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "BindService ---> onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.i(TAG, "unbindService: ");
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用onStartCommand或者onBind之前）
     * 如果服务已在运行，则不会调用此方法。该方法只会被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "BindService ---> onCreate: invoke");

        mThread=new Thread(new Runnable() {
            @Override
            public void run() {
                // 每隔1秒count加1，知道quit为true
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        });
        mThread.start();

    }

    // 公共方法
    public int publicMethos(){
        Log.i(TAG, "BindService ---> publicMethos: "+ count);
        return count;
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit=true;
        Log.i(TAG, "BindService ---> onDestroy: ");
    }
}
