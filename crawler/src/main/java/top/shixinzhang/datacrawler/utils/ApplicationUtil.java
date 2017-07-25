package top.shixinzhang.datacrawler.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;


/**
 * <br> Description: 应用管理，启动、关闭...
 * <p>
 * <br> Created by shixinzhang on 17/4/26.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public final class ApplicationUtil {
    private ApplicationUtil() {
    }

    /**
     * 启动某个应用
     *
     * @param context
     * @param packageName
     */
    public static void startApplication(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntentForPackage != null) {
            launchIntentForPackage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(launchIntentForPackage);
        }
    }

    /**
     * 获取当前应用名称
     *
     * @param context
     * @return
     */
    public static String getForegroundAppName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        if (processInfoList == null) {
            return null;
        }

        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
                    || processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return processInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取前台 Activity 名称
     *
     * @param context
     * @return
     */
    public static String getForegroundActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            return cpn.getClassName();
        }
        return null;
    }

    public static void killCurrentProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    /**
     * 获取当前应用信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getCurrentAppInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 apk 信息
     *
     * @param context
     * @param path
     * @return
     */
    public static PackageInfo getApkInfo(Context context, String path) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            return packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        }
        return null;
    }

    /**
     * 比较当前 package 和 指定 URI 路径文件的版本
     *
     * @param context
     * @param uri
     * @return 0 if version is same; lower than 0 if current package's version is lower; greater than 0 is current's version is greater
     */
    public static int compareVersion(Context context, Uri uri) {
        PackageInfo apkInfo = getApkInfo(context, uri.getPath());   //获取指定 uri 对应 apk 的信息
        if (apkInfo == null) {
            return 1;
        }
        PackageInfo currentAppInfo = getCurrentAppInfo(context);
        if (currentAppInfo != null) {
            if (currentAppInfo.packageName.equals(apkInfo.packageName)) {    //包名相同，比较版本
                return currentAppInfo.versionCode - apkInfo.versionCode;
            }
        }
        return 1;
    }

    /**
     * 安装应用
     *
     * @param context
     * @param uri
     */
    public static void installPackage(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
