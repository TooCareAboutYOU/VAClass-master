package com.dts.vaclass.utils.manager;

import android.app.Activity;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2018/2/8.
 */

public class ActivityManager {

    private static final String TAG = "ActivityManager";

    private List<Activity> mActivityList=new ArrayList<Activity>();

    private static ActivityManager instance;
    public ActivityManager(){}
    public static ActivityManager getInstance(){
        if(instance==null){
            instance=new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity){
        if (mActivityList == null) {
            mActivityList=new ArrayList<>();
        }
        if (!mActivityList.contains(activity)){
            mActivityList.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if (activity != null) {
            if (mActivityList.contains(activity)){
                mActivityList.remove(activity);
            }
            activity.finish();
            mActivityList.remove(activity);
        }
    }
    
    public void exitSystem(){
        for (Activity activity : mActivityList) {
            if (activity != null) {
                activity.finish();
                mActivityList.remove(activity);
            }
        }


        if (mActivityList.size() ==0) {
            System.exit(0);
        }
    }

    public void clearExceptMain(){
        for (Activity activity : mActivityList) {
            if (activity.getClass().getSimpleName().equals("MainActivity")){
                continue;
            }else {
                activity.finish();
            }
        }
    }


}
