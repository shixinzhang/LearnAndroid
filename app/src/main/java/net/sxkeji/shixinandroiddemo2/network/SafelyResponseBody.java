package net.sxkeji.shixinandroiddemo2.network;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SafelyResponseBody extends ResponseBody {

    private final ResponseBody delegate;
    private IOException thrownException;

    /**
     * SafetyRequestBody
     *
     * @param delegate delegate
     */
    public SafelyResponseBody(ResponseBody delegate) {
        this.delegate = delegate;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() {
        return delegate.contentLength();
    }

    @Override
    public BufferedSource source() {
        BufferedSource delegateSource;
        delegateSource = delegate.source();
        return Okio.buffer(new ForwardingSource(delegateSource) {
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                try {
                    return super.read(sink, byteCount);
                } catch (IOException e) {
                    thrownException = e;
                    throw e;
                }
            }
        });
    }

    @Override
    public void close() {
        delegate.close();
    }

    private void throwIfCaught() throws IOException {
        if (thrownException != null) {
            throw thrownException;
        }
    }
}
