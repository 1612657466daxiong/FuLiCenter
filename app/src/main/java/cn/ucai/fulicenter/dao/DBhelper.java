package cn.ucai.fulicenter.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.ucai.fulicenter.I;


/**
 * Created by Administrator on 2016/10/24.
 */
public class DBhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static DBhelper instant;
    private static final String FULICENTER_USER_TABLE_CREATE="CREATE TABLE "+
            UserDao.USER_TABLE_NAME+"(" +
            UserDao.USER_COLUMN_NAME+ " TEXT PRIMARY KEY, " +
            UserDao.USER_COLUMN_NICK+" TEXT, " +
            UserDao.USER_COLUMN_AVATER_ID+" INTEGER, " +
            UserDao.USER_COLUMN_AVATER_TYPE +" INTEGER, " +
            UserDao.USER_COLUMN_AVATER_PATH+" TEXT," +
            UserDao.USER_COLUMN_AVATER_SUFFIX+" TEXT, " +
            UserDao.USER_COLUMN_AVATER_LASTUPDATE_TIME+" TEXT);";

    public DBhelper(Context context) {
        super(context, getCreateName(), null , DATABASE_VERSION);
    }
    public static DBhelper getInstant(Context context){
        if (instant==null){
            instant=new DBhelper(context.getApplicationContext());
        }
        return instant;
    }

    public static String getCreateName(){
        return I.User.TABLE_NAME+"_demo.db";
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FULICENTER_USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public  void closeDB(){
     if (instant!=null){
         SQLiteDatabase db = instant.getWritableDatabase();
         db.close();
         instant=null;
     }
    }
}
