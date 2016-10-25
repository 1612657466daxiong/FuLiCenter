package cn.ucai.fulicenter.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activitys.MainActivity;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.UserAvater;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    View view;

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.count_goods)
    TextView countGoods;
    @Bind(R.id.count_shop)
    TextView countShop;
    @Bind(R.id.count_foot)
    TextView countFoot;
    @Bind(R.id.iv_avatartitle)
    ImageView ivAvatartitle;
    @Bind(R.id.tv_set)
    TextView mtvSet;

    MainActivity context;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, layout);
        context = (MainActivity) getContext();
        view = layout;
        initView();
        initData();
        return layout;
    }

    private void initData() {
        UserAvater user = FuLiCenterApplication.getUser();
        ImageLoader.downloadAvatar(context, user.getMuserName(), user.getMavatarSuffix(), ivAvatartitle);
        tvName.setText(user.getMuserName());
        findcountcollect(user.getMuserName());
    }



    private void findcountcollect(String username) {
        GoodsDao.findcollectCount(context, username, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null) {
                    if (result.isSuccess()) {

                        countGoods.setText(result.getMsg());
                    } else {
                        CommonUtils.showShortToast(R.string.findcountcollect);
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void initView() {

    }

    @OnClick({R.id.tv_set})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_set:
                MFGT.gotoPersonInfoActivity(context);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
