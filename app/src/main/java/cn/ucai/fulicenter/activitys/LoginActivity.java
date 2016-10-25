package cn.ucai.fulicenter.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvater;
import cn.ucai.fulicenter.dao.SharePreferenceDao;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.ResultUtils;

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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context =this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK && requestCode==223){
            String extra = data.getStringExtra(I.MERCHANT_NAME);
            mloginUser.setText(extra);
        }
    }

    @OnClick({R.id.login_btnregister,R.id.login_btnlogin,R.id.login_backpress})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_btnregister:
                MFGT.gotoRegister(this);
                break;
            case R.id.login_btnlogin:
                login();
            case R.id.login_backpress:
                finish();
                break;
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

        GoodsDao.login(this, name, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String string) {
                Gson gson = new Gson();
                Result result = gson.fromJson(string, Result.class);
                if (result!=null){
                    if (result.isRetMsg()){
                        Log.i("main",  result.getRetData().toString());
                        UserAvater user = gson.fromJson(result.getRetData().toString(), UserAvater.class);
                        UserDao dao = new UserDao(context);
                        boolean b = dao.saveUser(user);
                        if (b){
                            FuLiCenterApplication.getInstance().setUser(user);
                            SharePreferenceDao.getInstance(context).saveUser(user.getMuserName());
                            CommonUtils.showShortToast("登录成功");
                            Log.i("main","数据库存入用户成功");
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            setResult(101,intent);
                            finish();
                        }else {
                            CommonUtils.showShortToast(R.string.user_db_exception);
                        }
                        return;

                    }else if (result.getRetCode()==I.MSG_LOGIN_ERROR_PASSWORD){
                        CommonUtils.showShortToast("密码错误");
                        mloginPwd.requestFocus();
                        return;
                    }else if (result.getRetCode()==I.MSG_LOGIN_UNKNOW_USER){
                        CommonUtils.showShortToast("用户名不存在");
                        mloginUser.requestFocus();
                        return;
                    }else if (result.getRetCode()==I.MSG_UNKNOW || result.getRetCode()==I.MSG_ILLEGAL_REQUEST){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        setResult(102,intent);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
