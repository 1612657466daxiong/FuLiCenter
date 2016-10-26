package cn.ucai.fulicenter.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragments.BoutiqueFragment;
import cn.ucai.fulicenter.fragments.CategoryFragment;
import cn.ucai.fulicenter.fragments.NewGoodsFragment;
import cn.ucai.fulicenter.fragments.PersonalFragment;
import cn.ucai.fulicenter.utils.MFGT;

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


    int currIndex;
    Fragment[] fragments = new Fragment[5];

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragments[0]=new BoutiqueFragment();
        fragments[1]=new NewGoodsFragment();
        fragments[2]=new CategoryFragment();
        fragments[3]=new PersonalFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_stay,fragments[0])
                .add(R.id.fragment_stay,fragments[1])
                .add(R.id.fragment_stay,fragments[2])
                .hide(fragments[0])
                .hide(fragments[2])
                .show(fragments[1])
                .commit();

    }

    @OnClick({R.id.rb_boutique, R.id.rb_new_good, R.id.rb_category, R.id.rb_personal_center, R.id.rb_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_boutique:
                index=0;
                break;
            case R.id.rb_new_good:
                index=1;
                break;
            case R.id.rb_category:
                index=2;
                break;
            case R.id.rb_personal_center:
              //  index=3;
                if (FuLiCenterApplication.getUser()!=null){
                    index=3;
                }else {
                    MFGT.gotoLoginActivity(this);
                }
                break;
            case R.id.rb_cart:
                index=4;
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (index!=currIndex){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (currIndex<5){
                ft.hide(fragments[currIndex]);
            }
            if (!fragments[index].isAdded()){
                ft.add(R.id.fragment_stay,fragments[index]);
            }
            ft.show(fragments[index]).commitAllowingStateLoss();
        }

        currIndex=index;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FuLiCenterApplication.getUser()==null){
            index=1;
            rbNewGood.setChecked(true);
        }
        setFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 111:
                if (resultCode==101){
                    index=3;
                    setFragment();
                }else {
                    setFragment();
                }
                break;

        }
    }
}
