package cn.ucai.fulicenter.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activitys.MainActivity;
import cn.ucai.fulicenter.adapters.CartAdapter;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment {


    @Bind(R.id.cart_tv_shuaxin)
    TextView mcartTvShuaxin;
    @Bind(R.id.tv_nothing)
    TextView mtvNothing;
    @Bind(R.id.cart_recv)
    RecyclerView mcartRecv;
    @Bind(R.id.car_swrl)
    SwipeRefreshLayout mcarSwrl;
    @Bind(R.id.car_buy)
    RadioButton mcarBuy;
    @Bind(R.id.should_pay)
    TextView mshouldPay;
    @Bind(R.id.layout_bottom_cart)
    RelativeLayout mlayoutBottomCart;
    @Bind(R.id.cart_savemoney)
    TextView mcartSavemoney;
    LinearLayoutManager mlinearlm;
    CartAdapter mAdapter;

    ArrayList<CartBean> list;
    MainActivity context;
    CartBoast mboast;

    public CarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        mboast=new CartBoast();
        IntentFilter filter =new IntentFilter();
        filter.addAction("updatecount");
        getContext().registerReceiver(mboast,filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car, container, false);
        ButterKnife.bind(this, view);
        context = (MainActivity) getActivity();
        initView();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
    }

    private void initData() {
        GoodsDao.downloadcart(context, FuLiCenterApplication.getUser().getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                if (result != null) {
                    L.e(result.toString());
                    ArrayList<CartBean> carlist = ConvertUtils.array2List(result);
                    mAdapter.initData(carlist);
                    setshownothing(true);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        mlinearlm = new LinearLayoutManager(context);
        mcartRecv.setHasFixedSize(true);
        mcartRecv.setLayoutManager(mlinearlm);
        list = new ArrayList<>();
        mAdapter = new CartAdapter(list, context);
        mcartRecv.setAdapter(mAdapter);
        setshownothing(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setshownothing(boolean isCart) {
        if (isCart) {
            mtvNothing.setVisibility(View.GONE);
            mlayoutBottomCart.setVisibility(View.VISIBLE);
            mcartRecv.setVisibility(View.VISIBLE);
        }else {
            mtvNothing.setVisibility(View.VISIBLE);
            mlayoutBottomCart.setVisibility(View.GONE);
            mcartRecv.setVisibility(View.GONE);
        }
    }

    class CartBoast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            sumprice();
        }
    }

    private void sumprice() {
        int sumPrice = 0;
        int rankPrice = 0;
        if (list.size() > 0) {
            for (CartBean c : list) {
                if (c.isChecked()) {
                    sumPrice += money(c.getGoods().getCurrencyPrice()) * c.getCount();
                    rankPrice += money(c.getGoods().getRankPrice()) * c.getCount();
                }
            }
            mshouldPay.setText("合计：￥ " + Double.valueOf(sumPrice));
            mcartSavemoney.setText("节省：￥ " + Double.valueOf(sumPrice - rankPrice));
        } else {
            setshownothing(false);
        }
    }

    private int money(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        int jaige = Integer.valueOf(price);
        return jaige;
    }
}
