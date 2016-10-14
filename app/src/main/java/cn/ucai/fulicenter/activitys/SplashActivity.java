package cn.ucai.fulicenter.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.R;

public class SplashActivity extends AppCompatActivity {
    static final long SLEEP_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
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
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));

            }
        }).start();
    }
}
