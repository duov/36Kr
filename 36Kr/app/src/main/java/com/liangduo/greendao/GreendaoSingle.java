package com.liangduo.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liangduo.kr36.base.BaseApplication;

/**
 * Created by liangduo on 16/5/27.
 * Green的
 */
public class GreendaoSingle {
    private SQLiteDatabase db;//数据库
    private DaoMaster daoMaster;//管理者 (老板)
    private DaoSession daoSession;//会话者 (秘书)
    private CollectionDao collectionDao;//数据库内相应表的操作对象
    private UserDao userDao;

    private Context context;
    private DaoMaster.DevOpenHelper helper;



    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null){
            helper = new DaoMaster.DevOpenHelper(context,"kr36.db",null);
        }
        return helper;
    }

    public SQLiteDatabase getDb() {
        if (db == null){
            db = getHelper().getWritableDatabase();
        }
        return db;
    }

    public DaoMaster getDaoMaster() {
        if (daoMaster == null){
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null){
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    public CollectionDao getCollectionDao() {
        if (collectionDao == null){
            collectionDao = getDaoSession().getCollectionDao();
        }
        return collectionDao;
    }

    public UserDao getUserDao(){
        if (userDao == null ){
            userDao = getDaoSession().getUserDao();
        }
        return userDao;
    }

    private static GreendaoSingle ourInstance = new GreendaoSingle();

    public static GreendaoSingle getInstance() {
        return ourInstance;
    }

    private GreendaoSingle() {
        context= BaseApplication.getContext();
    }
}
