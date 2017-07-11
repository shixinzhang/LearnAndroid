package top.shixinzhang.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private Observable mObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createObservables();
//        createObservableWithInterval();
//        createObservableWithJust();
//        createObservableWithRange();
        createObservableWithRepeat();
    }

    private void createObservableWithRepeat() {
        String[] words = {"shixin", "is", "cute"};
        Observable<String> from = Observable.from(words);
        from.repeat(2)
                .repeatWhen(new Func1<Observable<String>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<String> s) {
                        return "shixin".equals(s);
                    }
                })
                .subscribe(getPrintSubscriber());
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


    /**
     * 用于打印结果的订阅者
     * @param <T>
     * @return
     */
    private <T> Subscriber<T> getPrintSubscriber() {
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
