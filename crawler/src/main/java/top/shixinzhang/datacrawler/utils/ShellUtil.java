package top.shixinzhang.datacrawler.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.OutputStream;

/**
 * Author : erik
 * Email  : erik7@126.com
 * Date   : 17/2/7
 * Version 1.0
 * Description:
 * <p>
 * <p>
 * Copyright (c) 2017 www.yaomaiche.com
 * All Rights Reserved.
 */

public class ShellUtil {

    private static final String TAG = ShellUtil.class.getSimpleName();

    public static void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void testRoot(Context context) {
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Toast.makeText(context, "请允许 ROOT 权限", Toast.LENGTH_LONG).show();
        }
    }
}
