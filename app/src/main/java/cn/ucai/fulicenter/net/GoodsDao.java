package cn.ucai.fulicenter.net;

import android.content.Context;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsDao {
   public static OkHttpUtils<NewGoodsBean[]> util;
    public static void downloadNewGoods(Context context,int pageid,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        util=utils;
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID,String.valueOf(pageid))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);

    }

    public static void  downloadGoodsDetails(Context context,int goodsid,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,String.valueOf(goodsid))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    public static void  downloadBoutiqueFirstPage(Context context,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }


}
