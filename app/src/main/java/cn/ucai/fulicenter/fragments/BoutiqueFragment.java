package cn.ucai.fulicenter.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activitys.MainActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {


    @Bind(R.id.boutique_tv_srlhint)
    TextView mTvSrlhint;
    @Bind(R.id.boutique_rev)
    RecyclerView mRev;
    @Bind(R.id.boutique_srfl)
    SwipeRefreshLayout mSrfl;

    GridLayoutManager mgridlayoutmanager;
    MainActivity mcontext;
    ArrayList<BoutiqueBean> mboutiquelist;


    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
    }

    private void initView() {
        mSrfl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mboutiquelist= new ArrayList<>();
        mcontext=(MainActivity)getContext();
        mgridlayoutmanager = new GridLayoutManager(mcontext, I.COLUM_NUM);
        mRev.setLayoutManager(mgridlayoutmanager);
        mRev.setHasFixedSize(true);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
