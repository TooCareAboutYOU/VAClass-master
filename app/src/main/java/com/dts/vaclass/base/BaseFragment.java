package com.dts.vaclass.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zs on 2018/2/8.
 */

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    
    private Activity mActivity;
    private View mParentView;
    private boolean isLazyLoad=false;


    protected abstract int setLayoutResourceById();
    protected abstract void initView(View view,Bundle savedInstanceState);
    protected abstract void loadData();
    protected abstract void onInvisible();



    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription(){
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription=new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    public void addCompositeSubscription(Subscription subscription){
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription=new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mParentView=LayoutInflater.from(mActivity).inflate(setLayoutResourceById(),container,false);
        initView(mParentView,savedInstanceState);
        return mParentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mParentView == null) {
            return;
        }

        this.isLazyLoad=isVisibleToUser;
        if (isVisibleToUser) {
            loadData();
        }else {
            onInvisible();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
