package cn.ucai.fulicenter.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import cn.ucai.fulicenter.adapters.BoutiqueAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.ImageLoader;

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

    LinearLayoutManager mlinerlayout;
    MainActivity mcontext;
    ArrayList<BoutiqueBean> mboutiquelist;
    BoutiqueAdapter mAdapter;


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
        initData(I.ACTION_DOWNLOAD);
        setListener();
        return view;
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
        mRev.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastposition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastposition = mlinerlayout.findLastVisibleItemPosition();
                if (lastposition>=mAdapter.getItemCount()-1 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mAdapter.isMore()){
                    initData(I.ACTION_DOWNLOAD);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastposition= mlinerlayout.findLastVisibleItemPosition();
            }
        });

    }

    private void setPullDownListener() {
        mSrfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrfl.setEnabled(true);
                mSrfl.setRefreshing(true);
                mTvSrlhint.setVisibility(View.VISIBLE);
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initData(final int type) {
        GoodsDao.downloadBoutiqueFirstPage(mcontext, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]> (){
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mAdapter.setMore(result!=null && result.length>0);
                if (!mAdapter.isMore()){
                    if (type==I.ACTION_PULL_UP){
                        mAdapter.setFooterText("没有更多数据");
                    }
                    return;
                }
                ArrayList<BoutiqueBean> boutiquelsit = ConvertUtils.array2List(result);
                mAdapter.setFooterText("加载更多数据");
                switch (type){
                    case I.ACTION_DOWNLOAD:
                        mAdapter.initData(boutiquelsit);
                        break;
                    case I.ACTION_PULL_DOWN:
                        mAdapter.initData(boutiquelsit);
                        mSrfl.setRefreshing(false);
                        mTvSrlhint.setVisibility(View.GONE);
                        ImageLoader.release();
                        break;
                    case I.ACTION_PULL_UP:
                        mAdapter.addData(boutiquelsit);
                        break;
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(error);
            }
        });
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
        mlinerlayout = new LinearLayoutManager(mcontext);
        mRev.setLayoutManager(mlinerlayout);
        mRev.setHasFixedSize(true);

        mAdapter = new BoutiqueAdapter(mcontext,mboutiquelist);
        mRev.setAdapter(mAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
