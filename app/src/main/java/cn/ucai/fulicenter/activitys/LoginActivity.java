package cn.ucai.fulicenter.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.MFGT;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_backpress)
    ImageView loginBackpress;
    @Bind(R.id.login_user)
    EditText loginUser;
    @Bind(R.id.login_pwd)
    EditText loginPwd;
    @Bind(R.id.login_btnlogin)
    Button loginBtnlogin;
    @Bind(R.id.login_btnregister)
    Button loginBtnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK && requestCode==223){
            String extra = data.getStringExtra(I.MERCHANT_NAME);
            loginUser.setText(extra);
        }
    }

    @OnClick({R.id.login_btnregister,R.id.login_btnlogin})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_btnregister:
                MFGT.gotoRegister(this);
                break;
        }
    }

}
