package top.shixinzhang.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rx.Observable;
import top.shixinzhang.rxjavademo.operator.TransformingOperators;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Observable mObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CreatingOperators.test();
        TransformingOperators.test();
    }

}
