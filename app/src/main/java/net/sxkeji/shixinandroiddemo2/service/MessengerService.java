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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import net.sxkeji.shixinandroiddemo2.helper.ConfigHelper;

import top.shixinzhang.sxframework.utils.LogUtils;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/6/1.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class MessengerService extends BaseService {

    private final String TAG = this.getClass().getSimpleName();

    Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_CLIENT) {
                if (msg.getData() == null) {
                    return;
                }
                String content = (String) msg.getData().get(ConfigHelper.MSG_CONTENT);  //接收客户端的消息
                LogUtils.d(TAG, "Message from client: " + content);

                //回复消息给客户端
                Message replyMsg = Message.obtain();
                replyMsg.arg1 = ConfigHelper.MSG_ID_SERVER;
                Bundle bundle = new Bundle();
                bundle.putString(ConfigHelper.MSG_CONTENT, "听到你的消息了，请说点正经的");
                replyMsg.setData(bundle);

                try {
                    msg.replyTo.send(replyMsg);     //回信
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return mMessenger.getBinder();
    }
}
