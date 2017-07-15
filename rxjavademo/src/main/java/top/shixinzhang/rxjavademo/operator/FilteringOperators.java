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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description:
 * <br> 过滤型操作符
 * http://reactivex.io/documentation/operators.html
 * <p>
 * <br> Created by shixinzhang on 17/7/12.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class FilteringOperators extends BaseOperators{

    private FilteringOperators() {
        //do some init work
    }

    private volatile static FilteringOperators mInstance = new FilteringOperators();

    public static FilteringOperators getInstance() {
        return mInstance;
    }

    public static void test() {
        getInstance().testFilteringOperators();
    }

    private void testFilteringOperators() {
//        filteringWithDebounce();
//        filteringWithThrottleTimeout();

//        filteringWithDistinct();
//        filteringWithDistinct2();
//        filteringWithDistinctUntilChanged();
//        filteringWithDistinctUntilChanged2();

//        filteringWithElementAt();
//        filteringWithElementAtDefault();

//        filteringWithFilter();
//        filteringWithOfType();

//        filteringWithFirst();
//        filteringWithFirstOrDefault();
//        filteringWithSingle();

//        filteringWithTake();

//        filteringWithIgnoreElements();

//        filteringWithSample();
//        filteringWithThrottleFirst();

        filteringWithSkip();
    }

    private void filteringWithSkip() {
        Observable.range(0 , 10)
                .skip(3 , TimeUnit.SECONDS)
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithThrottleFirst() {
        Observable
                .unsafeCreate(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            SystemClock.sleep( i % 5 * 1000);
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithSample() {
        Observable
                .unsafeCreate(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            SystemClock.sleep( i % 5 * 1000);
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .throttleLast(3, TimeUnit.SECONDS)
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithIgnoreElements() {
        Observable.range(0 , 100)
                .ignoreElements()
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithTake() {
        Observable
                .unsafeCreate(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            SystemClock.sleep(1_000);
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .take(3, TimeUnit.SECONDS)
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithSingle() {
        Observable.empty()
                .single()
                .subscribe(getPrintSubscriber());

    }

    private void filteringWithFirstOrDefault() {
        Observable.range(0 , 4)
                .firstOrDefault(33, new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
                    }
                })
                .subscribe(getPrintSubscriber());
    }

    private void filteringWithFirst() {
        Observable.range(4, 10)
                .first(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
                    }
                })
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithOfType() {
        Observable.range(0, 10)
                .ofType(String.class)
                .subscribe(this.<String>getPrintSubscriber());
    }

    private void filteringWithFilter() {
        Observable.range(0, 10)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer item) {
                        return item > 5;
                    }
                })
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithElementAtDefault() {
        Observable.range(0, 10)
                .elementAtOrDefault(12, 222)
                .subscribe(this.<Integer>getPrintSubscriber());

    }

    private void filteringWithElementAt() {
        Observable.range(0, 10)
                .elementAt(12)
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithDistinctUntilChanged2() {

        Observable.from(Arrays.asList(1,1,3,1,3,4,4))
                .distinctUntilChanged(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer item) {
                        return null;
                    }
                })
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithDistinctUntilChanged() {
        Observable.from(Arrays.asList(1,1,3,1,3,4,4))
                .distinctUntilChanged()
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithDistinct2() {

        Observable.from(Arrays.asList(1,3,1,3,4))
                .distinct(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer item) {
                        return item % 2;
                    }
                })
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithDistinct() {
        Observable.from(Arrays.asList(1,3,1,3,4))
                .distinct()
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithThrottleTimeout() {
        Observable
                .unsafeCreate(new Observable.OnSubscribe<Integer>() {
                        @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            SystemClock.sleep( i % 5 * 1000);
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .throttleWithTimeout(2, TimeUnit.SECONDS)
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithDebounce() {
        Observable
                .unsafeCreate(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            SystemClock.sleep(i % 5 * 1000);
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .debounce(2, TimeUnit.SECONDS)
                .subscribe(this.<Integer>getPrintSubscriber());

    }
}
