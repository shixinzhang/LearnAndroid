package net.sxkeji.shixinandroiddemo2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.api.OaApi;
import net.sxkeji.shixinandroiddemo2.bean.OaCheckInResultBean;
import net.sxkeji.shixinandroiddemo2.bean.OaLoginResultBean;
import net.sxkeji.shixinandroiddemo2.bean.OaStatusBean;
import net.sxkeji.shixinandroiddemo2.bean.OaUserInfoBean;
import net.sxkeji.shixinandroiddemo2.network.RequestHelper;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <br/> Description: 嘿嘿嘿
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class OaLoginActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.tv_detail)
    TextView mTvDetail;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_user_info)
    TextView mTvUserInfo;
    @BindView(R.id.iv_ez)
    ImageView mIvEz;
    @BindView(R.id.ll_cover)
    LinearLayout mLlCover;
    @BindView(R.id.btn_input)
    Button mBtnInput;

    private String mSessionKey;
    private String mMsg;
    private String mLatlng = "31.221517,121.382759";    //经纬度
    //    private String mAddr = "%E4%B8%8A%E6%B5%B7%E5%B8%82%E6%99%AE%E9%99%80%E5%8C%BA%E5%85%89%E5%A4%8D%E8%A5%BF%E8%B7%AF%E9%9D%A0%E8%BF%91%E6%B1%87%E9%93%B6%E9%93%AD%E5%B0%8A6%E5%8F%B7%E6%A5%BC";
    private String[] mAddressArray = {"上海市普陀区光复西路靠近汇银铭尊6号楼", "上海市长宁区泸定路桥靠近泸定路桥",
            "上海市长宁区威宁路靠近上海浦东发展银行(天山路支行)", "上海市长宁区威宁路靠近海益商务大厦"};
    private String mAddr;
    private final String MY_ACCOUNT = "zhangshixin";
    private final String MY_PWD = "Yuntu@123";
