package cn.ucai.fulicenter.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapters.NewGoodsAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;

public class Category2Activity extends AppCompatActivity {

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

    int goodsid;
    int pageid = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category2);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mcontext=this;
        goodsid = getIntent().getIntExtra(I.Category.KEY_ID,0);
        Log.i("main",goodsid+"------------id");
       // String name = getIntent().getStringExtra(I.Category.KEY_NAME);
      //  ArrayList<CategoryChildBean> list= (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra(I.CategoryChild.CAT_ID);
        initView();
        initData();
    }




    protected void initView() {
        mlist = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(mcontext,mlist);
        m2recv.setAdapter(mAdapter);
        m2recv.setHasFixedSize(true);
        mglm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        m2recv.setLayoutManager(mglm);

    }

    protected void initData() {
        GoodsDao.dowlaodCategory3list(mcontext, goodsid, pageid, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result!=null&& result.length>0){
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
