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
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.MFGT;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_backpress)
    ImageView loginBackpress;
    @Bind(R.id.login_user)
    EditText mloginUser;
    @Bind(R.id.login_pwd)
    EditText mloginPwd;
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
            mloginUser.setText(extra);
        }
    }

    @OnClick({R.id.login_btnregister,R.id.login_btnlogin})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_btnregister:
                MFGT.gotoRegister(this);
                break;
            case R.id.login_btnlogin:
                login();
        }
    }

    private void login() {
        if ("".equals(mloginUser.getText().toString().trim())){
            mloginUser.setError("用户名不能为空");
            mloginUser.requestFocus();
            return;
        }else if ("".equals(mloginPwd.getText().toString().trim())){
            mloginPwd.setError("密码不能为空");
            mloginPwd.requestFocus();
            return;
        }
        String name= mloginUser.getText().toString().trim();
        String password=mloginPwd.getText().toString().trim();
        GoodsDao.login(this, name, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result!=null){
                    if (result.getRetCode()==0){
                        CommonUtils.showShortToast("登录成功");
                        return;

                    }else if (result.getRetCode()==I.MSG_LOGIN_ERROR_PASSWORD){
                        CommonUtils.showShortToast("密码错误");
                        mloginPwd.requestFocus();
                        return;
                    }else if (result.getRetCode()==I.MSG_LOGIN_UNKNOW_USER){
                        CommonUtils.showShortToast("用户名不存在");
                        mloginUser.requestFocus();
                        return;
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
