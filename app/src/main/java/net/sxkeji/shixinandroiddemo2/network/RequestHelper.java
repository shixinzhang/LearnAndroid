package net.sxkeji.shixinandroiddemo2.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sxkeji.shixinandroiddemo2.network.SafelyResponseBody;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * <br/> Description: 封装的请求类，目前使用 Retrofit、okHttp
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class RequestHelper implements Interceptor {
    private final String TAG = this.getClass().getSimpleName();
    private static String mBaseUrl = "";

    public static void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public <T> T create(final Class<T> tClass) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(tClass);
    }

    private OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(this).build();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl.Builder urlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        //是否需要加密内容
        RequestBody requestBody = encryptRequest(oldRequest);
        if (requestBody == null){
            requestBody = oldRequest.body();
        }

        Request.Builder requestBuilder = oldRequest.newBuilder()
                .method(oldRequest.method(), requestBody)
                .cacheControl(oldRequest.cacheControl())
                .headers(oldRequest.headers())
                .url(urlBuilder.build());

        addRequestHeader(requestBuilder);

        Request request = requestBuilder.build();
        Log.d(TAG,"request Header+++++++++: " + request.headers().toString());
        Log.d(TAG,"request url+++++++++: " + request.url().toString());

        Response response = chain.proceed(request);
//        SafelyResponseBody safelyResponseBody = new SafelyResponseBody(response.body());
//        String safelyResponse = safelyResponseBody.string();

//        Log.d(TAG,"response+++++++++: " + response.body().string());
//        Log.d(TAG,"response+++++++++: " + safelyResponse);


        return response;
    }

    private void addRequestHeader(Request.Builder requestBuilder) {
        requestBuilder
                .addHeader("Host", mBaseUrl)
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.5")
                .addHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7")
//                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("User-Agent", "E-Mobile/6.5.3 (Linux;U;Android 2.2.1;zh-CN;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1")
                .addHeader("Cookie", "userid=186; userKey=abc4vPejWNAIYblmR7CJv; ClientUDID=868362021581736; ClientToken=; ClientVer=6.5.3; ClientType=android; ClientLanguage=zh; ClientCountry=CN; ClientMobile=; setClientOS=HonorCHM-TL00H; setClientOSVer=4.4.2; Pad=false; JSESSIONID=abc4vPejWNAIYblmR7CJv")
                .addHeader("Cookie2", "$Version=1")
        ;
    }

    /**
     * 加密请求
     * @param request
     * @return
     */
    private RequestBody encryptRequest(Request request) {
        // TODO: 16/12/8 加密
        return request.body();
    }
}
