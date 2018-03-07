package com.dts.vaclass.base;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.dts.vaclass.R;
import com.dts.vaclass.base.broadcast.NetBroadcastReceiver;
import com.dts.vaclass.utils.StatusBarViewUtils;
import com.dts.vaclass.utils.manager.ActivityManager;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetChangeListener {

    public static final String TAG = "BaseActivity";

    protected abstract void setLayoutBefore();
    protected abstract int setLayoutResourceId();
    protected abstract void initView(Bundle savedInstanceState);

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription(){
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription=new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    public void addCompositeSubscription(Subscription subscription){
        if (this.mCompositeSubscription == null){
            this.mCompositeSubscription=new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    public static NetBroadcastReceiver mNetBroadcastReceiver;
    public static NetBroadcastReceiver.NetChangeListener netEvent;
    private IntentFilter intentFilter;


    //singleTask 模式下的回调使用
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarViewUtils.getInstance().init(this, R.color.colorAccent);
        setLayoutBefore();
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceId());
        //隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //沉浸效果
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        //添加Activity工具类
        ActivityManager.getInstance().addActivity(this);
        initView(savedInstanceState);

        //初始化netEvent
        netEvent=this;
//        mNetBroadcastReceiver=new NetBroadcastReceiver();
//        intentFilter=new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

    }

    @Override
    protected void onStart() {
        super.onStart();
//        registerReceiver(mNetBroadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(mNetBroadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (this.mCompositeSubscription != null && this.mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
        //Activity销毁时，提示系统回收
        System.gc();
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    /**
     * 检测权限，false：没有该权限,true：有该权限
     */
    public boolean isHasPermission(String... permission){
        for (String s : permission) {
            if (ContextCompat.checkSelfPermission(this,s) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求
     */
    public void requestpermission(int code,String... permission){
        ActivityCompat.requestPermissions(this,permission,code);
    }

    /**
     * 处理请求权限结果事件
     * @param requestCode 请求码
     * @param permissions 权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionsResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果事件
     * @param requestCode 请求码
     * @param grantResults 权限组
     */
    protected void doRequestPermissionsResult(int requestCode, int[] grantResults){

    }

    /**
     * 网络状态改变事件监听
     * @param networkState
     */
    @Override
    public void onNetChange(boolean networkState) {
        Log.i(TAG, "onNetChange: "+networkState);
    }

    /**
     * 判断前后台
     * @return
     */
    public String getForegroundApp(Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager sUsageStatsManager=(UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
            long ts=System.currentTimeMillis();
            List<UsageStats> queryUsageStats=sUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,ts-2000,ts);
            if (queryUsageStats == null || queryUsageStats.isEmpty()) {
                return null;
            }

            UsageStats recentStats=null;
            for (UsageStats queryUsageStat : queryUsageStats) {
                if (recentStats == null || recentStats.getLastTimeUsed() < queryUsageStat.getLastTimeUsed()) {
                    recentStats=queryUsageStat;
                }
            }
            return recentStats.getPackageName();
        }
        return null;
    }
}
