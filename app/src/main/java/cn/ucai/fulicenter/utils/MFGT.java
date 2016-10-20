package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.activitys.BoutiqueDetActivity;
import cn.ucai.fulicenter.activitys.Category2Activity;
import cn.ucai.fulicenter.activitys.GoodsDetActivity;
import cn.ucai.fulicenter.activitys.MainActivity;
import cn.ucai.fulicenter.R;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotoGoodsDetailsActivity(Context context, int goodsid){
        Intent intent = new Intent();
        intent.putExtra(I.NewGoods.KEY_GOODS_ID,goodsid);
        intent.setClass(context, GoodsDetActivity.class);
       startActivity(context,intent);
    }
    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotoBoutiqueDetActivity(Context context, String title){
        Intent intent = new Intent();
        intent.putExtra(I.Boutique.TITLE,title);
        intent.setClass(context, BoutiqueDetActivity.class);
        startActivity(context,intent);
    }
    public static void gotoCategory2Activity(Context context, String name_id){
        Intent intent = new Intent();
        intent.putExtra("name_id",name_id);
        intent.setClass(context, Category2Activity.class);
        startActivity(context,intent);
    }

}
