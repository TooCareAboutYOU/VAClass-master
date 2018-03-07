package com.dts.vaclass.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.dts.vaclass.R;
import com.dts.vaclass.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void setLayoutBefore() {
        getWindow().getDecorView().setBackgroundResource(R.drawable.ic_launcher_background);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;  // 始终隐藏，触摸屏幕时也不出现
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;   // 隐藏了，但触摸屏幕时出现
        getWindow().setAttributes(params);
    }

    @Override
    protected int setLayoutResourceId() { return R.layout.activity_splash; }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mHandler.postDelayed(mRunnable,1000);
    }

    Handler mHandler=new Handler();
    Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        }
    };

}
