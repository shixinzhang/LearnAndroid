package net.sxkeji.shixinandroiddemo2.network;

import android.os.AsyncTask;
import android.util.Log;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/14.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class DownloadTask extends AsyncTask<String, Integer, Long> {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * 主线程中执行，首先被调用，可以做一些准备工作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute");
    }

    /**
     * 线程池中执行异步任务
     * @param params
     * @return
     */
    @Override
    protected Long doInBackground(String... params) {
        int length = params.length;
        long downloadSize = 0;
        for (int i = 0; i < length; i++) {
            downloadSize += 1;
            publishProgress(i / length * 100);
            if (isCancelled()){
                break;
            }
        }
        return downloadSize;
    }

    /**
     * 主线程中执行，进度改变时会调用
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, "onProgressUpdate " + values[0]);
    }

    /**
     * 异步任务结束后调用，参数是返回值
     * @param aLong
     */
    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Log.d(TAG, "onPostExecute " + aLong);
    }

}
