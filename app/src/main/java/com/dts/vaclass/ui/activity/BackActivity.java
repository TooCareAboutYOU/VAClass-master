package com.dts.vaclass.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.vaclass.R;
import com.dts.vaclass.base.BaseActivity;
import com.dts.vaclass.lib_utils.util.AppUtils;

public class BackActivity extends BaseActivity {

    private static final String MSG="msg";

    @Override
    protected void setLayoutBefore() {
//        Slidr.attach(this);   // 设置右滑动返回
    }

    @Override
    protected int setLayoutResourceId() { return R.layout.activity_back; }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView textView=findViewById(R.id.tv_info);
        String info=getIntent().getExtras().getString(MSG);
        textView.setText(info);
    }

    public static Intent newIndexIntent(Context context,String message){
        Intent intent=new Intent(context,BackActivity.class);
        intent.putExtra(MSG,message);
        return intent;
    }

    @Override
    protected void onDestroy() {
        if (getCompositeSubscription().hasSubscriptions()) {
            getCompositeSubscription().unsubscribe();
        }
        super.onDestroy();
    }
}
