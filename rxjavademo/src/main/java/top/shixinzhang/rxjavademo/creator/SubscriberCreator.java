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

import rx.Subscriber;

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
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onNext(T t) {
                System.out.println("onNext: " + t);
            }
        };
    }
}
