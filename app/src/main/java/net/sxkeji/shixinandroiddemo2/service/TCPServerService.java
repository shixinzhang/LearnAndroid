/*
 * Copyright (c) 2017. shixinzhang (shixinzhang2016@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sxkeji.shixinandroiddemo2.service;

import android.text.TextUtils;

import net.sxkeji.shixinandroiddemo2.helper.ConfigHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import top.shixinzhang.sxframework.manager.ThreadPoolManager;
import top.shixinzhang.utils.LogUtils;

/**
 * Description:
 * <br> 使用 Socket 进行 IPC 的例子
 * <p>
 * <br> Created by shixinzhang on 17/6/3.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class TCPServerService extends BaseService {

    private final String TAG = this.getClass().getSimpleName();

    private boolean mIsServiceDisconnected;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "服务已 create");
        new Thread(new TCPServer()).start();
    }


    private class TCPServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(ConfigHelper.TEST_SOCKET_PORT);
                LogUtils.d(TAG, "TCP 服务已创建");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("TCP 服务端创建失败");
                return;
            }

            while (!mIsServiceDisconnected) {
                try {
                    Socket client = serverSocket.accept();  //接受客户端消息，阻塞直到收到消息

//                    new Thread(responseClient(client)).start();
                    ThreadPoolManager.getInstance()
                            .addTask(responseClient(client));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Runnable responseClient(final Socket client) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    //接受消息
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    //回复消息
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                    out.println("服务端已连接 *****");

                    while (!mIsServiceDisconnected) {
                        String inputStr = in.readLine();
                        LogUtils.i(TAG, "收到客户端的消息：" + inputStr);
                        if (TextUtils.isEmpty(inputStr)) {
                            LogUtils.i(TAG, "收到消息为空，客户端断开连接 ***");
                            break;
                        }
                        out.println("你这句【" + inputStr + "】非常有道理啊！");
                    }
                    out.close();
                    in.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        mIsServiceDisconnected = true;
        super.onDestroy();
    }
}
