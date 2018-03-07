package com.dts.vaclass.utils;

import android.app.Activity;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;

/**
 * Created by zs on 2018/2/5.
 * 没有使用全屏模式，无法适配4.4
 */

public class StatusBarViewUtils {

    private static final String TAG = "StatusBarViewUtil";

    private View statusBarView;
    private Activity mActivity;
    private int backgroundRes;

    public static StatusBarViewUtils getInstance(){return new StatusBarViewUtils();}
    public StatusBarViewUtils(){}


    public void init(Activity activity,int res){
       this.mActivity=activity;
       this.backgroundRes=res;
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (isStatusBar()) {
                    initStatusBar();
                    mActivity.getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                            initStatusBar();
                        }
                    });
                }

                //只走一次
                return false;
            }
        });
    }

    private void initStatusBar(){
        if (statusBarView == null) {
            int identifier=mActivity.getResources().getIdentifier("statusBarBackground","id","android");
            statusBarView=mActivity.getWindow().findViewById(identifier);
        }

        if (statusBarView != null) {
            statusBarView.setBackgroundResource(backgroundRes);
        }
    }

    protected boolean isStatusBar(){
        return true;
    }
}
