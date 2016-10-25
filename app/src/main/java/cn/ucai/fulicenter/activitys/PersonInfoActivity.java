package cn.ucai.fulicenter.activitys;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvater;
import cn.ucai.fulicenter.dao.SharePreferenceDao;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.BitmapUtils;
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

    AlertDialog pd;
    String username = FuLiCenterApplication.getUser().getMuserName();

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
        ImageLoader.downloadAvatar(context, user.getMuserName(), user.getMavatarSuffix(), ivPersonalAvatar);
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
                updateAvatar();
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
                finish();
                break;
        }
    }
    static final int AVATAR_TYPE=10001;
    private void updateAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,AVATAR_TYPE);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case AVATAR_TYPE:
                if (resultCode==RESULT_OK){
                    final Uri uri = data.getData();
                    ContentResolver resolver = getContentResolver();
                    String  file = resolver.getType(uri);
                    if (file.startsWith("image")){
                        final Cursor cursor = resolver.query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                        cursor.moveToFirst();
                        final String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        File filein = new File(path);
                        Log.i("main",filein.toString());

                        final Bitmap bitmap = BitmapFactory.decodeFile(path);
                        GoodsDao.updateavatar(context, username, filein, new OkHttpUtils.OnCompleteListener<Result>() {
                            @Override
                            public void onSuccess(Result result) {
                                if(result.isRetMsg()){

                                    ivPersonalAvatar.setImageBitmap(bitmap);
                                    UserDao dao = new UserDao(context);
                                    UserAvater user = FuLiCenterApplication.getUser();
                                    user.setMavatarLastUpdateTime(SystemClock.currentThreadTimeMillis()+"");
                                    user.setMavatarPath(path);
                                    user.setMavatarSuffix(path.substring(path.length()-4));
                                    FuLiCenterApplication.setUser(user);
                                    boolean b = dao.updateUser(user);
                                    if (b){
                                        CommonUtils.showShortToast(R.string.updateavatarsuccess);
                                    }else {
                                        CommonUtils.showShortToast("图片修异常");
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                }
                break;
        }
    }

    private void showdialog() {
        final View layout = View.inflate(context, R.layout.update_personnal_info, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.tb_icon_more_msg_56)
                .setTitle("确认修改")
                .setView(layout)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText text = (EditText) layout.findViewById(R.id.tv_update);
                        if (text.getText()==null){
                            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
                            return;
                        }
                        String nick = text.getText().toString();
                        UserAvater user = FuLiCenterApplication.getUser();
                        user.setMuserNick(nick);
                        UserDao dao = new UserDao(context);
                        dao.updateUser(user);
                        getUpdatenick(nick, user);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        pd = builder.create();
        pd.show();
    }

    private void getUpdatenick(final String nick, UserAvater user) {
        GoodsDao.updatenick(context, user.getMuserName(), nick, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result!=null){
                    if (result.isRetMsg()){
                        tvPersonalinfoNick.setText(nick);
                        CommonUtils.showShortToast(R.string.updatanicks);
                    }else {
                        CommonUtils.showShortToast(R.string.updatanickf);
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }
}
