package net.sxkeji.shixinandroiddemo2.weex.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * <br/> Description: Weex 需要自己实现图片加载
 * <p>
 * <br/> Created by shixinzhang on 17/3/23.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ImageAdapter implements IWXImgLoaderAdapter {
    private final String TAG = "WeexImageLoader";
    private final String YMC_URL_PRE = "http://produce.oss-cn-hangzhou.aliyuncs.com/ops";

    //    private static class SingleHolder {
//        private static final ImageAdapter INSTANCE = new ImageAdapter();
//    }
    //    public static ImageAdapter getInstance() {
//        return SingleHolder.INSTANCE;
//    }
    private Context mContext;

    public ImageAdapter(Context context) {
        mContext = context;
    }


    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        if (view == null) {
            return;
        }
        Log.d(TAG, url);
        if (!url.startsWith("http")) {
            url = YMC_URL_PRE + url;
        }
        Picasso.with(mContext)
                .load(url)
                .into(view);
    }
}
