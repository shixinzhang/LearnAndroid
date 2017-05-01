package net.sxkeji.shixinandroiddemo2.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/4/30.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class StandardActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        ButterKnife.bind(this);

        mTvTitle.setText("This is Standard");
    }

    @OnClick(R.id.btn_start_standard)
    public void startStandard() {
        Intent intent = new Intent(this, StandardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_start_singleTop)
    public void startSingleTop() {
        Intent intent = new Intent(this, SingleTopActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_start_singleTask)
    public void startSingleTask() {
        Intent intent = new Intent(this, SingleTaskActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_start_singleInstance)
    public void startSingleInstance() {
        Intent intent = new Intent(this, SingleInstanceActivity.class);
        startActivity(intent);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void addListeners() {

    }
}
