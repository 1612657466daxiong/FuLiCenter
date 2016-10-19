package cn.ucai.fulicenter.activitys;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapters.NewGoodsAdapter;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.ImageLoader;

public class BoutiqueDetActivity extends AppCompatActivity {

    @Bind(R.id.boutique_det_back)
    ImageView mDetBack;
    @Bind(R.id.boutique_det_title)
    TextView mDetTitle;
    @Bind(R.id.boutique_det_tvhint)
    TextView mDetTvhint;
    @Bind(R.id.boutique_det_rcv)
    RecyclerView mDetRcv;
    @Bind(R.id.boutique_det_srfl)
    SwipeRefreshLayout mDetSrfl;

    int catid;
    BoutiqueDetActivity mcontext;
    int pageid =1;

    int type = I.ACTION_DOWNLOAD;

    GridLayoutManager mglm;
    NewGoodsAdapter mAapter;
    ArrayList<NewGoodsBean> mlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_det);
        ButterKnife.bind(this);
        mcontext = this;
        String title = getIntent().getStringExtra(I.Boutique.TITLE);
        String[] split = title.split(",");
        catid = Integer.parseInt(split[1]);
        mDetTitle.setText(split[0]);
        initView();
        initData();
        setListener();
        if (catid==0){
            finish();
        }
    }


    protected void initView() {
        mDetSrfl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mlist = new ArrayList<>();
        mAapter = new NewGoodsAdapter(this,mlist);
        mDetRcv.setAdapter(mAapter);
         mglm = new GridLayoutManager(this,I.COLUM_NUM);
        mDetRcv.setLayoutManager(mglm);
        mDetRcv.setHasFixedSize(true);


    }


    protected void initData() {
        GoodsDao.downloadButiqueDetpage(mcontext, catid, pageid, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mAapter.setMore(result!=null && result.length>0);
                if (!mAapter.isMore()){
                    if (type==I.ACTION_PULL_UP){
                        mAapter.setFooterText("没有更多数据");
                    }
                    return;
                }
                ArrayList<NewGoodsBean> newGoodslist = ConvertUtils.array2List(result);
                mAapter.setFooterText("加载更多数据...");
                switch (type) {
                    case I.ACTION_DOWNLOAD:
                        mAapter.initData(newGoodslist);
                        break;
                    case I.ACTION_PULL_DOWN:
                        mAapter.initData(newGoodslist);
                        mDetSrfl.setRefreshing(false);
                        mDetTvhint.setVisibility(View.GONE);
                        ImageLoader.release();
                        break;
                    case I.ACTION_PULL_UP:
                        mAapter.addData(newGoodslist);
                        break;
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }


    protected void setListener() {
        setPullUpListner();
        setPullDownLisener();
    }

    private void setPullDownLisener() {
        mDetSrfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageid=1;
                mDetSrfl.setRefreshing(true);
                mDetSrfl.setEnabled(true);
                mDetTvhint.setVisibility(View.VISIBLE);
                type=I.ACTION_PULL_DOWN;
                initData();
            }
        });
    }

    private void setPullUpListner() {
        mDetRcv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastposition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastposition = mglm.findLastVisibleItemPosition();
                if (lastposition>=mAapter.getItemCount()-1 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mAapter.isMore()){
                    pageid++;
                    type=I.ACTION_PULL_UP;
                    initData();
                }
                if (newState!=RecyclerView.SCROLL_STATE_DRAGGING){
                    mAapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastposition = mglm.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.boutique_det_back)
    public void OnClick(View view){
        finish();
    }
}
