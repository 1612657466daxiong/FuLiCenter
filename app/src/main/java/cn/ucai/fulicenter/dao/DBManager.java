package cn.ucai.fulicenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.UserAvater;

/**
 * Created by Administrator on 2016/10/24.
 */
public class DBManager  {
    private static DBManager dbmgr=new DBManager();
    private DBhelper dbhelper;

    void onInit(Context context){
        dbhelper=new DBhelper(context);
    }

    public static synchronized DBManager getInstant(){
        return dbmgr;
    }

    public synchronized void closeDB(){
        if (dbhelper!=null){
            dbhelper.closeDB();
        }
    }

    public synchronized boolean saveUser(UserAvater user){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.USER_COLUMN_NAME,user.getMuserName());
        values.put(UserDao.USER_COLUMN_NICK,user.getMuserNick());
        values.put(UserDao.USER_COLUMN_AVATER_ID,user.getMavatarId());
        values.put(UserDao.USER_COLUMN_AVATER_TYPE,user.getMavatarType());
        values.put(UserDao.USER_COLUMN_AVATER_PATH,user.getMavatarPath());
        values.put(UserDao.USER_COLUMN_AVATER_SUFFIX,user.getMavatarSuffix());
        values.put(UserDao.USER_COLUMN_AVATER_LASTUPDATE_TIME,user.getMavatarLastUpdateTime());

        if (db.isOpen()){
            Log.i("main","=======================进入DBManager");
            Log.i("main","boolean"+(db.replace(UserDao.USER_TABLE_NAME,null,values)!=-1));
            return db.replace(UserDao.USER_TABLE_NAME,null,values)!=-1;

        }
        return false;
    }

    public synchronized UserAvater getUser(String  name){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String sql = "select * from "+UserDao.USER_TABLE_NAME +" where "+
                UserDao.USER_COLUMN_NAME +" = ?";
        UserAvater user = null;
        Cursor cursor = db.rawQuery(sql,new String[]{name});
        if (cursor.moveToNext()){
            user = new UserAvater();
            user.setMuserName(name);
            user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATER_ID)));
            user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATER_PATH)));
            user.setMavatarType(cursor.getShort(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATER_TYPE)));
            user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATER_SUFFIX)));
            user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATER_LASTUPDATE_TIME)));
        }
        return user;
    }
    public synchronized boolean updateUser(UserAvater uesr){
        int reult = -1;
        SQLiteDatabase db =dbhelper.getWritableDatabase();
        String sql =UserDao.USER_COLUMN_NAME+"=?";
        ContentValues cv =new ContentValues();
        cv.put(UserDao.USER_COLUMN_NICK,uesr.getMuserNick());
        if (db.isOpen()){
            reult=db.update(UserDao.USER_TABLE_NAME,cv,sql,new String[]{uesr.getMuserName()});
        }
        return reult>0;
    }



}
