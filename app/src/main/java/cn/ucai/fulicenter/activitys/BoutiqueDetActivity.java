package cn.ucai.fulicenter.activitys;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;

public class BoutiqueDetActivity extends AppCompatActivity {

    @Bind(R.id.boutique_det_back)
    ImageView boutiqueDetBack;
    @Bind(R.id.boutique_det_title)
    TextView boutiqueDetTitle;
    @Bind(R.id.boutique_det_tvhint)
    TextView boutiqueDetTvhint;
    @Bind(R.id.boutique_det_rcv)
    RecyclerView boutiqueDetRcv;
    @Bind(R.id.boutique_det_srfl)
    SwipeRefreshLayout boutiqueDetSrfl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_det);
        ButterKnife.bind(this);
    }
}
