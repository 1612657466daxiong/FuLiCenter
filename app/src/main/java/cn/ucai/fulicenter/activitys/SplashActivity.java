package cn.ucai.fulicenter.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.UserAvater;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    static final long SLEEP_TIME = 2000;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long countTime = System.currentTimeMillis()-start;
                if (SLEEP_TIME-countTime>0){
                    try {
                        Thread.sleep(SLEEP_TIME-countTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                MFGT.finish(SplashActivity.this);

            }
        }).start();*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserDao dao = new UserDao(context);
                UserAvater userAvater = dao.getUser("...");
            }
        },SLEEP_TIME);
    }
}
