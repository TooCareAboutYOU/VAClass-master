package com.dts.vaclass.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.dts.vaclass.R;
import com.dts.vaclass.base.BaseActivity;
import com.dts.vaclass.base.broadcast.NetBroadcastReceiver;
import com.dts.vaclass.base.service.LocalIntentService;
import com.dts.vaclass.http.TestApiService;
import com.dts.vaclass.model.MusicBean;
import com.dts.vaclass.model.MoviesBean;
import com.dts.vaclass.base.service.BindService;
import com.dts.vaclass.base.service.ForegroundService;
import com.dts.vaclass.base.service.MessengerService;
import com.dts.vaclass.base.service.ProcessService;
import com.dts.vaclass.utils.manager.ActivityManager;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void setLayoutBefore() { }

    @Override
    protected int setLayoutResourceId() { return R.layout.activity_main; }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.sample_text).setOnClickListener(view -> {
//            new GreenDaoTest().test(this);
            TestApiService.GetMovieData(0,10).enqueue(new Callback<MoviesBean>() {
                @Override
                public void onResponse(Call<MoviesBean> call, Response<MoviesBean> response) {
                    if (response.body() != null) {
                        Logger.i("onResponse"+response.body().getTitle());
                    }
                }

                @Override
                public void onFailure(Call<MoviesBean> call, Throwable t) {
                    t.printStackTrace();
                    Logger.i( "onFailure: "+t.getMessage()+"\n"+t.getLocalizedMessage());
                }
            });
            Intent intent=new Intent(NetBroadcastReceiver.ACTION);
            intent.putExtra("sendMsg","信息来自Activity");
            sendBroadcast(intent);
        });

        findViewById(R.id.music_text).setOnClickListener(v ->{
            TestApiService.GetMusicData("hdedu").enqueue(new Callback<MusicBean>() {
                @Override
                public void onResponse(Call<MusicBean> call, Response<MusicBean> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        Log.i(TAG, "onResponse: "+response.body().getPrograms().size());
                    }else {
                        Log.i(TAG, "onResponse: \n"+response.message()+response.isSuccessful());
                    }
                }

                @Override
                public void onFailure(Call<MusicBean> call, Throwable t) {
                    t.printStackTrace();
                    Log.i(TAG, "onFailure: "+t.getMessage()+"\n"+t.getLocalizedMessage());
                }
            });
        });

        findViewById(R.id.music2_text).setOnClickListener(v -> {
            addCompositeSubscription(TestApiService.GetMusicData2("hdedu", new Observer<MusicBean>() {
                @Override
                public void onCompleted() {
                    Log.i(TAG, "onCompleted: 完成了");
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Log.i(TAG, "onError: "+e.getMessage());
                }

                @Override
                public void onNext(MusicBean musicBean) {
                    if (musicBean != null){
                        Log.i(TAG, "onNext size->>>: "+musicBean.getPrograms().size()+"\n");
                        for (int i = 0; i < musicBean.getPrograms().size(); i++) {
                            Log.i(TAG, "\t\t第 "+(i+1)+"条："+musicBean.getPrograms().get(i).getTitle());
                        }
                    }
                }
            }));
        });

        findViewById(R.id.jump_text).setOnClickListener(v -> {
            Intent intent=BackActivity.newIndexIntent(this,"来自主界面的信息");
            startActivity(intent);
        });

        findViewById(R.id.btn_bottomsheets).setOnClickListener(v -> {
            startActivity(BottomSheetActivity.newIntent(this));
        });


        VoidStartService();
        VoidBindService();
        VoidMessengerService();
        VoidForegroundService();
        VoidIntentService();
    }


    private void VoidStartService() {
        Intent intent=new Intent(this, ProcessService.class);
        findViewById(R.id.btn_startService).setOnClickListener(v -> {
            startService(intent);
            Toast.makeText(this, "开启服务", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_stopService).setOnClickListener(v -> {
            stopService(intent);
            Toast.makeText(this, "关闭服务", Toast.LENGTH_SHORT).show();
        });
    }


    private BindService mService;
    private void VoidBindService() {
        Intent intentBind=new Intent(this, BindService.class);

        ServiceConnection connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("BindService","绑定成功 onServiceConnected: ");

                BindService.LocalBinder binder= (BindService.LocalBinder) service;
                mService=binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("BindService","onServiceDisconnected: "+ name.getClassName());
                mService=null;
            }
        };

        findViewById(R.id.btn_bindService).setOnClickListener(v -> {
            bindService(intentBind,connection, Context.BIND_AUTO_CREATE);
            Toast.makeText(this, "开启绑定服务", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_unbindService).setOnClickListener(v -> {
            if (mService != null) {
                unbindService(connection);
                mService=null;
                Toast.makeText(this, "解除绑定服务", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_bindService_data).setOnClickListener(v -> {
            if (mService !=null) {
                Log.i("BindService", "返回数据："+mService.publicMethos());
            }else {
                Log.i("BindService", "还没绑定: ");
            }
        });
    }


    /**
     * 此处mMessenger 为服务端的 Messenger ，与服务端交互
     */
    private Messenger mMessenger=null;
    private boolean mBound;
    private void VoidMessengerService() {

        Intent intent=new Intent(this, MessengerService.class);

        // 实现与服务端链接的对象
        ServiceConnection mConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // 通过服务端传递的IBinder对象，创建相应的Messenger
                // 通过该Messenger对象与服务端进行交互
                Log.i(MessengerService.TAG, "Service onServiceConnected: ");
                mMessenger=new Messenger(service);
                mBound=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(MessengerService.TAG, "Service onServiceDisconnected: ");
                mMessenger=null;
                mBound=false;
            }
        };

        findViewById(R.id.btn_msg_bindService).setOnClickListener(v -> {
            // 绑定服务端
            bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
            Toast.makeText(this, "绑定msger服务", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_msg_unbindService).setOnClickListener(v -> {
            if (mBound) {
                unbindService(mConnection);
                mBound=false;
                mMessenger=null;
                Toast.makeText(this, "解除msger绑定", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_msg_sendMsg).setOnClickListener(v -> {
            if (!mBound) {
                return;
            }
            //创建于服务交互的消息实体Message
            try {
                Message msg=Message.obtain(null,MessengerService.MSG);

                mMessenger.send(msg);   //
                Log.i(MessengerService.TAG, "Client 发送数据: ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        class ReplyMsgHandler extends Handler{
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MessengerService.MSG_RETURN:{
                        Log.i("MessengerService", "Client接收到Service消息: "+ msg.getData().getString("reply"));
                        break;
                    }
                    default:{
                        super.handleMessage(msg);

                    }
                }
            }
        }

        //创建客户端的 Messenger ，实现服务端消息回复
        Messenger messenger=new Messenger(new ReplyMsgHandler());

        findViewById(R.id.btn_msg_getMsg).setOnClickListener(v -> {
            if (!mBound) {
                return;
            }

            try {
                //创建与服务交互的消息实体Message
                Message msg1=Message.obtain(null,MessengerService.MSG_RETURN,0,0);
                //把接受到的服务端的回复的Messenger通过Message的replyTo参数传递给服务端
                msg1.replyTo=messenger;
                mMessenger.send(msg1);
                Log.i(MessengerService.TAG, "Client 请求Service发送数据: ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }


    private void VoidForegroundService() {
        //显示启动
//        Intent intent=new Intent(this, ForegroundService.class);

        //隐式启动 Android5.0之后google禁止了隐式声明Intent启动Service,
        // 否则报异常： java.lang.IllegalArgumentException: Service Intent must be explicit
        // 添加 intent.setPackage(getPackageName()); 即可
        Intent intent=new Intent();
        intent.setAction("com.dts.vaclass.base.service.ForegroundService");
        intent.setPackage(getPackageName()); //设置应用包名

        findViewById(R.id.btn_startForegroundService).setOnClickListener(v -> {
            intent.putExtra(ForegroundService.INTENT_KEY,ForegroundService.INTENT_INT_VALUE_ZONE);
            startService(intent);
            Log.i(ForegroundService.TAG, "绑定ForegroundService: ");
        });
        findViewById(R.id.btn_stopForegroundService).setOnClickListener(v -> {
            intent.putExtra(ForegroundService.INTENT_KEY,ForegroundService.INTENT_INT_VALUE_ONE);
            stopService(intent);
            Log.i(ForegroundService.TAG, "解绑ForegroundService: ");
        });

    }


    private void VoidIntentService() {
        findViewById(R.id.btn_startIntentService).setOnClickListener(v -> {
            LocalIntentService.startActionFoo(this,"参数一","参数二");
            LocalIntentService.startActionBaz(this,"参数三","参数四");
        });
        findViewById(R.id.btn_stopIntentService).setOnClickListener(v -> {
            LocalIntentService.stopService(this);
        });
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                ActivityManager.getInstance().exitSystem();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
