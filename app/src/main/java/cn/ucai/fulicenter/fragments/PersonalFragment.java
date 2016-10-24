package cn.ucai.fulicenter.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activitys.MainActivity;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.UserAvater;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;

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
    MainActivity context;
    @Bind(R.id.iv_avatartitle)
    ImageView ivAvatartitle;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_personal, container, false);
        context = (MainActivity) getContext();
        view = layout;
        ButterKnife.bind(this, layout);
        initView();
        initData();
        return layout;
    }

    private void initData() {
        UserAvater user = FuLiCenterApplication.getUser();
        ImageLoader.downloadAvatar(context, user.getMuserName(), user.getMavatarSuffix(), ivAvatartitle);
        tvName.setText(user.getMuserName());
        countGoods.setText(findcountcollect(user.getMuserName())+"");
    }
    int countcollcet;
    private int findcountcollect(String username) {
        GoodsDao.findcollectCount(context, username, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result!=null){
                    if (result.isSuccess()){
                        countcollcet=Integer.parseInt(result.getMsg());
                    }else {
                        CommonUtils.showShortToast(R.string.findcountcollect);
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
        return countcollcet;
    }

    private void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
