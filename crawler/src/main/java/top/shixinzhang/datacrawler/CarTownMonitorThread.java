package top.shixinzhang.datacrawler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description:
 * <br> 车镇数据爬取监控线程
 * <p>
 * <br> Created by shixinzhang on 17/5/8.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class CarTownMonitorThread extends HandlerThread {
    private final String TAG = this.getClass().getSimpleName();
    public final static int MSG_MONITOR = 11;
    private Handler mWorkerHandler;
    private Handler mUIHandler;
    private AtomicBoolean mRunnable = new AtomicBoolean(true);
    private int mLastPhoneNumber;

    public CarTownMonitorThread(String name) {
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
                        Thread.sleep(16000);    //16s 检查一次电话数量
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (mLastPhoneNumber == DataCrawlerService.mCarPhoneMap.size()) {
                        sendMsgToUIThread(new Message());
                    } else {
                        mLastPhoneNumber = DataCrawlerService.mCarPhoneMap.size();
                        Log.i(TAG, "Running normal, current size is " + mLastPhoneNumber);
                    }
                }
            }
        });
    }

    private void sendMsgToUIThread(Message msg) {
//        Message obtain = Message.obtain(null, MSG_MONITOR);
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
