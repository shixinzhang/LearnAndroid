package net.sxkeji.shixinandroiddemo2.api;

import net.sxkeji.shixinandroiddemo2.bean.BookBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/22.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public interface RxTestApi {

    @GET("/books")
    public Observable<BookBean> getBook(@Query("bookId") String bookId);

    @GET("/token")
    public Observable<String> getToken();

    @GET("/books")
    public Observable<BookBean> getBook(@Query("bookId") String bookId, @Query("token") String token);
}
