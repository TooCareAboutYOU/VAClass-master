package com.dts.vaclass.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.dts.vaclass.R;
import com.dts.vaclass.ui.fragment.BottomSheetsDialogFragment;
import com.orhanobut.logger.Logger;

public class BottomSheetActivity extends AppCompatActivity {
    private static final String TAG = "BottomSheetActivity";

    private NestedScrollView mNestedScrollView;
    private BottomSheetBehavior mBottomSheetBehavior;

    private BottomSheetDialog mBottomSheetDialog;
    private BottomSheetsDialogFragment fragment;



    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,BottomSheetActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        initBottomsheets();
        initBottomsheetsDialog();
        initBottomSheetsFragment();

    }

    private void initBottomsheets() {
        mNestedScrollView=findViewById(R.id.nsview);
        mBottomSheetBehavior= BottomSheetBehavior.from(mNestedScrollView);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:{
                        Toast.makeText(BottomSheetActivity.this, "拖拽BottomSheet时", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING:{
                        Toast.makeText(BottomSheetActivity.this, "拖拽松开手指时", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED:{
                        Toast.makeText(BottomSheetActivity.this, "完全展开的状态", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED:{
                        Toast.makeText(BottomSheetActivity.this, "BottomSheet关闭时", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case BottomSheetBehavior.STATE_HIDDEN:{
                        Toast.makeText(BottomSheetActivity.this, "完全隐藏时的状态", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case BottomSheetBehavior.PEEK_HEIGHT_AUTO:{
                        Toast.makeText(BottomSheetActivity.this, "自动隐藏", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                Logger.e("bottomSheet："+bottomSheet+"\t\tslideOffset："+slideOffset);
            }
        });

        findViewById(R.id.btn_open).setOnClickListener(v -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        findViewById(R.id.btn_hide).setOnClickListener(v -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
    }

    private void initBottomsheetsDialog() {
        mBottomSheetDialog=new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(R.layout.fragment_blank);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);
        mBottomSheetDialog.setCancelable(true);
        findViewById(R.id.btn_show_dialog).setOnClickListener(v -> {
            mBottomSheetDialog.show();
        });
        findViewById(R.id.btn_hide_dialog).setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
        });
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(BottomSheetActivity.this, "隐藏回调成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBottomSheetsFragment(){
        fragment=BottomSheetsDialogFragment.getInstance();
        android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(fragment,"show fragment");


        findViewById(R.id.btn_show_bsdialog).setOnClickListener(v -> {
            fragment.show(transaction,"哈哈哈");
        });
        findViewById(R.id.btn_hide_bsdialog).setOnClickListener(v -> {
            if (fragment != null){ // && fragment.isVisible()) {
                fragment.dismiss();
                startActivity(new Intent(BottomSheetActivity.this,MainActivity.class));
            }
        });
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN) {
            if (mBottomSheetBehavior.getState()== BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                mBottomSheetDialog=null;
                Toast.makeText(this, "调用了 dialog", Toast.LENGTH_SHORT).show();
            }
            if (fragment != null ){ //&& fragment.isVisible()) {
                fragment.dismiss();
                fragment=null;
                Toast.makeText(this, "调用了 fragment", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
