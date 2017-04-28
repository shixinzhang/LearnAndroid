package net.sxkeji.shixinandroiddemo2.activity;

import android.support.annotation.NonNull;
import android.widget.Toast;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.annotation.Author;
import net.sxkeji.shixinandroiddemo2.annotation.ContentView;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/13.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

@Author(name = "shixinzhang", date = "2016.12.13", habit = {"lol", "code"})
@ContentView(R.layout.activity_annotation)
public class AnnotationTestActivity extends BaseActivity {
    @Override
    public void initViews() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadData() {

    }

    @Override
    public void addListeners() {

    }

    @Deprecated
    public void showToast(@NonNull String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
