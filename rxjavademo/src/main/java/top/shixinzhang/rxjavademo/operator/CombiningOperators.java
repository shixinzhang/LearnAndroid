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

import rx.Observable;
import rx.functions.Func2;

/**
 * Description:
 * <br> 组合型操作符
 * http://reactivex.io/documentation/operators.html
 * <p>
 * <br> Created by shixinzhang on 17/7/19.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class CombiningOperators extends BaseOperators {

    private CombiningOperators() {
        //do some init work
    }

    private volatile static CombiningOperators mInstance = new CombiningOperators();

    public static CombiningOperators getInstance() {
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

    public static void test() {
        getInstance().testCombiningOperators();
    }

    private void testCombiningOperators() {
//        zip();
        zipWith();
    }

    /**
     * 非静态，可以和当前 Observable 组合
     */
    private void zipWith() {
        Observable<String> observableA = Observable.just("A", "B", "C", "d", "E");
        Observable
                .just(1, 2, 3, 4)
                .zipWith(observableA, new Func2<Integer, String, String>() {
                    @Override
                    public String call(final Integer integer, final String s) {
                        return integer + ", " + s;
                    }
                })
                .subscribe(this.<String>getPrintSubscriber());
    }

    /**
     * 将多个 Observable 发射的结果严格按顺序组合起来
     */
    private void zip() {
        Observable<String> observableA = Observable.just("A", "B", "C", "d", "E");
        Observable<Integer> observableB = Observable.just(1, 2, 3, 4);

        Observable
                .zip(observableA, observableB, new Func2<String, Integer, String>() {
                    @Override
                    public String call(final String s, final Integer integer) {
                        return s + "_" + integer;
                    }
                })
                .subscribe(this.<String>getPrintSubscriber());
    }
}
