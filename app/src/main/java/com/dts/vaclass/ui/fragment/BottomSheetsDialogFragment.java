package com.dts.vaclass.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dts.vaclass.R;

/**
 * Created by zs on 2018/3/7.
 */

public class BottomSheetsDialogFragment extends BottomSheetDialogFragment {

    public static BottomSheetsDialogFragment getInstance(){
        return new BottomSheetsDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main,container,false);
    }
}
