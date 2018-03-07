package com.dts.vaclass.database.other;

import android.content.Context;
import android.util.Log;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by zs on 2018/2/2.
 */

public class DBCipherHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBCipherHelper";
    private static final String DB_NAME = "Encode_vaclass_db";  //数据库名字
    public static final String DB_PWD = "vaclass";   //数据库密码
    public static final String TABLE_NAME = "USER";  //列名
    private static final String FIELD_ID = "id";         //列名
    public static final String FIELD_NAME = "name";  //列名
    private static final int  DB_VERSION = 1;         //数据库版本号

    public DBCipherHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        //加载so库
        SQLiteDatabase.loadLibs(context);
    }

    public DBCipherHelper(Context context){
        this(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       //创建表
        createTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        String  sql = "CREATE TABLE " + TABLE_NAME + "(" + FIELD_ID + " integer primary key autoincrement , " + FIELD_NAME + " text not null);";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate " + TABLE_NAME + "Error" + e.toString());
            return;
        }
    }

    //数据库升级
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
