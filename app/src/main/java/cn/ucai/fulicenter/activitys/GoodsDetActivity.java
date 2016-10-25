package cn.ucai.fulicenter.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.views.FlowIndicator;
import cn.ucai.fulicenter.views.SlideAutoLoopView;

public class GoodsDetActivity extends AppCompatActivity {

    @Bind(R.id.id_ivback)
    ImageView idIvback;
    @Bind(R.id.id_ivshare)
    ImageView idIvshare;
    @Bind(R.id.id_ivcollect)
    ImageView idIvcollect;
    @Bind(R.id.tv_det_Englishname)
    TextView mtvEnglishname;
    @Bind(R.id.tv_det_Chinesename)
    TextView mtvchinesename;
    @Bind(R.id.tv_det_sellprice)
    TextView mtvsellprice;
    @Bind(R.id.tv_det_willprice)
    TextView mtvwillprice;
    @Bind(R.id.id_diy_view)
    FlowIndicator mdiyview;
    @Bind(R.id.id_iv_det_car)
    ImageView mcar;
    @Bind(R.id.iv_det_goods)
    SlideAutoLoopView mivpictrues;
    int goodsid;

    GoodsDetailsBean details;
    @Bind(R.id.wv_details)
    WebView mwvDetails;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_det);
        ButterKnife.bind(this);
        goodsid = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        initData();
        context=this;
    }

    private void initData() {
        GoodsDao.downloadGoodsDetails(this, goodsid, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showDetails(result);
                } else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showDetails(GoodsDetailsBean result) {
        this.details = result;
        mtvEnglishname.setText(details.getGoodsEnglishName());
        mtvchinesename.setText(details.getGoodsName());
        mtvwillprice.setText(details.getPromotePrice());
        mtvsellprice.setText(details.getCurrencyPrice());
        mivpictrues.startPlayLoop(mdiyview, getAlbumlmgUrl(details), getPictrueCount(details));
        mwvDetails.loadDataWithBaseURL(null,details.getGoodsBrief(),I.TEXT_HTML,"utf-8",null);
    }

    private int getPictrueCount(GoodsDetailsBean details) {
        if (details.getProperties()!=null && details.getProperties().length>0){
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumlmgUrl(GoodsDetailsBean details) {
        String[] urls = new String[details.getProperties()[0].getAlbums().length];
        for (int i = 0; i < details.getProperties()[0].getAlbums().length; i++) {
            urls[i] =new String();
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls[i] = albums[i].getImgUrl();
        }
        return urls;
    }


    @OnClick({R.id.id_ivback,R.id.id_ivcollect})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_ivback:
                finish();
                break;
            case R.id.id_ivcollect:
                iscollect();
                break;
        }
    }

    private void addgoodsincollect() {
        GoodsDao.addCollect(context, FuLiCenterApplication.getUser().getMuserName(),
                details.getGoodsId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result!=null){
                    if (result.isSuccess()){
                        CommonUtils.showShortToast(R.string.iscollectsuccess);
                    }else {
                        CommonUtils.showShortToast(R.string.iscollectfail);
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MFGT.finish(this);
    }

    public void iscollect() {
        GoodsDao.isCollect(context, FuLiCenterApplication.getUser().getMuserName(),
                details.getGoodsId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {

                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null){
                            if (result.isSuccess()){
                                CommonUtils.showShortToast(R.string.iscollect);
                            }else {
                             addgoodsincollect();
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showShortToast(error);
                    }

                });


    }
}
