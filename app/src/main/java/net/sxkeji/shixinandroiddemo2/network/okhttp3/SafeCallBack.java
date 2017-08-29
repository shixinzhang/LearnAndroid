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

package net.sxkeji.shixinandroiddemo2.network.okhttp3;

import java.io.IOException;

import top.shixinzhang.sxframework.network.third.okhttp3.Call;
import top.shixinzhang.sxframework.network.third.okhttp3.Callback;
import top.shixinzhang.sxframework.network.third.okhttp3.Response;


/**
 * Description:
 * <br> 用于将回调抛到主线程的中间类
 * <p>
 * <br> Created by shixinzhang on 17/5/27.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public abstract class SafeCallBack implements Callback {
    @Override
    public void onFailure(final Call call, final IOException e) {
        postToMainThread(new Runnable() {
            @Override
            public void run() {
                onUIFailure(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        if (response != null && response.isSuccessful()) {
            postToMainThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response resultResponse = response.cacheResponse();     //先去缓存拿，如果设置了 Cache 的话就不为空
                        if (resultResponse == null) {
                            resultResponse = response;
                        }
                        onUISuccess(call, resultResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            postToMainThread(new Runnable() {
                @Override
                public void run() {
                    onUIFailure(call, new IOException("Not Success, response code is " + response.code()));
                }
            });
        }
    }


    protected abstract void onUIFailure(final Call call, final IOException e);

    protected abstract void onUISuccess(final Call call, final Response response) throws IOException;

    private void postToMainThread(Runnable runnable) {
        ResponseExecutor.getInstance().execute(runnable);
    }
}
