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

package top.shixinzhang.rxjavademo.creator;

import android.os.SystemClock;
import android.util.Log;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Description:
 * <br> 创建测试用的订阅者
 * <p>
 * <br> Created by shixinzhang on 17/7/11.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class SubscriberCreator {
    private static final String TAG = "top.shixinzhang.rxjavademo";


    public static <T> void printNextMsg(final T t) {
        Log.d(TAG, "onNext: " + t);
    }

    public static void printCompleteMsg() {
        Log.d(TAG, "onCompleted");
    }

    public static void printErrorMsg(final Throwable e) {
        Log.d(TAG, "onError: " + e.getMessage());
    }



    /**
     * 用于打印结果的订阅者
     *
     * @param <T>
     * @return
     */
    public static <T> Subscriber<T> getPrintSubscriber() {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                printCompleteMsg();
            }

            @Override
            public void onError(Throwable e) {
                printErrorMsg(e);
            }

            @Override
            public void onNext(T t) {
                printNextMsg(t);
            }
        };
    }

    /**
     * 获得背压订阅者
     *
     * @param <T>
     * @return
     */
    public static <T> Subscriber<T> getBackpressureSubscriber() {
        return new Subscriber<T>() {
            @Override
            public void onStart() {
                //一定要在 onStart 中通知被观察者发送第一个数据
                request(1);
            }

            @Override
            public void onCompleted() {
                printCompleteMsg();
            }

            @Override
            public void onError(final Throwable e) {
                printErrorMsg(e);
            }

            @Override
            public void onNext(final T t) {
                SystemClock.sleep(1_000);
                printNextMsg(t);
                //处理完，请求发送下一个数据
                request(1);

//                request(Long.MAX_VALUE);  调用它取消背压
            }
        };
    }

    public static <T> Action1<T> getSleepAction1(final long sleepTime) {
        return new Action1<T>() {
            @Override
            public void call(final T item) {
                SystemClock.sleep(sleepTime);
                System.out.println("onNext: " + item);
            }
        };
    }
}
