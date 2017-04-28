package net.sxkeji.shixinandroiddemo2.weex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexModalFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexYmcFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/28.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class WeexYmcActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weex_ymc);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new WeexYmcFragment())
                .commit();
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
