package net.sxkeji.shixinandroiddemo2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.helper.LocationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class AmapLocationActivity extends BaseActivity {

    @BindView(R.id.btn_location_switch)
    Button mBtnLocationSwitch;
    @BindView(R.id.tv_location_result)
    TextView mTvLocationResult;

    private boolean isStartLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        initViews();
        addListeners();
    }

    @Override
    public void initViews() {
        LocationHelper.getInstance(this).setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation == null){
                    showErrorLog("location failed !");
                    return;
                }
                String locationResult = aMapLocation.getAddress() + ", " + aMapLocation.getLocationDetail() + ", (" + aMapLocation.getLatitude() + "," + aMapLocation.getLongitude() + ")";
                showErrorLog(locationResult);
                mTvLocationResult.setText(locationResult);
            }
        });
    }

    @Override
    public void loadData() {
//        getSupportFragmentManager().beginTransaction().replace()
    }

    @Override
    public void addListeners() {
        mBtnLocationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartLocation) {   //正在定位，点击后停止
                    LocationHelper.getInstance(AmapLocationActivity.this).stopLocation();
                    mBtnLocationSwitch.setText("开始定位");
                } else {
                    LocationHelper.getInstance(AmapLocationActivity.this).requesetLocationOnce();
                    mBtnLocationSwitch.setText("正在定位");
                }
                isStartLocation = !isStartLocation;
            }
        });
    }
}
