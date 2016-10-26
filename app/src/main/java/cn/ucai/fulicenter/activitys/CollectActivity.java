package cn.ucai.fulicenter.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapters.CollectAdapter;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;

public class CollectActivity extends AppCompatActivity {

    @Bind(R.id.collect_backpress)
    ImageView mtBackpress;
    @Bind(R.id.collect_reclv)
    RecyclerView mReclv;
    @Bind(R.id.collect_srl)
    SwipeRefreshLayout mSrl;
    Context context;
    GridLayoutManager mglm ;
    CollectAdapter mAdapter;
    ArrayList<CollectBean> list;
    String username;
    int pageid = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        username = getIntent().getStringExtra(I.Collect.USER_NAME);
        context=this;
        initView();
        initData();
    }

    private void initData() {
        GoodsDao.findcollects(context, username, String.valueOf(pageid), new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                if (result!=null){
                    ArrayList<CollectBean> collectlist = ConvertUtils.array2List(result);
                    list.addAll(collectlist);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void initView() {
        mReclv.setHasFixedSize(true);
        mglm=new GridLayoutManager(context, I.COLUM_NUM);
        list=new ArrayList<>();
        mAdapter=new CollectAdapter(list,context);
        mReclv.setAdapter(mAdapter);
        mReclv.setLayoutManager(mglm);
    }
    @OnClick(R.id.collect_backpress)
    public void onclick(View view){
        switch (view.getId()){
            case R.id.collect_backpress:
                finish();
                break;
        }
    }
}
