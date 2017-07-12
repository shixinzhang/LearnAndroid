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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import top.shixinzhang.rxjavademo.creator.SubscriberCreator;

/**
 * Description:
 * <br> 创建型操作符
 * http://reactivex.io/documentation/operators.html
 * <p>
 * <br> Created by shixinzhang on 17/7/11.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class CreatingOperators extends BaseOperators {


    private CreatingOperators() {
        //do some init work
    }

    private volatile static CreatingOperators mInstance = new CreatingOperators();

    public static CreatingOperators getInstance() {
        return mInstance;
    }

    public static void test() {
        getInstance().testCreatingOperators();
    }

    /**
     * 测试创建型操作符
     */
    public void testCreatingOperators() {

//        createObservables();
//        createObservableWithInterval();
//        createObservableWithJust();
//        createObservableWithRange();
//        createObservableWithRepeat();
//        createObservableWithStart();
        createObservableWithTimer();

//        errorHandlerWithRetry();

    }

    private void createObservableWithTimer() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(this.<Long>getPrintSubscriber());
    }

    private void createObservableWithStart() {
//        Observable.just(1)
//        .startWith();
    }

    /**
     * 错误重试
     * 在遇到需要重复时，抛出一个自定义的异常，在紧接着的链上使用retryWhen上判断是否是这个异常
     * http://www.jianshu.com/p/023a5f60e6d0
     */
    private void errorHandlerWithRetry() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {

            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable instanceof IOException) { //是否是指定的异常
                            return Observable.just(1);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                });
            }
        });
    }

    private int repeatCount;

    private void createObservableWithRepeat() {
        String[] words = {"shixin", "is", "cute"};
        Observable<String> from = Observable.from(words);
//        from.repeat(2)

        Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                for (int i = 0; i < 5; i++) {
                    if (i == 2) {
                        subscriber.onError(new Throwable("error")); //发射 onError 后会停止重复
                    } else {
                        subscriber.onNext("item " + i);
                    }
                }
                subscriber.onCompleted();
            }
        }).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(final Observable<? extends Void> completed) {
                //每次调用 onCompleted，都会进入这里，需要在这里决定是否需要重订阅
                return completed.delay(5, TimeUnit.SECONDS);
            }
        }).subscribe(getPrintSubscriber());
    }

    private void createObservableWithRange() {
        Observable<Integer> range = Observable.range(3, 5);
        range.subscribe(this.<Integer>getPrintSubscriber());
    }

    private void createObservableWithJust() {
        String[] words = {"shixin", "is", "cute"};
        Observable<String[]> just = Observable.just(words);
        just.subscribe(getPrintSubscriber());

        Observable<Object> just1 = Observable.just(null);
        just1.subscribe(getPrintSubscriber());
    }

    private void createObservableWithInterval() {
        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);
        interval.subscribe(getPrintSubscriber());
    }

    private void createObservables() {
        String[] words = {"shixin", "is", "cute"};
        Observable<String> from = Observable.from(words);
        from.subscribe(getPrintSubscriber());
    }
}
