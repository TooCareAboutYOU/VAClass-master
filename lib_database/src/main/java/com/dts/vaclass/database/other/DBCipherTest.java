package com.dts.vaclass.database.other;

import android.content.Context;

/**
 * Created by zs on 2018/2/2.
 */

public class DBCipherTest {

    private static final String TAG = "DBCipherTest";
    private Context mContext;

    public void init(){
        for (int i = 0; i < 10; i++) {
            DBCipherManager.getInstance(mContext).insertData(String.valueOf(i));
        }
        //更新数据
        DBCipherManager.getInstance(mContext).updateData(String.valueOf(5));
        //清空全部数据
        DBCipherManager.getInstance(mContext).deleteDatas();
        //删除数据
        DBCipherManager.getInstance(mContext).deleteData(String.valueOf(3));
        //查询全部数据
        DBCipherManager.getInstance(mContext).queryDatas();
    }
}
