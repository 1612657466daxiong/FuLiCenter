package cn.ucai.fulicenter.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapters.NewGoodsAdapter;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.GoodsDao;

public class Category2Activity extends BaseActivity {

    @Bind(R.id.category_2back)
    ImageView m2back;
    @Bind(R.id.category_title)
    TextView mTitle;
    @Bind(R.id.category_2price_sort)
    TextView m2priceSort;
    @Bind(R.id.category_2time_sort)
    TextView m2timeSort;
    @Bind(R.id.category_2tvhint)
    TextView m2tvhint;
    @Bind(R.id.category_2recv)
    RecyclerView m2recv;
    @Bind(R.id.category_2srfl)
    SwipeRefreshLayout m2srfl;
    Context mcontext;
    ArrayList<NewGoodsBean> mlist;
    NewGoodsAdapter mAdapter;
    GridLayoutManager mglm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category2);
        ButterKnife.bind(this);
        mcontext=this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mlist = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(mcontext,mlist);
        m2recv.setAdapter(mAdapter);
        m2recv.setHasFixedSize(true);
        mglm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        m2recv.setLayoutManager(mglm);

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {

    }
}
