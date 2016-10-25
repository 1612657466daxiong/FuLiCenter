package cn.ucai.fulicenter.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.UserAvater;
import cn.ucai.fulicenter.dao.SharePreferenceDao;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

public class PersonInfoActivity extends AppCompatActivity {

    @Bind(R.id.iv_personinfo_back)
    ImageView ivPersoninfoBack;
    @Bind(R.id.right_to_avatar)
    ImageView rightToAvatar;
    @Bind(R.id.right_to_name)
    ImageView rightToName;
    @Bind(R.id.right_to_nick)
    ImageView rightToNick;
    @Bind(R.id.btn_logout)
    Button btnLogout;

    @Bind(R.id.iv_personal_avatar)
    ImageView ivPersonalAvatar;
    @Bind(R.id.tv_personalinfo_name)
    TextView tvPersonalinfoName;
    @Bind(R.id.tv_personalinfo_nick)
    TextView tvPersonalinfoNick;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        context = this;
        initData();
    }

    private void initData() {
        UserDao dao = new UserDao(context);
        UserAvater user = FuLiCenterApplication.getUser();
        ImageLoader.downloadAvatar(context, user.getMuserName(), user.getMavatarSuffix(),ivPersonalAvatar);
        tvPersonalinfoName.setText(user.getMuserName());
        tvPersonalinfoNick.setText(user.getMuserNick());
    }

    @OnClick({R.id.iv_personinfo_back, R.id.right_to_avatar, R.id.right_to_name, R.id.right_to_nick, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_personinfo_back:
                finish();
                break;
            case R.id.right_to_avatar:
                break;
            case R.id.right_to_name:
                CommonUtils.showShortToast(R.string.uneditable_username);
                break;
            case R.id.right_to_nick:
                showdialog();
                break;
            case R.id.btn_logout:
                SharePreferenceDao.getInstance(context).removeUser();
                FuLiCenterApplication.setUser(null);
                MFGT.gotoMainActivity((PersonInfoActivity)context);
                break;
        }
    }

    private void showdialog() {
    }
}
