package net.sxkeji.shixinandroiddemo2.weex.adapter;

import android.support.annotation.Nullable;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ws.WebSocket;
import com.squareup.okhttp.ws.WebSocketCall;
import com.squareup.okhttp.ws.WebSocketListener;
import com.taobao.weex.appfram.websocket.IWebSocketAdapter;
import com.taobao.weex.appfram.websocket.WebSocketCloseCodes;

import java.io.EOFException;
import java.io.IOException;

import okio.Buffer;
import okio.BufferedSource;


/**
 * <br/> Description: Weex 的 网络请求
 * <p>
 * <br/> Created by shixinzhang on 17/3/28.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class DefaultWebSocketAdapter implements IWebSocketAdapter {
    private WebSocket mWebSocket;
    private EventListener mEventListener;

    @Override
    public void connect(String url, @Nullable String protocol, EventListener listener) {
        this.mEventListener = listener;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();

        if (protocol != null) {
            builder.addHeader(HEADER_SEC_WEBSOCKET_PROTOCOL, protocol);
        }

        builder.url(url);

        WebSocketCall.create(okHttpClient, builder.build()).enqueue(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Request request, Response response) {
                mWebSocket = webSocket;
                mEventListener.onOpen();
            }

            @Override
            public void onFailure(IOException e) {
                e.printStackTrace();
                if (e instanceof EOFException) {
                    mEventListener.onClose(WebSocketCloseCodes.CLOSE_NORMAL.getCode(), WebSocketCloseCodes.CLOSE_NORMAL.name(), true);
                } else {
                    mEventListener.onError(e.getMessage());
                }
            }

            @Override
            public void onMessage(BufferedSource payload, WebSocket.PayloadType type) throws IOException {
                mEventListener.onMessage(payload.readUtf8());
                payload.close();
            }

            @Override
            public void onPong(Buffer payload) {

            }

            @Override
            public void onClose(int code, String reason) {
                mEventListener.onClose(code, reason, true);
            }
        });
    }

    @Override
    public void send(String data) {
        if (mWebSocket == null) {
            reportError("WebSocket is not ready");
            return;
        }
        try {
            Buffer buffer = new Buffer().writeUtf8(data);
            mWebSocket.sendMessage(WebSocket.PayloadType.TEXT, buffer.buffer());
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
            reportError(e.getMessage());
        }
    }

    @Override
    public void close(int code, String reason) {
        if (mWebSocket == null) {
            return;
        }
        try {
            mWebSocket.close(code, reason);
        } catch (Exception e) {
            e.printStackTrace();
            reportError(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        if (mWebSocket == null) {
            return;
        }
        try {
            mWebSocket.close(WebSocketCloseCodes.CLOSE_GOING_AWAY.getCode(), WebSocketCloseCodes.CLOSE_GOING_AWAY.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reportError(String message) {
        if (mEventListener != null) {
            mEventListener.onError(message);
        }
    }
}