//    private final String MY_ACCOUNT = "larryzhang";
//    private final String MY_PWD = "zszh0822";
//    private final String MY_ACCOUNT = "youngwang";    //貌似是错的
//    private final String MY_PWD = "sh5201314";

    private String mBaseUrl = "http://oa.yaomaiche.com:89";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
        loadData();
        addListeners();
    }

    @Override
    public void initViews() {
        RequestHelper.setBaseUrl(mBaseUrl);
    }

    @Override
    public void loadData() {
        Random random = new Random();
        int nextInt = random.nextInt(3);
        if (nextInt < 0 || nextInt >= mAddressArray.length) {
            nextInt = 1;
        }
        mAddr = mAddressArray[nextInt];
        Toast.makeText(this, mAddr, Toast.LENGTH_SHORT).show();
        getUserInfo();
        getStatus();
    }

    @Override
    public void addListeners() {
        mIvEz.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLlCover.setVisibility(View.GONE);
                return true;
            }
        });
    }

    void showLog(String msg) {
        Log.d(TAG, msg + "");
    }

    @OnClick(R.id.btn_check_in)
    public void checkIn() {
        checkInOrOut(true);
    }

    @OnClick(R.id.btn_input)
    public void inputAccountAndPwd(){
//        new AlertDialog.Builder(this)
//                .setTitle("请输入账号密码")
//                .setView()
//                .show();
    }

    private void checkInOrOut(boolean isCheckIn) {
        String type = isCheckIn ? "checkin" : "checkout";
//        if (!isCheckIn) {
//            mAddr = EncodeUtils.urlDecode(mAddr);
//        }
//        if (TextUtils.isEmpty(mAddr)) {
//            mAddr = "上海市普陀区光复西路靠近汇银铭尊6号楼";
//        }

        new RequestHelper().create(OaApi.class)
                .checkIn("checkin", type, mLatlng, mAddr, mSessionKey)
                .enqueue(new Callback<OaCheckInResultBean>() {
                    @Override
                    public void onResponse(Call<OaCheckInResultBean> call, Response<OaCheckInResultBean> response) {
//                        showLog(response.body());
                        if (response.body() != null) {
                            showMsg(response.body().toString());
                        } else {
                            showMsg("签到失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<OaCheckInResultBean> call, Throwable t) {
                        showLog(t.getMessage());
                        showMsg("签到失败: " + t.getMessage());
                    }
                });
    }

    @OnClick(R.id.btn_check_out)
    public void checkOut() {
        checkInOrOut(false);
    }

    @OnClick(R.id.btn_get_status)
    public void getStatus() {
        new RequestHelper().create(OaApi.class)
                .getStatus("checkin", "getStatus", mSessionKey)
                .enqueue(new Callback<OaStatusBean>() {
                    @Override
                    public void onResponse(Call<OaStatusBean> call, Response<OaStatusBean> response) {
                        Log.e(TAG, "onResponse" + response.code() + " \n " + response.body() + " \n" + response.message() + "\n" + response.errorBody());
                        OaStatusBean oaStatusBean = response.body();
                        if (oaStatusBean != null) {
                            showLog(oaStatusBean.toString());
                        } else {
                            showLog("oaStatusBean is null");
                            return;
                        }
                        List<OaStatusBean.SignbtnsBean> signbtnsBeanList = oaStatusBean.getSignbtns();

                        if (signbtnsBeanList == null) {
                            showMsg("查询失败，请检查是否登录");
                            return;
                        }
                        String statusResult = " ";
                        for (int i = 0; i < signbtnsBeanList.size(); i++) {
                            OaStatusBean.SignbtnsBean signbtnsBean = signbtnsBeanList.get(i);
                            OaStatusBean.SignbtnsBean.DetailBean detail = signbtnsBean.getDetail();
                            if (detail != null) {
                                String addr = detail.getAddr();
                                String latitude = detail.getLatitude();
                                String longitude = detail.getLongitude();
                                String signTime = detail.getSignDate() + ", " + detail.getSignTime();
                                String status = detail.getStatus();
                                String isEnable = signbtnsBean.getIsEnable();

                                String currentStatus = i == 0 ? "签到" : "签退";

                                statusResult += "\n查询成功\n" + addr + "\n" + "坐标 (" + latitude + "," + longitude + ")\n" + currentStatus + "时间：" + signTime
                                        + "\n 当前状态：" + status + "\n能否再次：" + isEnable;
                            }
                        }
                        showMsg(statusResult);


                    }

                    @Override
                    public void onFailure(Call<OaStatusBean> call, Throwable t) {
                        Log.d(TAG, "onFailure " + t.getMessage());
                        showMsg("查询状态失败： " + t.getMessage());
                    }
                });
    }

    @OnClick(R.id.btn_login)
    public void login() {
        new RequestHelper().create(OaApi.class)
                .login("login", MY_ACCOUNT, MY_PWD)
                .enqueue(new Callback<OaLoginResultBean>() {
                    @Override
                    public void onResponse(Call<OaLoginResultBean> call, Response<OaLoginResultBean> response) {
                        showLog("onResponse: " + response);
                        OaLoginResultBean loginResultBean = response.body();
                        if (loginResultBean == null) {
                            return;
                        }
                        mSessionKey = loginResultBean.getSessionkey();
                        showLog(mSessionKey);
                        showMsg("登录成功，sessionKey: " + mSessionKey);
                        getUserInfo();
                    }

                    @Override
                    public void onFailure(Call<OaLoginResultBean> call, Throwable t) {
                        showLog("onFailure " + t.getMessage());
                        showMsg("登录失败：" + t.getMessage());
                    }
                });
    }

    public void getUserInfo() {
        new RequestHelper().create(OaApi.class)
                .getUserInfo("getuser", mSessionKey)
                .enqueue(new Callback<OaUserInfoBean>() {
                    @Override
                    public void onResponse(Call<OaUserInfoBean> call, Response<OaUserInfoBean> response) {
                        OaUserInfoBean userInfoBean = response.body();
                        if (userInfoBean == null) {
                            showErrorGetUserInfo();
                            return;
                        }
                        if (userInfoBean.getName() == null) {
                            showErrorGetUserInfo();
                            return;
                        }
                        mTvUserInfo.setText(userInfoBean.getName() + "\n" + userInfoBean.getSubcom() + "\n" + userInfoBean.getJobtitle());

                    }

                    @Override
                    public void onFailure(Call<OaUserInfoBean> call, Throwable t) {
                        showErrorGetUserInfo();
                    }
                });
    }

    private void showErrorGetUserInfo() {
        mTvUserInfo.setText("查询个人信息失败，请检查是否登录");
    }

    private void showMsg(String s) {
        if (!TextUtils.isEmpty(s))
            mTvDetail.setText(s);
    }
}
