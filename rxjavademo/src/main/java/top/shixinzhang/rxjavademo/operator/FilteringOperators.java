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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.shixinzhang.rxjavademo.creator.DataCreator;

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

public class FilteringOperators extends BaseOperators {

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
//        filteringWithDistinct();

        filteringWithDistinctUntilChanged();
//        filteringWithThrottle();
    }

    private void filteringWithDistinctUntilChanged() {

    }

    private void filteringWithDistinct() {
        Observable.from(Arrays.asList(1,2,3,4,1,2,3))
                .distinct(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(final Integer integer) {
                        return integer % 2;
                    }
                })
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void filteringWithThrottle() {
        Observable.interval(1, TimeUnit.SECONDS);
//                .throttleFirst()
    }

    private void filteringWithDebounce() {
        Observable
                .unsafeCreate(DataCreator.getSleepIntegers())
                .debounce(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .subscribe(this.<Integer>getPrintSubscriber());
    }
}
