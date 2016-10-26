package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.activitys.BoutiqueDetActivity;
import cn.ucai.fulicenter.activitys.Category2Activity;
import cn.ucai.fulicenter.activitys.CollectActivity;
import cn.ucai.fulicenter.activitys.GoodsDetActivity;
import cn.ucai.fulicenter.activitys.LoginActivity;
import cn.ucai.fulicenter.activitys.MainActivity;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activitys.PersonInfoActivity;
import cn.ucai.fulicenter.activitys.RegisterActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;


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
    public static void gotoCategory2Activity(Context context, String name, int id,ArrayList<CategoryChildBean> list){
        Intent intent = new Intent();
        intent.putExtra(I.Category.KEY_NAME,name);
        intent.putExtra(I.Category.KEY_ID,id);
        intent.putExtra("list",list);
        intent.setClass(context, Category2Activity.class);
        startActivity(context,intent);
    }
    public static void gotoLoginActivity(Activity context){
        Intent intent = new Intent(context,LoginActivity.class);
        startActivityforresult(context, intent,111);
    }
    public static void gotoRegister(Activity context){
        Intent intent = new Intent(context,RegisterActivity.class);
        startActivityforresult(context,intent,223);
    }
    public static void startActivityforresult(Activity context,Intent intent,int requestcode){
        context.startActivityForResult(intent,requestcode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoLoginActivity(Activity context,String name){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra(I.MERCHANT_NAME,name);
        context.setResult(Activity.RESULT_OK,intent);
    }

    public static void gotoPersonInfoActivity(Activity activity){
        Intent intent = new Intent(activity, PersonInfoActivity.class);
        startActivity(activity,intent);
    }
    public static void gotoCollectActivity(Activity activity,String username){
        Intent intent = new Intent(activity, CollectActivity.class);
        intent.putExtra(I.Collect.USER_NAME,username);
        startActivity(activity,intent);
    }

}
