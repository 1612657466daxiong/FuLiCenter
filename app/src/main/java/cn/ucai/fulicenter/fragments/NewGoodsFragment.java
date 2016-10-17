package cn.ucai.fulicenter.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activitys.MainActivity;
import cn.ucai.fulicenter.adapters.NewGoodsAdapter;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


    @Bind(R.id.tv_srf_hint)
    TextView mtvSrfHint;
    @Bind(R.id.rev_newgoods)
    RecyclerView mrecv;
    @Bind(R.id.srfl)
    SwipeRefreshLayout msrfl;

    MainActivity mcontext;
    NewGoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mlist;
    int pageid =1;

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
       GoodsDao.downloadNewGoods(mcontext,pageid, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
           @Override
           public void onSuccess(NewGoodsBean[] result) {
               ArrayList<NewGoodsBean> newGoodslist = GoodsDao.util.array2List(result);
               mAdapter.initData(newGoodslist);
           }

           @Override
           public void onError(String error) {

           }
       });
    }

    private void initView() {
       mcontext= (MainActivity) getContext();
        msrfl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        GridLayoutManager glm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        mlist = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(mcontext,mlist);
        mrecv.setLayoutManager(glm);
        mrecv.setHasFixedSize(true);
        mrecv.setAdapter(mAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
