package top.shixinzhang.datacrawler;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.shixinzhang.datacrawler.utils.AlertUtil;
import top.shixinzhang.datacrawler.utils.ApplicationUtil;
import top.shixinzhang.datacrawler.utils.DateUtils;
import top.shixinzhang.datacrawler.utils.ServiceUtil;
import top.shixinzhang.datacrawler.utils.ShellUtil;

/**
 * 爬虫程序，目前爬的是 车镇车源 V4.0.1
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private final int REQUEST_CODE_CAR_TOWN = 11; //车镇
    private final int REQUEST_CODE_WX = 12; //weixin

    @BindView(R.id.btn_get_car_town_data)
    Button mBtnGetCarTownData;
    @BindView(R.id.tv_hello)
    TextView mTvHello;

    private boolean mWxAutoClickState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ShellUtil.testRoot(this);

        if (getIntent() != null) {
            boolean resumeFromCrash = getIntent().getBooleanExtra(Config.RESUME_FROM_CRASH, false);
            if (resumeFromCrash) {
                openWx();
            }
        }
//        test();
    }

    private void test() {
        Calendar calendar = DateUtils.getCurrentCalendar();
        mTvHello.setText(calendar.get(Calendar.HOUR_OF_DAY) + "");
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
    }

    @OnClick(R.id.btn_get_car_town_data)
    public void getCarTownData() {
        if (!hasEnv()) {
            goToOpenAccessibility(REQUEST_CODE_CAR_TOWN);
        } else {
            openCarTown();
        }
    }

    @OnClick(R.id.btn_stop_car_town_data)
    public void stop() {
        stopService(new Intent(MainActivity.this, DataCrawlerService.class));
    }

    @OnClick(R.id.btn_wx_auto_make_friends)
    public void wxAutoMakeFriends() {
        if (!mWxAutoClickState) {
            startWxAutoMakeFriends();
        }
    }

    private void startWxAutoMakeFriends() {
        if (!hasEnv()) {
            goToOpenAccessibility(REQUEST_CODE_WX);
        } else {
            startService(new Intent(this, DataCrawlerService.class));
            openWx();
        }

    }

    private void goToOpenAccessibility(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, requestCode);  //在当前 Task 中，才能收到回调
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (!hasEnv()) {
//            AlertUtil.toastShort(this, "拜托开启辅助服务吧");
//            return;
//        }
//        if (requestCode == REQUEST_CODE_CAR_TOWN) {
//            openCarTown();
//        } else if (requestCode == REQUEST_CODE_WX) {
//            openWx();
//        }
//    }

    private void openWx() {
        ApplicationUtil.startApplication(this, Config.WX_PACKAGE_NAME);
    }

    private void openCarTown() {

        Intent intent = new Intent(this, DataCrawlerService.class);
        intent.putExtra(DataCrawlerService.MODE_KEY, DataCrawlerService.MODE_SELECT_BRAND);
        startService(intent);

    }

    private boolean hasEnv() {
        return ServiceUtil.isAccessibilitySettingsOn(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }
}
