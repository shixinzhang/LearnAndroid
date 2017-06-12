package net.sxkeji.shixinandroiddemo2.activity.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.shixinzhang.sxframework.eventsubscribe.third.eventbus.EventBus;
import top.shixinzhang.sxframework.eventsubscribe.third.eventbus.Subscribe;
import top.shixinzhang.sxframework.eventsubscribe.third.eventbus.ThreadMode;

/**
 * <header>
 * Description:
 * </header>
 * <p/>
 * Author: shixinzhang
 */
public class EventBusRegisterActivity extends AppCompatActivity {

    @BindView(R.id.tv_event_info)
    TextView mTvEventInfo;
    @BindView(R.id.tv_event_info_priority)
    TextView mTvEventInfoPriority;
    @BindView(R.id.btn_cancel_delivery)
    Button mBtnCancelDelivery;
    private boolean mCancelDelivery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test);
        ButterKnife.bind(this);
    }

    /**
     * 订阅方法，设置线程为 POSTING，优先级为 5
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 5)
    public void readMessageFirst(MessageEvent event) {
        mTvEventInfoPriority.setText("\n" + event.getMessage() + ", " + event.getTime());
        if (mCancelDelivery) {
            EventBus.getDefault().cancelEventDelivery(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void readMessage(MessageEvent event) {
        mTvEventInfo.setText("\n" + event.getMessage() + ", " + event.getTime());
    }

    /**
     * 为了演示效果，我们在按钮点击事件中注册事件
     */
    @OnClick(R.id.btn_register_normal)
    public void registerNormal() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @OnClick(R.id.btn_unregister_normal)
    public void unregisterNormal() {
        EventBus.getDefault().unregister(this);
    }


    @OnClick(R.id.btn_cancel_delivery)
    public void cancelDelivery() {
        mCancelDelivery = !mCancelDelivery;
        mBtnCancelDelivery.setText(mCancelDelivery ? "已拦截事件传递" : "点击拦截事件传递");
    }

    @OnClick(R.id.btn_jump_next)
    public void jumpToNext() {
        startActivity(new Intent(this, EventBusPosterActivity.class));
    }


    @OnClick(R.id.btn_jump_sticky)
    public void jumpToSticky() {
        startActivity(new Intent(this, EventBusStickyActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
