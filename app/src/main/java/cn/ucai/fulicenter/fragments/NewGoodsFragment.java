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
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    static final int TYPE_PULLUP = 0;
    static final int TYPE_PULLDOWN = 1;
    static final int TYPE_DOWNLOAD = 2;

    @Bind(R.id.tv_srf_hint)
    TextView mtvSrfHint;
    @Bind(R.id.rev_newgoods)
    RecyclerView mrecv;
    @Bind(R.id.srfl)
    SwipeRefreshLayout msrfl;

    GridLayoutManager glm;

    MainActivity mcontext;
    NewGoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mlist;
    int pageid =1;

    int mNewstatus;

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
        initData(TYPE_DOWNLOAD,pageid);
        setlisener();
        return view;
    }

    private void setlisener() {
        setPullUpListner();
        setPullDownLisener();
    }

    private void setPullDownLisener() {
        msrfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageid=1;
                msrfl.setRefreshing(true);
                msrfl.setEnabled(true);
                mtvSrfHint.setVisibility(View.VISIBLE);
                initData(TYPE_PULLDOWN,pageid);

            }
        });
    }

    private void setPullUpListner() {
        mrecv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastposition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mNewstatus = newState;
                lastposition = glm.findLastVisibleItemPosition();
                if (lastposition>=mAdapter.getItemCount()-1 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mAdapter.isMore()){
                    pageid++;
                    initData(TYPE_PULLUP,pageid);
                }
                if (newState!=RecyclerView.SCROLL_STATE_DRAGGING){
                    mAdapter.setYibu(true);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastposition=glm.findLastVisibleItemPosition();

            }
        });
    }

    private void initData(final int type,int pageid) {
       GoodsDao.downloadNewGoods(mcontext,pageid, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
           @Override
           public void onSuccess(NewGoodsBean[] result) {
               mAdapter.setMore(result!=null && result.length>0);
               if (!mAdapter.isMore()){
                   if (type==TYPE_PULLUP){
                       mAdapter.setFooterText("没有更多数据");
                   }
                   return;
               }

               ArrayList<NewGoodsBean> newGoodslist = GoodsDao.util.array2List(result);
               mAdapter.setFooterText("加载更多数据...");
               switch (type){
                   case TYPE_DOWNLOAD:
                       mAdapter.initData(newGoodslist);
                       break;
                   case TYPE_PULLDOWN:
                       mAdapter.initData(newGoodslist);
                       msrfl.setRefreshing(false);
                       mtvSrfHint.setVisibility(View.GONE);
                       ImageLoader.release();
                       break;
                   case TYPE_PULLUP:
                       mAdapter.addData(newGoodslist);
                       break;
               }

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
        glm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        mlist = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(mcontext,mlist);
        mrecv.setHasFixedSize(true);
        mrecv.setLayoutManager(glm);
        mrecv.setAdapter(mAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
