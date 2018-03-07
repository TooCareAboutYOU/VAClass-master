package com.dts.vaclass.database.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dts.vaclass.database.DaoMaster;
import com.dts.vaclass.database.DaoSession;
import com.dts.vaclass.database.bean.UserDataBean;
import com.dts.vaclass.database.dao.UserDataBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;
import java.util.List;

/**
 * Created by zs on 2018/2/2.
 */

public class DBManager {

    private static final String dbName = "vaclass_db";

    private static volatile DBManager instance=null;
    private DaoMaster.DevOpenHelper mOpenHelper;
    private Context mContext;

    public DBManager(Context context){
        this.mContext=context;
        mOpenHelper=new DaoMaster.DevOpenHelper(context,dbName,null);

        //SQLCipher加密  256位AWS加密
//        Database db=mOpenHelper.getEncryptedWritableDb("<vaclass>");
//        DaoSession daoSession=new DaoMaster(db).newSession();


        //SQLCipher 加密  https://www.zetetic.net/sqlcipher/sqlcipher-for-android/
//        net.sqlcipher.database.SQLiteDatabase.loadLibs(mContext);
//        File databaseFile=getDatabasePath("vaclass_db");
//        databaseFile.mkdir();
//        databaseFile.delete();
//        net.sqlcipher.database.SQLiteDatabase database= net.sqlcipher.database.SQLiteDatabase.openOrCreateDatabase(databaseFile,"vaclasss",null);
//        database.execSQL("create table t1(a,b)");
//        database.execSQL("insert into t1(a,b) value(?,?)",new Object[]{"one of the money","two for the show"});
    }

    public  static synchronized DBManager getInstance(Context context){
        if(instance==null){
            synchronized(DBManager.class){
                if(instance==null){
                    instance=new DBManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDataBase(){
        if (mOpenHelper == null) {
            mOpenHelper=new DaoMaster.DevOpenHelper(mContext,dbName,null);
        }
        SQLiteDatabase db=mOpenHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getWritableDataBase(){
        if (mOpenHelper == null) {
            mOpenHelper=new DaoMaster.DevOpenHelper(mContext,dbName,null);
        }
        SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        return db;
    }

    /************************************  插入数据 ******************************************/
    //  插入一条数据
    public void insertUser(UserDataBean user){
        DaoMaster daoMaster=new DaoMaster(getWritableDataBase());
        DaoSession daoSession=daoMaster.newSession();
        UserDataBeanDao userDataBeanDao=daoSession.getUserDataBeanDao();
        userDataBeanDao.insert(user);
    }

    //  插入集合
    public void insertUserList(List<UserDataBean> userList){
        if (userList != null || userList.isEmpty()) {
            return;
        }
        DaoMaster daoMaster=new DaoMaster(getWritableDataBase());
        DaoSession daoSession=daoMaster.newSession();
        UserDataBeanDao userDataBeanDao=daoSession.getUserDataBeanDao();
        userDataBeanDao.insertInTx(userList);
    }


    /************************************  删除数据 ******************************************/
    //删除一条数据
    public void deleteUser(UserDataBean user){
        DaoMaster daoMaster=new DaoMaster(getWritableDataBase());
        DaoSession daoSession=daoMaster.newSession();
        UserDataBeanDao userDataBeanDao=daoSession.getUserDataBeanDao();
        userDataBeanDao.delete(user);
    }

    //删除一条数据
    public void deleteUserAll(){
        DaoMaster daoMaster=new DaoMaster(getWritableDataBase());
        DaoSession daoSession=daoMaster.newSession();
        UserDataBeanDao userDataBeanDao=daoSession.getUserDataBeanDao();
        userDataBeanDao.deleteAll();
    }



    /************************************  更新数据 ******************************************/
    //更新一条数据
    public void updateUser(UserDataBean user){
        DaoMaster daoMaster=new DaoMaster(getWritableDataBase());
        DaoSession daoSession=daoMaster.newSession();
        UserDataBeanDao userDataBeanDao=daoSession.getUserDataBeanDao();
        userDataBeanDao.update(user);
    }

    /************************************  查询数据 ******************************************/
    //查询用户列表
    public List<UserDataBean> queryUserList(){
        DaoMaster daoMaster=new DaoMaster(getReadableDataBase());
        DaoSession daoSession=daoMaster.newSession();
        UserDataBeanDao userDataBeanDao=daoSession.getUserDataBeanDao();
        QueryBuilder<UserDataBean> qb=userDataBeanDao.queryBuilder();
        List<UserDataBean> list=qb.list();
        return list;
    }

    //条件查询用户列表
    public List<UserDataBean> queryUserList(int age){
        DaoMaster daoMaster=new DaoMaster(getReadableDataBase());
        DaoSession daoSession=daoMaster.newSession();
        UserDataBeanDao userDataBeanDao=daoSession.getUserDataBeanDao();
        QueryBuilder<UserDataBean> qb=userDataBeanDao.queryBuilder();
        qb.where(UserDataBeanDao.Properties.Age.gt(age)).orderAsc(UserDataBeanDao.Properties.Age);
        List<UserDataBean> list=qb.list();
        return list;
    }

}
