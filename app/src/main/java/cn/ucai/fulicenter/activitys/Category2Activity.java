package cn.ucai.fulicenter.activitys;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapters.NewGoodsAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.views.CatChildFilterButton;

public class Category2Activity extends AppCompatActivity {


    Context mcontext;
    ArrayList<NewGoodsBean> mlist;
    NewGoodsAdapter mAdapter;
    GridLayoutManager mglm;
    boolean addtimeAsc = false;
    boolean priceAsc = false;
    int sortby;

    int goodsid;
    int pageid = 1;
    String name;
    ArrayList<CategoryChildBean> list;
    @Bind(R.id.category_2back)
    ImageView m2back;

    @Bind(R.id.category_2pricesort)
    RadioButton m2pricesort;
    @Bind(R.id.category_2addtimesort)
    RadioButton m2addtimesort;
    @Bind(R.id.category_2tvhint)
    TextView m2tvhint;
    @Bind(R.id.category_2recv)
    RecyclerView m2recv;
    @Bind(R.id.category_2srfl)
    SwipeRefreshLayout m2srfl;
    @Bind(R.id.btnCatChildFilter)
    CatChildFilterButton btnCatChildFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category2);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mcontext = this;
        goodsid = getIntent().getIntExtra(I.Category.KEY_ID, 0);
        if (goodsid == 0) {
            finish();
        }
        name = getIntent().getStringExtra(I.Category.KEY_NAME);
        list = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra("list");
        initView();
        initData();
    }

    @OnClick({R.id.category_2pricesort, R.id.category_2addtimesort})
    public void onClick(View view) {
        Drawable right = null;
        switch (view.getId()) {
            case R.id.category_2addtimesort:
                if (addtimeAsc) {
                    sortby = I.SORT_BY_ADDTIME_ASC;
                } else {
                    sortby = I.SORT_BY_ADDTIME_DESC;
                }
                addtimeAsc = !addtimeAsc;
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicWidth());
                m2addtimesort.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                break;
            case R.id.category_2pricesort:
                if (priceAsc) {
                    sortby = I.SORT_BY_PRICE_ASC;
                } else {
                    sortby = I.SORT_BY_PRICE_DESC;
                }
                priceAsc = !priceAsc;
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicWidth());
                m2pricesort.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                break;
        }
        mAdapter.setSortBy(sortby);
    }

    protected void initView() {
        mlist = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(mcontext, mlist);
        m2recv.setAdapter(mAdapter);
        m2recv.setHasFixedSize(true);
        mglm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        m2recv.setLayoutManager(mglm);
        btnCatChildFilter.setTag(name);
    }

    protected void initData() {
        btnCatChildFilter.setOnCatFilterClickListener(name,list);
        GoodsDao.dowlaodCategory3list(mcontext, goodsid, pageid, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> newGoodsBeen = ConvertUtils.array2List(result);
                    mAdapter.setMore(true);
                    mAdapter.initData(newGoodsBeen);
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }


    protected void setListener() {

    }
}
