package cn.ucai.fulicenter;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FuLiCenterApplication extends Application {
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
