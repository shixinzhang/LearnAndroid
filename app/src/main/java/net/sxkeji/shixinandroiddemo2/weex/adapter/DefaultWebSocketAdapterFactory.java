package net.sxkeji.shixinandroiddemo2.weex.adapter;

import com.taobao.weex.appfram.websocket.IWebSocketAdapter;
import com.taobao.weex.appfram.websocket.IWebSocketAdapterFactory;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/28.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class DefaultWebSocketAdapterFactory implements IWebSocketAdapterFactory {
    @Override
    public IWebSocketAdapter createWebSocketAdapter() {
        return new DefaultWebSocketAdapter();
    }
}
