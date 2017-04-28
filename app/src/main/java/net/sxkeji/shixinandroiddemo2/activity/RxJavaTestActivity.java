package net.sxkeji.shixinandroiddemo2.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.api.RxTestApi;
import net.sxkeji.shixinandroiddemo2.bean.BookBean;
import net.sxkeji.shixinandroiddemo2.network.RequestHelper;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/21.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 * https://gank.io/post/560e15be2dca930e00da1083
 */

@Keep
public class RxJavaTestActivity extends BaseActivity {
    private Observable mObservable; //被订阅者
    private Observer mObserver; //订阅者

    private TextView mTextView;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        initViews();
        loadData();
    }

    @Override
    public void loadData() {

        threeWaysToCreateObservable();

        twoWaysToCreateObserver();

        //使用 subscribe 方法关联两者
        //Observer 在 subscribe 的过程中最终会被转换成 Subscriber
        mObservable.subscribe(mObserver);

        tryWithAction();

        setImageDrawableWidthId();

        learnScheduler();

        tryWithFunction();
        littleTryLift();

        tryWithCompose();

        littleTryDoOnSubscribe();

        tryRxJavaWithRetrofit();
    }

    private void tryRxJavaWithRetrofit() {
        final String BOOK_ID = "111";

        new RequestHelper().create(RxTestApi.class)
                .getBook(BOOK_ID)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<BookBean>() {
                    @Override
                    public void call(BookBean bookBean) {
                        //做一些长耗时操作，比如存储到本地数据库
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText("查询数据信息失败" + e.getMessage());
                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        mTextView.setText(bookBean.getName());
                    }
                });

        //场景2：需要先获取 token 才能去获取书籍信息 （嵌套网络请求）
        new RequestHelper().create(RxTestApi.class)
                .getToken()
                .flatMap(new Func1<String, Observable<BookBean>>() {
                    @Override
                    public Observable<BookBean> call(String token) {
                        return new RequestHelper().create(RxTestApi.class).getBook(BOOK_ID, token);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        mTextView.setText(bookBean.getName());
                    }
                });
    }

    /**
     * doOnSubscribe 类似 Subscriber.onStart()，也是在事件启动时被调用，但是我们可以控制它在哪个线程执行，这是比 onStart() 好的地方
     */
    private void littleTryDoOnSubscribe() {
        Observable.just(1, 2)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //被后面的 subscribeOn 指定线程
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        //...
                    }
                });
    }


    private class LiftAllTransformer implements Observable.Transformer<Integer, String> {
        @Override
        public Observable<String> call(Observable<Integer> integerObservable) {
            return integerObservable.lift(new Observable.Operator<String, Integer>() {
                @Override
                public Subscriber<? super Integer> call(Subscriber<? super String> subscriber) {
                    return null;
                }
            }).lift(new Observable.Operator<String, String>() {
                @Override
                public Subscriber<? super String> call(Subscriber<? super String> subscriber) {
                    return null;
                }
            });
        }
    }

    /**
     * compose：对 Observable（数据源） 自己做修改
     */
    @SuppressWarnings("unchecked")
    private void tryWithCompose() {
        mObservable.compose(new LiftAllTransformer())
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        //do something
                    }
                });
    }

    /**
     * 简单了解一下 lift
     * <p>
     * 讲述 lift() 的原理只是为了让你更好地了解 RxJava ，从而可以更好地使用它。
     * 然而不管你是否理解了 lift() 的原理，RxJava 都不建议开发者自定义 Operator 来直接使用 lift()，
     * 而是建议尽量使用已有的 lift() 包装方法（如 map() flatMap() 等）进行组合来实现需求，
     * <p>
     * 因为直接使用 lift() 非常容易发生一些难以发现的错误。
     * <p>
     * https://gank.io/post/560e15be2dca930e00da1083
     */
    private void littleTryLift() {
        //转换的关键操作符
        mObservable.lift(new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                //返回一个代理，实际上调用的还是原来的订阅者，但是可以做一些修改，比如将 Integer 的值改成 String 的
                return new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        subscriber.onNext("" + integer);
                    }
                };
            }
        });
    }

    /**
     * 了解转换
     * FuncX 和 ActionX 的区别是：有返回值
     */
    private void tryWithFunction() {
        Observable
                .just(R.drawable.app_progress_horizontal)
                .map(new Func1<Integer, Drawable>() {       //map 方法将参数中的 Integer 转换成了一个 Drawable 然后返回，一对一的转化
                    @Override
                    public Drawable call(Integer integer) {
                        return getResources().getDrawable(integer);
                    }
                })
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        mImageView.setImageDrawable(drawable);
                    }
                });

        final BookBean book = new BookBean("红楼梦", 10);
        BookBean book1 = new BookBean("三国演义", 20);
        BookBean[] books = {book, book1};

        //需求1：打印所有图书的名字
        Observable.from(books)
                .map(new Func1<BookBean, String>() {
                    @Override
                    public String call(BookBean bookBean) {
                        return bookBean.getName();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String bookName) {
                        showInfoLog(bookName);
                    }
                });

        //需求2：打印所有图书的每个章节的名称
        //实现方式1
        Observable.from(books)
                .subscribe(new Subscriber<BookBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BookBean bookBean) {     //这里要是直接拿到的就是 Chapter 就好了
                        List<BookBean.Chapter> chapters = bookBean.getChaptes();
                        for (BookBean.Chapter chapter : chapters) {
                            showInfoLog(chapter.getName());
                        }
                    }
                });
        //实现方式2：flatMap
        Observable.from(books)
                //通过 flatMap 把原始数据里的数据列表取出来撸平，然后返回一个新的被观察数据，以后的操作都是基于它
                .flatMap(new Func1<BookBean, Observable<BookBean.Chapter>>() {
                    @Override
                    public Observable<BookBean.Chapter> call(BookBean bookBean) {
                        return Observable.from(bookBean.getChaptes());
                    }
                })
                .subscribe(new Action1<BookBean.Chapter>() {
                    @Override
                    public void call(BookBean.Chapter chapter) {    //直接拿到了 Chapter 哈哈
                        showInfoLog(chapter.getName());
                    }
                });


    }

    /**
     * 了解调度器
     */
    private void learnScheduler() {
        //直接在当前线程运行，默认这个
        Schedulers.immediate();

        //总是启用新线程，并在新线程执行操作
        Schedulers.newThread();

        //后台耗时操作（读写文件、网络请求）使用的 Scheduler
        //内部用一个无数量上限的线程池，可以重用空闲的线程（有些类似 newCachedThreadPool 吧）
        //多数情况下 io() 比 newThread() 更有效率
        //计算工作不要放到 io() 中，而是长耗时操作
        Schedulers.io();

        //真正用于计算的 Scheduler，这里指的是 CPU 密集计算（不会等待 I/O，全心放在计算上）
        //内部使用固定的线程池，大小为 CPU 核数 （类似 newFixedThreadPool）
        //不要把 I/O 操作放到 computation() 中，否则 I/O 等待时会浪费 CPU
        Schedulers.computation();

        //Android 专用的 Scheduler，在主线程运行
        AndroidSchedulers.mainThread();


        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.newThread())    //指定在子线程进行事件产生操作
                .observeOn(AndroidSchedulers.mainThread())  //观察者回调在主线程进行
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        //这里已经回到主线程
                        mTextView.setText(integer);
                    }
                });
    }

    /**
     * 这里只是一次同步的 RxJava
     */
    private void setImageDrawableWidthId() {
        final int drawableResId = R.drawable.app_progress_primary;

        Observable
                .create(new Observable.OnSubscribe<Drawable>() {
                    @Override
                    public void call(Subscriber<? super Drawable> subscriber) {
                        Drawable drawable = getResources().getDrawable(drawableResId);
                        subscriber.onNext(drawable);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())                   //加了这两句，事件执行和回调就被分别调度到不同线程了
                .observeOn(AndroidSchedulers.mainThread())      //第二句
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorLog(e.getMessage());
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        mImageView.setImageDrawable(drawable);
                    }
                });
    }

    private void tryWithAction() {
        Action0 onCompletedAction = new Action0() {
            /**
             * 无参无返回值，相等于包装了一个 onCompleted()
             */
            @Override
            public void call() {
                showInfoLog("onCompletedAction");
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //error handling
            }
        };

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                showInfoLog("onNextAction: " + s);
            }
        };

        mObservable.subscribe(onNextAction);
        mObservable.subscribe(onNextAction, onErrorAction);
        mObservable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    /**
     * 创建被订阅者的三种方式
     */
    private void threeWaysToCreateObservable() {
        //被观察者，使用 create 方法创造被观察者
        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            /**
             * 当 observable 被订阅时，会自动调用 call() 方法，依次触发其中的事件
             * 其实就是调用订阅者的回调方法，即实现了被观察者向观察者的事件传递
             * @param subscriber
             */
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //这里可能会有复杂的事件发送规则
                subscriber.onNext("next 1");
                subscriber.onNext("next 2");
                subscriber.onCompleted();   //必须得调用 completed
            }
        });

        //just 创建被观察者，将传入的参数依次发送出来
        Observable<String> just = Observable.just("shi", "xin", "zhang", "from", "just");
        //会依次调用 onNext("shi"); onNext("xin"); ... onCompleted();

        //from 创建被观察者，从数组或者 Iterable 中读取具体对象，然后依次发出来
        String[] words = {"z", "s", "x"};
        Observable<String> from = Observable.from(words);
        //会依次调用 onNext("z); onNext("s"); ... onCompleted();
    }

    /**
     * 创建订阅者的两种方式
     */
    private void twoWaysToCreateObserver() {
        mObserver = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        //抽象类，实现了 Observable 接口
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };
    }

    @Override
    public void addListeners() {

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void initViews() {
        mImageView = (ImageView) findViewById(R.id.image);
        mTextView = (TextView) findViewById(R.id.text);
    }

}
