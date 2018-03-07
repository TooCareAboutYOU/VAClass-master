package com.dts.vaclass.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 普通服务
 * 参考资料  http://blog.csdn.net/javazejian/article/details/52709857
 */

public class ProcessService extends Service {

    private static final String TAG = "ProcessService";

    /**
     * bindService时才会调用
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "ProcessService ---> onBind: ");
        return null;
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用onStartCommand或者onBind之前）
     * 如果服务已在运行，则不会调用此方法。该方法只会被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "ProcessService ---> onCreate: invoke");
    }

    /**
     * 每次通过startService方法启动Service时都会被回调
     * bindService时不会调用
     * @param intent 启动时，启动组件传递过来的intent
     * @param flags 表示启动请求时是否有额外数据，可选值
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "ProcessService ---> onStartCommand: ");
        switch (flags) {

        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ProcessService ---> onDestroy: ");
    }
}
