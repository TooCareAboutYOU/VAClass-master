package com.dts.vaclass.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dts.vaclass.R;
import com.dts.vaclass.base.BaseFragment;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment {

    @Override
    protected int setLayoutResourceById() {return R.layout.fragment_blank;}

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onInvisible() {

    }
}
