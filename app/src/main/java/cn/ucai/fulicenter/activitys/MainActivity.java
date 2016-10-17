package cn.ucai.fulicenter.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragments.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rb_boutique)
    RadioButton rbBoutique;
    @Bind(R.id.rb_new_good)
    RadioButton rbNewGood;
    @Bind(R.id.rb_category)
    RadioButton rbCategory;
    @Bind(R.id.rb_personal_center)
    RadioButton rbPersonalCenter;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rg_menu)
    RadioGroup rgMenu;
    @Bind(R.id.tv_cart_hint)
    TextView tvCartHint;
    @Bind(R.id.rlayout)
    RelativeLayout rlayout;

    NewGoodsFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rb_boutique, R.id.rb_new_good, R.id.rb_category, R.id.rb_personal_center, R.id.rb_cart, R.id.tv_cart_hint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_boutique:
                break;
            case R.id.rb_new_good:
                if (fragment!=null){
                }else {
                    fragment= new NewGoodsFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_stay, fragment).show(fragment).commit();
                }
                break;
            case R.id.rb_category:
                break;
            case R.id.rb_personal_center:
                break;
            case R.id.rb_cart:
                break;
            case R.id.tv_cart_hint:
                break;
        }
    }
}
