package net.sxkeji.shixinandroiddemo2.mvc.model;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <br/> Description: 获取手机信息
 * <p>
 * <br/> Created by shixinzhang on 17/3/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 * http://2402766.blog.51cto.com/2392766/1080837
 * http://www.cnblogs.com/wangxingliu/p/3531167.html
 * http://www.procedurego.com/article/153661.html
 */

public class PhoneInfoModelImpl implements PhoneInfoModel {
    /**
     * 需要添加权限：
     *
     * @param phoneInfoListener
     */
    @Override
    public void getPhoneInfo(OnPhoneInfoListener phoneInfoListener) {

    }

}
