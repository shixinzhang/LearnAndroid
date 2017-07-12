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

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import top.shixinzhang.rxjavademo.creator.DataCreator;
import top.shixinzhang.rxjavademo.creator.SubscriberCreator;
import top.shixinzhang.rxjavademo.model.Clazz;
import top.shixinzhang.rxjavademo.model.Grade;
import top.shixinzhang.rxjavademo.model.People;

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
    private final String TAG = this.getClass().getSimpleName();

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

//        transformingWithBuffer();
//        transformingWithBufferSkip();
//        transformingWithBufferClosingSelector();
//        transformingWithBufferBoundary();

//        transformingWithFlatMap();
//        transformingWithConcatMap();
//        transformingWithSwitchMap();

//        transformingWithGroupBy();

//        transformingWithMap();
//        transformingWithCast();

        transformingWithScan();
    }

    private void transformingWithScan() {

        Observable.from(Arrays.asList(6, 4, 1, 5, 7))
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(final Integer first, final Integer second) {
                        return first + second;
                    }
                })
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void transformingWithCast() {
        Observable.range(1, 5)
                .cast(String.class)
                .subscribe(this.<String>getPrintSubscriber());
    }

    private void transformingWithMap() {
        Observable.range(1, 5)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(final Integer integer) {
                        return integer * 3;
                    }
                })
                .subscribe(this.<Integer>getPrintSubscriber());
    }

    private void transformingWithGroupBy() {
        Observable.from(DataCreator.getPeopleData())
                .groupBy(new Func1<People, Integer>() {
                    @Override
                    public Integer call(final People people) {
                        return people.getAge();
                    }
                })
                .subscribe(new Action1<GroupedObservable<Integer, People>>() {
                    @Override
                    public void call(final GroupedObservable<Integer, People> integerPeopleGroupedObservable) {
                        integerPeopleGroupedObservable.buffer(2)
                                .subscribe(getPrintSubscriber());
                    }
                });
    }

    private void transformingWithSwitchMap() {
        Observable.just(Observable.timer(4, TimeUnit.SECONDS), Observable.range(2, 10))
                .switchMap(new Func1<Observable<? extends Number>, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Observable<? extends Number> observable) {
                        return observable;
                    }
                })
                .subscribe(this.getPrintSubscriber());
    }

    private void transformingWithConcatMap() {
        Observable.just(DataCreator.getGradeData())
                .concatMap(new Func1<Grade, Observable<Clazz>>() {
                    @Override
                    public Observable<Clazz> call(final Grade grade) {
                        return Observable.from(grade.getClassList());
                    }
                })
                .concatMap(new Func1<Clazz, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Clazz clazz) {
                        return Observable.from(clazz.getStudentNameList());
                    }
                })
                .subscribe(this.getPrintSubscriber());
    }

    private void transformingWithFlatMap() {

        Observable.just(DataCreator.getGradeData())
                .flatMap(new Func1<Grade, Observable<Clazz>>() {
                    @Override
                    public Observable<Clazz> call(final Grade grade) {
                        return Observable.from(grade.getClassList());   //先拿到年级里的班级数据，合并成一个班级 List
                    }
                })
                .flatMap(new Func1<Clazz, Observable<String>>() {
                    @Override
                    public Observable<String> call(final Clazz clazz) {
                        return Observable.from(clazz.getStudentNameList()); //再从每个班级里拿出所有学生名称数据，合并成一个 List
                    }
                })
                .subscribe(this.getPrintSubscriber());
    }

    private void transformingWithBufferBoundary() {
        Observable.interval(1, TimeUnit.SECONDS)
                .buffer(Observable.interval(3, TimeUnit.SECONDS))
                .subscribe(this.<List<Long>>getPrintSubscriber());
    }

    private int emitCount;

    private void transformingWithBufferClosingSelector() {
        Observable.range(2, 10)
//                .repeat()
                .buffer(new Func0<Observable<?>>() {
                    @Override
                    public Observable<?> call() {
                        emitCount++;
                        Log.d(TAG, "emitCount:" + emitCount);
                        return Observable.timer(3, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribe(this.<List<Integer>>getPrintSubscriber());
    }

    private void transformingWithBufferSkip() {
        Observable.range(2, 10)
                .buffer(3, 4)
                .subscribe(this.<List<Integer>>getPrintSubscriber());
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
