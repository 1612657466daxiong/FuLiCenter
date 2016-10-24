package cn.ucai.fulicenter.dao;

import android.content.Context;

import cn.ucai.fulicenter.bean.UserAvater;

/**
 * Created by Administrator on 2016/10/24.
 */
public class UserDao {
    public static final String USER_TABLE_NAME="t_superwechat_user";
    public static final String USER_COLUMN_NAME="m_user_name";
    public static final String USER_COLUMN_NICK="m_user_nick";
    public static final String USER_COLUMN_AVATER_ID="m_user_avatar_id";
    public static final String USER_COLUMN_AVATER_PATH="m_user_avatar_path";
    public static final String USER_COLUMN_AVATER_SUFFIX="m_user_avatar_suffix";
    public static final String USER_COLUMN_AVATER_TYPE="m_user_avatar_type";
    public static final String USER_COLUMN_AVATER_LASTUPDATE_TIME="m_user_avatar_lastupdate_time";

    public UserDao (Context context){
        DBManager.getInstant().onInit(context);
    }

    public boolean saveUser(UserAvater user){
        return DBManager.getInstant().saveUser(user);
    }
    public UserAvater getUser(String  username){
        return DBManager.getInstant().getUser(username);
    }
    public boolean updateUser(UserAvater user){
        return DBManager.getInstant().updateUser(user);
    }
}
