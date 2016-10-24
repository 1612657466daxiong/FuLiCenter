package cn.ucai.fulicenter;

import android.app.Application;
import android.content.Context;

import cn.ucai.fulicenter.bean.UserAvater;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application {


    public static UserAvater getUser() {
        return userAvater;
    }

    public static void setUser(UserAvater userAvater) {
        FuLiCenterApplication.userAvater = userAvater;
    }

    private static UserAvater userAvater;
   static FuLiCenterApplication applicationContext;
    public FuLiCenterApplication(){
        applicationContext =this;
    }
    public static   FuLiCenterApplication getInstance() {
         if (applicationContext==null){
             applicationContext = new FuLiCenterApplication();
         }
        return applicationContext;
    }



}
