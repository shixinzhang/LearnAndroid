package net.sxkeji.shixinandroiddemo2.activity.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <header>
 * Description:
 * </header>
 * <p>
 * Author: shixinzhang
 */
public class EventBusRegisterActivity extends AppCompatActivity {

    @BindView(R.id.tv_event_info)
    TextView mTvEventInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test);
        ButterKnife.bind(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void readMessage(MessageEvent event){
        mTvEventInfo.setText(mTvEventInfo.getText() + "\n" + event.getMessage() + ", " + event.getTime());
    }

    @OnClick(R.id.btn_register_normal)
    public void registerNormal(){
        EventBus.getDefault().register(this);
    }


    @OnClick(R.id.btn_unregister_normal)
    public void unregisterNormal(){
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_jump_next)
    public void jumpToNext(){
        startActivity(new Intent(this, EventBusPosterActivity.class));
    }


    @OnClick(R.id.btn_jump_sticky)
    public void jumpToSticky(){
        startActivity(new Intent(this, EventBusStickyActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
