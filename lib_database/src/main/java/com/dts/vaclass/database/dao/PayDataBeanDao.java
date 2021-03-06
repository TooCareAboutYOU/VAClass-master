package com.dts.vaclass.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.dts.vaclass.database.DaoSession;
import com.dts.vaclass.database.bean.PayDataBean;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PAY_DATA_BEAN".
*/
public class PayDataBeanDao extends AbstractDao<PayDataBean, Void> {

    public static final String TABLENAME = "PAY_DATA_BEAN";

    /**
     * Properties of entity PayDataBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property PayType = new Property(0, int.class, "payType", false, "PAY_TYPE");
        public final static Property PayName = new Property(1, String.class, "payName", false, "PAY_NAME");
        public final static Property PayMoney = new Property(2, String.class, "payMoney", false, "PAY_MONEY");
    }


    public PayDataBeanDao(DaoConfig config) {
        super(config);
    }
    
    public PayDataBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PAY_DATA_BEAN\" (" + //
                "\"PAY_TYPE\" INTEGER NOT NULL ," + // 0: payType
                "\"PAY_NAME\" TEXT," + // 1: payName
                "\"PAY_MONEY\" TEXT);"); // 2: payMoney
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PAY_DATA_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PayDataBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getPayType());
 
        String payName = entity.getPayName();
        if (payName != null) {
            stmt.bindString(2, payName);
        }
 
        String payMoney = entity.getPayMoney();
        if (payMoney != null) {
            stmt.bindString(3, payMoney);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PayDataBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getPayType());
 
        String payName = entity.getPayName();
        if (payName != null) {
            stmt.bindString(2, payName);
        }
 
        String payMoney = entity.getPayMoney();
        if (payMoney != null) {
            stmt.bindString(3, payMoney);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public PayDataBean readEntity(Cursor cursor, int offset) {
        PayDataBean entity = new PayDataBean( //
            cursor.getInt(offset + 0), // payType
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // payName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // payMoney
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PayDataBean entity, int offset) {
        entity.setPayType(cursor.getInt(offset + 0));
        entity.setPayName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPayMoney(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(PayDataBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(PayDataBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(PayDataBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
