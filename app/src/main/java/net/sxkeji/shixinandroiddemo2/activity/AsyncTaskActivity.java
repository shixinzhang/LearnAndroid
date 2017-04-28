package net.sxkeji.shixinandroiddemo2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.network.DownloadTask;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/14.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class AsyncTaskActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);

        new DownloadTask().execute("www.baidu.com", "www.sougou.com");
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
