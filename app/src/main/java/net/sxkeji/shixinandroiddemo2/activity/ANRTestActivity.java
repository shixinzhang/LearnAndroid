package net.sxkeji.shixinandroiddemo2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <br> Description:ANR 检测
 * <p>
 * <br> Created by shixinzhang on 17/4/26.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ANRTestActivity extends BaseActivity {

    @BindView(R.id.btn_anr)
    Button mBtnAnr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_anr)
    public void triggerANR(){
        try {
            System.out.println(System.currentTimeMillis());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
