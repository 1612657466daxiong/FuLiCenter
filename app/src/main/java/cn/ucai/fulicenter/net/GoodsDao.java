package cn.ucai.fulicenter.net;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.lang.reflect.Array;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.utils.MD5;

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

    public static void downloadButiqueDetpage(Context context,int catid,int pageid,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catid))
                .addParam(I.PAGE_ID,String.valueOf(pageid))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    public static void downloadGroupList(Context context,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    public static void downloadChildList(Context context,int groupid,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+"findCategoryChildren")
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(groupid))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    public static void dowlaodCategory3list(Context context,int catid,int pageid,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catid))
                .addParam(I.PAGE_ID,String.valueOf(pageid))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    public static void register(Context context,String username,String nick,String passwrod,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .addParam(I.User.PASSWORD,passwrod)
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }
    public static void login(Context context,String name,String password,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,name)
                .addParam(I.User.PASSWORD,password)
                .targetClass(String.class)
                .execute(listener);
    }

    public static void addCollect(Context context,String username,int goodsid,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_ADD_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsid))
                .addParam(I.User.USER_NAME_ISCOLLECT,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void isCollect(Context context,String username,int goodsid,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsid))
                .addParam(I.User.USER_NAME_ISCOLLECT,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void findcollectCount(Context context,String username,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void  updatenick(Context context,String username,String nick,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .targetClass(Result.class)
                .execute(listener);
    }

    public static void updateavatar(Context context, String name,  File file, OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,name)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }

    public static void finduserinfobyusername(Context context,String name,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME,name)
                .targetClass(Result.class)
                .execute(listener);
    }

    public static void deletecollectbyid(Context context,String username,String id,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_DELETE_COLLECT)
                .addParam(I.Collect.GOODS_ID,id)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void findcollects(Context context,String username,String pageid,OkHttpUtils.OnCompleteListener listener){
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.PAGE_ID,pageid)
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

}
