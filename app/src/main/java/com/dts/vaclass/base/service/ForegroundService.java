package com.dts.vaclass.base.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dts.vaclass.R;

/**
 * 前台服务
 */

public class ForegroundService extends Service {

    public static final String TAG = "ForegroundService";

    // id 不可以设置为0，否则不能设置为前台服务
    private static final int NOTIFICATION_ID=0x0001;

    private boolean isRmove=false;  //是否移除状态栏通知

    public static final String INTENT_KEY="cmd";
    public static final int INTENT_INT_VALUE_ZONE=0;
    public static final int INTENT_INT_VALUE_ONE=1;

    /**
     * 创建 通知栏
     */
    private void createNotification(){
        //使用兼容版本
        Notification.Builder builder=new Notification.Builder(this);
        //设置状态栏通知图标
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        //设置通知栏横条的图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground));
        //禁止用户点击删除按钮
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //右上角的时间显示
        builder.setShowWhen(true);
        //设置通知栏的标题内容
        builder.setContentTitle("I am Foreground Service");
        //设置通知栏文字
        builder.setContentText("我本楚狂人，凤歌笑孔丘");
        //创建通知
        Notification notification=builder.build();
        //设置为前台服务
        startForeground(NOTIFICATION_ID,notification);
        Log.i(TAG, "createNotification: 完成");
    }

    /**
     * 兼容8.0
     */
    void createNotification8(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelID = "1";
            String channelName = "channel_name";
            NotificationChannel channel = null;
            channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Notification.Builder builder = new Notification.Builder(this);
            //设置状态栏通知图标
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            //设置通知栏横条的图标
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground));
            //禁止用户点击删除按钮
            builder.setAutoCancel(false);
            //禁止滑动删除
            builder.setOngoing(true);
            //右上角的时间显示
            builder.setShowWhen(true);
            builder.setContentText("我本楚狂人，凤歌笑孔丘");
            builder.setContentTitle("I am Foreground Service");
            //创建通知时指定channelID
            builder.setChannelId(channelID);
            Notification notification = builder.build();
            startForeground(NOTIFICATION_ID,notification);
            Log.i(TAG, "createNotification: 完成（适配8.0）");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service onCreate: ");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand: ");
        int i=intent.getExtras().getInt(INTENT_KEY);
        if (i==INTENT_INT_VALUE_ZONE) {
            if (!isRmove) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotification8();
                }else {
                    createNotification();
                }
            }
            isRmove=true;
        }else if (i==INTENT_INT_VALUE_ONE) {
            //移除前台服务
            if (isRmove) {
                stopForeground(isRmove);
            }
            isRmove=false;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind: ");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Service onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.i(TAG, "Service unbindService: ");
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);
        Log.i(TAG, "Service unregisterReceiver: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service onDestroy: ");
        //移除前台服务
        if (isRmove) {
            stopForeground(isRmove);
        }
        isRmove=false;
    }
    /** 保证服务不被杀死
     *
     * 因内存资源不足而杀死Service这种情况比较容易处理，
     * 可将onStartCommand() 方法的返回值设为 START_STICKY或START_REDELIVER_INTENT ，
     * 该值表示服务在内存资源紧张时被杀死后，在内存资源足够时再恢复。也可将Service设置为前台服务，
     * 这样就有比较高的优先级，在内存资源紧张时也不会被杀掉。这两点的实现，我们在前面已分析过和实现过这里就不重复。
     * onStartCommand 返回 START_STICKY或START_REDELIVER_INTENT
     *
     */
}

