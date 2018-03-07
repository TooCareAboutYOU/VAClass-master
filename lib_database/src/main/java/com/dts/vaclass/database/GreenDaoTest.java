package com.dts.vaclass.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dts.vaclass.database.bean.UserDataBean;
import com.dts.vaclass.database.manager.DBManager;

import java.util.List;

/**
 * 使用示例
 */

public class GreenDaoTest {

    private static final String TAG = "TestDB";
    private Context mContext;

    public void test(Context context) {
        this.mContext=context;

        DBManager dbManager = DBManager.getInstance(context);

        dbManager.deleteUserAll();

        for (int i = 0; i < 5; i++) {
            UserDataBean user = new UserDataBean();
            user.setId((long) i);
            user.setAge(i * 3);
            user.setName("大神" + i);
            dbManager.insertUser(user);
        }
        List<UserDataBean> userDataBeans = dbManager.queryUserList();
        for (UserDataBean userDataBean : userDataBeans) {
            Log.i(TAG, "queryUserList before-->: userId==" + userDataBean.getId() + "\t\tuserAge==" + userDataBean.getAge() + "\t\tuserName==" + userDataBean.getName());
            if (userDataBean.getId() == 0) {
                dbManager.deleteUser(userDataBean);
            }
            if (userDataBean.getId() == 3) {
                userDataBean.setAge(10000);
                dbManager.updateUser(userDataBean);
            }
        }
        userDataBeans = dbManager.queryUserList();
        for (UserDataBean userDataBean : userDataBeans) {
            Log.i(TAG, "queryUserList after-->: userId==" + userDataBean.getId() + "\t\tuserAge==" + userDataBean.getAge() + "\t\tuserName==" + userDataBean.getName());
        }
    }
}
