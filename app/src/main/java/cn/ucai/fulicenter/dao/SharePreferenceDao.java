package cn.ucai.fulicenter.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/24.
 */
public class SharePreferenceDao {
    private static final String SHARE_NEME="saveUserInfo";
    private static SharePreferenceDao instance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor meditor;
    private static final String SHARE_KEY_USER_NAME="share_key_user_name";

    public SharePreferenceDao(Context context){
        mSharedPreferences =context.getSharedPreferences(SHARE_NEME,Context.MODE_APPEND);
        meditor=mSharedPreferences.edit();
    }

    public static SharePreferenceDao getInstance(Context context){
        if (instance==null){
            instance= new SharePreferenceDao(context);
        }
        return instance;
    }

    public void saveUser(String name){
        meditor.putString(SHARE_KEY_USER_NAME,name);
        meditor.commit();
    }
    public void removeUser(){
        meditor.remove(SHARE_KEY_USER_NAME);
        meditor.commit();
    }
    public String getUser(){
        return mSharedPreferences.getString(SHARE_KEY_USER_NAME,null);
    }
}
