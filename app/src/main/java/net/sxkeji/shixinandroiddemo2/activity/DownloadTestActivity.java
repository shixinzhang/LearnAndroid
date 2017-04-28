package net.sxkeji.shixinandroiddemo2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.annotation.AutoRegister;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.shixinzhang.sxframework.update.DownloadApk;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/4/27.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

@AutoRegister(label = "测试下载")
public class DownloadTestActivity extends BaseActivity {
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.btn_download_big_file)
    Button mBtnDownloadBigFile;

    private String TEST_APK_URL = "http://www.wandoujia.com/apps/com.dianping.v1/binding?source=wandoujia-web_direct_binded";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_download)
    public void download() {
        new DownloadApk.Builder()
                .context(this)
                .apkName("shixinzhang.apk")
                .title("shixinzhang app")
                .url(TEST_APK_URL)
                .build()
                .download();
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
