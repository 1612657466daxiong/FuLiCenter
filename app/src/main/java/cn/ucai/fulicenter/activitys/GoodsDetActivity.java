package cn.ucai.fulicenter.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;

public class GoodsDetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_det);
        int goodsid = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
    }
}
