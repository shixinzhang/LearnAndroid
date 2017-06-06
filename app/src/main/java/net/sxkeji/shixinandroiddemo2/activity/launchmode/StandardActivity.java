package net.sxkeji.shixinandroiddemo2.activity.launchmode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
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
    @BindView(R.id.btn_intent_filter)
    Button mBtnIntentFilter;
    @BindView(R.id.btn_intent_chooser)
    Button mBtnIntentChooser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        ButterKnife.bind(this);

        mTvTitle.setText("This is Standard");
        mBtnIntentFilter.setVisibility(View.VISIBLE);
        mBtnIntentChooser.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.btn_intent_filter)
    public void startActivityWithAction() {
        Uri uri = Uri.parse("smsto:18789999999");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(uri);
        intent.putExtra("sms_body", "Hello");
        startActivity(intent);
    }

    @OnClick(R.id.btn_intent_chooser)
    public void startIntentWithChooser() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        String title = getResources().getString(R.string.chooser_title);
        Intent chooser = Intent.createChooser(sendIntent, title);
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    public void addListeners() {

    }
}
