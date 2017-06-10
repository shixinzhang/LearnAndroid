package net.sxkeji.shixinandroiddemo2.activity.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
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
public class EventBusStickyActivity extends AppCompatActivity {

    @BindView(R.id.tv_event_info)
    TextView mTvEventInfo;
    @BindView(R.id.btn_register_sticky_event)
    Button mBtnRegisterStickyEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_sticky);
        ButterKnife.bind(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void readStickyMsg(MessageEvent event){
        mTvEventInfo.setText(mTvEventInfo.getText() + "\n" + event.getMessage() + ", " + event.getTime());
    }

    @OnClick(R.id.btn_jump_next)
    public void jumpToNext(){
        startActivity(new Intent(this, EventBusPosterActivity.class));
    }

    @OnClick(R.id.btn_register_sticky_event)
    public void registerSticky(){
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.btn_unregister_sticky_event)
    public void unregisterSticky(){
        EventBus.getDefault().removeStickyEvent(MessageEvent.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
