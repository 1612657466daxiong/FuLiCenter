package cn.ucai.fulicenter.activitys;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.MFGT;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.register_backpress)
    ImageView registerBackpress;
    @Bind(R.id.register_user)
    EditText mUser;
    @Bind(R.id.register_nick)
    EditText mNick;
    @Bind(R.id.register_pwb)
    EditText mPwb;
    @Bind(R.id.register_comfitpwd)
    EditText mComfitpwd;
    @Bind(R.id.btnregister)
    Button btnregister;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        context=this;

    }
    @OnClick(R.id.btnregister)
    public void OnClick(View view){
        if ("".equals(mUser.getText().toString().trim())){
            mUser.setError("用户名不能为空");
            mUser.requestFocus();
            return;
        }else if (!(mUser.getText().toString().trim().matches("^[A-Za-z]\\w{5,15}$"))){
            CommonUtils.showShortToast(R.string.illegal_user_name);
            mUser.requestFocus();
            return;
        }else if ("".equals(mNick.getText().toString().trim())){
            mNick.setError("昵称不能为空");
            mNick.requestFocus();
            return;
        }else if ("".equals(mPwb.getText().toString().trim())){
            mPwb.setError("密码不能为空");
            mPwb.requestFocus();
            return;
        }else if ("".equals(mComfitpwd.getText().toString().trim())){
            mComfitpwd.setError("密码不一致");
            mComfitpwd.requestFocus();
            return;
        }else if (!(mComfitpwd.getText().toString().equals(mPwb.getText().toString()))){
            mComfitpwd.setError("密码不一致");
            mComfitpwd.requestFocus();
            return;
        }
        String name = mUser.getText().toString().trim();
        String nick= mNick.getText().toString().trim();
        String password = mPwb.getText().toString().trim();
        register(name,nick,password);
    }

    private void register(final String name, String nick, String password) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("注册中...");
        pd.show();
        GoodsDao.register(context, name, nick, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result.getRetCode()==0){
                    CommonUtils.showShortToast(R.string.register_success);
                    MFGT.gotoLoginActivity(context,name);
                    finish();
                }else {
                    CommonUtils.showShortToast(R.string.register_fail);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
