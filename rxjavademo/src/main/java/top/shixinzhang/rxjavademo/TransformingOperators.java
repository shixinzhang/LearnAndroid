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

package top.shixinzhang.rxjavademo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Description:
 * <br> 变换型操作符
 * <p>
 * <br> Created by shixinzhang on 17/7/11.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class TransformingOperators {


    private TransformingOperators() {
        //do some init work
    }

    private volatile static TransformingOperators mInstance = new TransformingOperators();

    public static TransformingOperators getInstance() {
        return mInstance;
    }

    /**
     * 测试变换型操作符
     */
    public static void test() {
        getInstance().testTransformingOperators();
    }

    private void testTransformingOperators() {

        transformingWithBuffer();
    }

    private void transformingWithBuffer() {
        Observable.range(2, 10)
                .buffer(3)
                .subscribe(getPrintSubscriber());
    }


    private <T> Subscriber<T> getPrintSubscriber() {
        return SubscriberCreator.getPrintSubscriber();
    }
}
