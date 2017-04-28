package net.sxkeji.shixinandroiddemo2.weex;

import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;

import net.sxkeji.shixinandroiddemo2.BaseFragment;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/24.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public abstract class WeexBaseFragment extends BaseFragment implements IWXRenderListener {

    protected WXSDKInstance mWXSDKInstance;

    @Override
    public void onStart() {
        super.onStart();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        onWeexViewCreate(instance, view);
    }

    public abstract void onWeexViewCreate(WXSDKInstance instance, View view);

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }
}
