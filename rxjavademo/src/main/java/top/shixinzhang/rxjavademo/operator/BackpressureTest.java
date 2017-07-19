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

package top.shixinzhang.rxjavademo.operator;

import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import top.shixinzhang.rxjavademo.creator.SubscriberCreator;

/**
 * Description: 背压，控制被观察者发射速率，从 push 改为 pull
 * <br>
 * http://reactivex.io/documentation/operators/backpressure.html
 * https://juejin.im/post/582d413c8ac24700619cceed
 * <p>
 * <br> Created by shixinzhang on 17/7/19.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class BackpressureTest extends BaseOperators {

    private BackpressureTest() {
        //do some init work
    }

    private volatile static BackpressureTest mInstance = new BackpressureTest();

    public static BackpressureTest getInstance() {
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

    public static void test() {
        getInstance().testBackPressure();
    }

    private void testBackPressure() {
//        codeWithoutBackpressure();
        backpressureFirstTry();
    }

    /**
     * 没有使用背压，发射数据速率大于处理速率，会报错： rx.exceptions.MissingBackpressureException
     */
    private void codeWithoutBackpressure() {
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(final Long item) {
                        SystemClock.sleep(1_000);
                        System.out.println("onNext: " + item);
                    }
                });
    }

    private void backpressureFirstTry() {
        //interval 操作符本身并不支持背压策略，它并不响应 request(n)，也就是说，它发送事件的速度是不受控制的
//        Observable.interval(1, TimeUnit.MILLISECONDS)
        Observable.range(1, 100_1000)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onStart() {
                        request(1);
                    }

                    @Override
                    public void onCompleted() {
                        SubscriberCreator.printCompleteMsg();
                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onNext(final Integer item) {
                        SystemClock.sleep(1_000);
                        System.out.println("onNext: " + item);
                        if (item < 15) {
                            request(1);
                        }else {
                            request(Long.MAX_VALUE);    //不管用？
                        }
                    }
                });
    }
}
