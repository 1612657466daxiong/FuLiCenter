package cn.ucai.fulicenter.activitys;

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
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.btnregister)
    public void OnClick(View view){
        if ("".equals(mUser.getText().toString().trim())){
            mUser.setError("用户名不能为空");
            mUser.requestFocus();
            return;
        }
        if (mUser.getText().toString().trim()!=("[A-Za-z0-9]{6,16}")){
            CommonUtils.showShortToast("用户名不规范，用户名是10位的字母和数字组合");
            mUser.requestFocus();
        }
        if ("".equals(mNick.getText().toString().trim())){
            mNick.setError("昵称不能为空");
            mNick.requestFocus();
            return;
        }
        if ("".equals(mPwb.getText().toString().trim())){
            mPwb.setError("密码不能为空");
            mPwb.requestFocus();
            return;
        }
        if ("".equals(mComfitpwd.getText().toString().trim())){
            mComfitpwd.setError("密码不一致");
            mComfitpwd.requestFocus();
            return;
        }
        if (!(mComfitpwd.getText().toString().equals(mPwb.getText().toString()))){
            mComfitpwd.setError("密码不一致");
            mComfitpwd.requestFocus();
            return;
        }
    }
}
