package net.sxkeji.shixinandroiddemo2.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.annotation.Author;

import top.shixinzhang.BindView;
import top.shixinzhang.ioc.ViewBinder;

/**
 * <br/> Description:
 * 测试自定义注解、编译时注解
 * <p>
 * <br/> Created by shixinzhang on 16/12/13.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

@Author(name = "shixinzhang", date = "2016.12.13", habit = {"lol", "code"})
//@ContentView(R.layout.activity_annotation)
public class AnnotationTestActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    public TextView mTextView;
    @BindView(R.id.tv_bottom_content)
    public TextView mBottomTextView;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        ViewBinder.bind(this);

        mTextView.setText("Haha I'm found by annotation !");
    }

    @Override
    public void initViews() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadData() {

    }

    public void addListeners() {

    }

    @Deprecated
    public void showToast(@NonNull String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
