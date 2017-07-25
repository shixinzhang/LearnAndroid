package top.shixinzhang.datacrawler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import top.shixinzhang.datacrawler.accessibility.WxAutoClick;

/**
 * Description:
 * <br> 微信加好友监控线程
 * <p>
 * <br> Created by shixinzhang on 17/5/12.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class WxFriendsMonitorThread extends HandlerThread {
    private final String TAG = this.getClass().getSimpleName();
    public final static int MSG_MONITOR = 11;
    private Handler mWorkerHandler;
    private Handler mUIHandler;
    private AtomicBoolean mRunnable = new AtomicBoolean(true);
    private int mLastRequestNumber;

    public WxFriendsMonitorThread(String name) {
        super(name);
    }

    public Handler getWorkerHandler() {
        return mWorkerHandler;
    }

    public Handler getUIHandler() {
        return mUIHandler;
    }

    public void setUIHandler(Handler UIHandler) {
        mUIHandler = UIHandler;
    }

    @Override
    public synchronized void start() {
        mRunnable.set(true);
        super.start();
    }

    @Override
    protected void onLooperPrepared() {
        mWorkerHandler = new Handler(getLooper());
        mWorkerHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Monitor Thread started!");

                while (mRunnable.get()) {
                    try {
                        Thread.sleep(12000);    //12s 检查一次
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (mLastRequestNumber == WxAutoClick.mClickedMemberNameSet.size()) {
                        sendMsgToUIThread(new Message());
                    } else {
                        mLastRequestNumber = WxAutoClick.mClickedMemberNameSet.size();
                        Log.i(TAG, "Running normal, current size is " + mLastRequestNumber);
                    }
                }
            }
        });
    }

    private void sendMsgToUIThread(Message msg) {
        if (mUIHandler != null) {
            mUIHandler.sendMessage(msg);
        }
    }

    @Override
    public boolean quit() {
        mRunnable.set(false);
        mWorkerHandler.removeMessages(MSG_MONITOR);
        setUIHandler(null);
        return super.quit();
    }
}
