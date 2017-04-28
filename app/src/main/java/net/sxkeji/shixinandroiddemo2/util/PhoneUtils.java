package net.sxkeji.shixinandroiddemo2.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class PhoneUtils {

    /**
     * 获取 SD 卡信息
     * <uses-permission
     * android:name="android.permission.WRITE_EXTERNAL_STORAGE">
     * </uses-permission>
     *
     * @return
     */
    public String getSDCardInfo() {
        //是否有 SD 卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return "未安装SD卡";
        }
        File path = Environment.getExternalStorageDirectory();
        //获取文件系统的整体信息
        StatFs statFs = new StatFs(path.getPath());

        int blockSize = statFs.getBlockSize();
        int totalBlocks = statFs.getBlockCount();
        int availableBlocks = statFs.getAvailableBlocks();

        //计算总空间的大小和空闲空间大小
//        int availableSize = blockSize * availableBlocks;
//        int availableSize = blockSize * availableBlocks / 1024; //KB
        int availableSize = blockSize * availableBlocks / 1024 / 1024;  //MB
        int allSize = blockSize * totalBlocks;

        return String.format("总内存 %d, 可用内存 %d ", allSize, availableSize);
    }

    /**
     * 获取手机 IP
     * <uses-permission
     * // android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * // <uses-permission
     * // android:name="android.permission.INTERNET"></uses-permission>
     *
     * @return
     */
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements(); ) {
                NetworkInterface networkInterface = enumeration.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //不是环回地址
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取 IP 的第二种方法
     * <uses-permission
     * // android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * // <uses-permission
     * // android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
     * // <uses-permission
     * // android:name="android.permission.WAKE_LOCK"></uses-permission>
     *
     * @param context
     * @return
     */
    public String getLocalIpAddress2(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断 wifi 是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return intToIp(ipAddress);
    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
    }


}
