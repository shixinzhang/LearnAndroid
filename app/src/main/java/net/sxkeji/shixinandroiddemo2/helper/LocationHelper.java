package net.sxkeji.shixinandroiddemo2.helper;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * <br/> Description: 定位
 * <p>
 * <br/> Created by shixinzhang on 16/12/19.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class LocationHelper {
    private static LocationHelper mInstance;
    //定位请求发起者
    private AMapLocationClient mLocationClient;
    //定位回调
    private AMapLocationListener mLocationListener;
    //定位相关配置
    private AMapLocationClientOption mLocationClientOption;

    public static LocationHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LocationHelper(context);
        }
        return mInstance;
    }

    private LocationHelper(Context context) {
        mLocationClient = new AMapLocationClient(context);
        initLocationOption();
    }

    private void initLocationOption() {
        mLocationClientOption = new AMapLocationClientOption();
        mLocationClientOption.setNeedAddress(true); //是否返回地址信息
        mLocationClientOption.setWifiActiveScan(false); //是否强制刷新 WIFI，每次定位主动刷新WIFI模块会提升WIFI定位精度，但相应的会多付出一些电量消耗。
        mLocationClientOption.setMockEnable(false); //是否允许模拟位置，多为模拟 GPS 定位结果
        mLocationClientOption.setHttpTimeOut(20000);    //设置定位请求超时时间，默认为 30 秒
        mLocationClientOption.setLocationCacheEnable(false);    //设置是否开启定位缓存机制
    }

    /**
     * 设置定位模式
     * 高德定位包含两种能力：GPS 定位 和 网络（WIFI,基站）定位
     * 对外开放三种定位模式：
     * Battery_Saving, //低功耗模式，只使用网络定位
     * Device_Sensors, //设备定位模式，只使用 GPS
     * Hight_Accuracy; //高精度模式（网络加 GPS）,默认的模式
     */
    public void setLocationMode(AMapLocationClientOption.AMapLocationMode locationMode) {
        if (mLocationClientOption == null) {
            mLocationClientOption = new AMapLocationClientOption();
        }
        mLocationClientOption.setLocationMode(locationMode);
    }

    /**
     * 只定位一次
     */
    public void requesetLocationOnce() {
        if (mLocationClientOption == null) {
            return;
        }
        mLocationClientOption.setOnceLocation(true);    //获取一次定位结果
//        mLocationClientOption.setOnceLocationLatest(true);  //获取最近 3s 精度最高的一次定位结果

        startLocation();
    }

    public void requestLocationFrequently() {
        requestLocationFrequently(1000);
    }

    /**
     * 连续定位，时间间隔为 intervalTime
     */
    public void requestLocationFrequently(int intervalTime) {
        if (mLocationClientOption == null) {
            return;
        }
        mLocationClientOption.setInterval(intervalTime);
        startLocation();
    }

    private void startLocation() {
        if (mLocationClient == null) {
            return;
        }
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        if (mLocationClient == null) {
            return;
        }
        mLocationClient.stopLocation();
//        mLocationClient.onDestroy();
    }

    public void setLocationListener(AMapLocationListener listener) {
        mLocationListener = listener;
    }
}
