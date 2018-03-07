package com.dts.vaclass.base.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Messenger服务通信
 */

public class MessengerService extends Service {

    public static final String TAG = "MessengerService";
    public static final int MSG=1;
    public static final int MSG_RETURN=2;

    /**
     * 用于接受来自客户端传递过来的数据
     */
    class InComingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG:{
                    Log.i(TAG, "Service 接收到客户端消息");
                    break;
                }
                case MSG_RETURN:{
                    try {
                        // 回复客户端信息，该对象有客户端传递过来
                        Messenger client=msg.replyTo;
                        //获取回复信息的消息实体
                        Message replyMsg=Message.obtain(null,MSG_RETURN);
                        Bundle bundle=new Bundle();
                        bundle.putString("reply","response the client request");
                        replyMsg.setData(bundle);
                        //向客户端发送消息
                        client.send(replyMsg);
                        Log.i(TAG, "Service接收到Client请求，向客户端发送消息 bundle数据包");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:{
                    super.handleMessage(msg);
                }
            }
        }
    }

    /**
     * 创建Messenger并传入Handler实例对象
     */
    Messenger mMessenger=new Messenger(new InComingHandler());


    /**
     * 当绑定Sevice时，该方法呗调用将通过mMessenger返回一个实现
     * IBinder 接口实例对象
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind: ");
        return mMessenger.getBinder();
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
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service onDestroy: ");
    }
}
