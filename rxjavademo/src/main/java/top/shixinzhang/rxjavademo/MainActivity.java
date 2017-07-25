package top.shixinzhang.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rx.Observable;
import top.shixinzhang.rxjavademo.operator.BackpressureTest;
import top.shixinzhang.rxjavademo.operator.CombiningOperators;
import top.shixinzhang.rxjavademo.operator.FilteringOperators;
import top.shixinzhang.rxjavademo.operator.TransformingOperators;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Observable mObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //操作符
//        CreatingOperators.test();
//        TransformingOperators.test();
//        FilteringOperators.test();
        CombiningOperators.test();

        //背压
//        BackpressureTest.test();
    }

}
