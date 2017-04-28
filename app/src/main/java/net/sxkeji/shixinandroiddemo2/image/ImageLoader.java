package net.sxkeji.shixinandroiddemo2.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/15.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ImageLoader {

    public void load(Context context, String url, ImageView targetView){
        Picasso
                .with(context)
                .load(url)
                .centerCrop()
                .config(getConfig())
                .error(getErrorDrawable())
                .memoryPolicy(getMemoryPolicy())
                .networkPolicy(getNetworkPolicy())
                .placeholder(getPlaceHolder())
                .priority(getPriority())
                .into(targetView);
//                .into(getTarget());
    }

    private Target getTarget() {
        return null;
    }

    private Picasso.Priority getPriority() {
        return null;
    }

    private Drawable getPlaceHolder() {
        return null;
    }

    private NetworkPolicy getNetworkPolicy() {
        return null;
    }

    private MemoryPolicy getMemoryPolicy() {

        return null;
    }

    private Drawable getErrorDrawable() {
        return null;
    }

    private Bitmap.Config getConfig() {
        return null;
    }
}
