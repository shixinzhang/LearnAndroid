package net.sxkeji.shixinandroiddemo2.activity.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.shixinzhang.sxframework.utils.DateUtils;

/**
 * <header>
 * Description:
 * </header>
 * <p>
 * Author: shixinzhang
 * </p>
 */
public class EventBusPosterActivity extends AppCompatActivity {

    @BindView(R.id.tv_event_info)
    TextView mTvEventInfo;
    @BindView(R.id.btn_post_normal)
    Button mBtnPostNormal;
    @BindView(R.id.btn_post_sticky_event)
    Button mBtnPostStickyEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_post);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_post_normal)
    public void postNormalEvent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new MessageEvent("另外界面的子线程，普通消息", DateUtils.getCurrentTime()));
                finish();
            }
        }).start();
    }

    @OnClick(R.id.btn_post_sticky_event)
    public void postStickyEvent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().postSticky(new MessageEvent("另外界面的子线程，粘性消息", DateUtils.getCurrentTime()));
                finish();
            }
        }).start();
    }
}
