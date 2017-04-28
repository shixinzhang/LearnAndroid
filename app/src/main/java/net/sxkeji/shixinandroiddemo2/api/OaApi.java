package net.sxkeji.shixinandroiddemo2.api;

import net.sxkeji.shixinandroiddemo2.bean.OaCheckInResultBean;
import net.sxkeji.shixinandroiddemo2.bean.OaLoginResultBean;
import net.sxkeji.shixinandroiddemo2.bean.OaStatusBean;
import net.sxkeji.shixinandroiddemo2.bean.OaUserInfoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public interface OaApi {
    /**
     * get
     */
    @GET("/client.do")
    Call<OaLoginResultBean> login(@Query("method") String method,   //login
                                  @Query("loginid") String id,
                                  @Query("password") String password);


    /**
     * GET
     * URL	http://oa.yaomaiche.com:89/client.do?method=checkin&type=getStatus&sessionkey=abcMPBbb3LSr0hPofKCJv
     */
    @GET("/client.do")
    Call<OaStatusBean> getStatus(@Query("method") String method,
                                 @Query("type") String type,
                                 @Query("sessionkey") String sessionKey);

    /**
     * 签到
     * GET
     * URL http://oa.yaomaiche.com:89/client.do?method=checkin&type=checkin&latlng=31.221517,121.382759&addr=%E4%B8%8A%E6%B5%B7%E5%B8%82%E6%99%AE%E9%99%80%E5%8C%BA%E5%85%89%E5%A4%8D%E8%A5%BF%E8%B7%AF%E9%9D%A0%E8%BF%91%E6%B1%87%E9%93%B6%E9%93%AD%E5%B0%8A6%E5%8F%B7%E6%A5%BC&sessionkey=abct6SvVcif9MhuV8CCJv
     */
    @GET("/client.do")
    Call<OaCheckInResultBean> checkIn(@Query("method") String method,//checkin
                                      @Query("type") String type,    //checkin
                                      @Query("latlng") String latlng,
                                      @Query("addr") String addr,
                                      @Query("sessionkey") String sessionKey);

    /**
     * GET
     * URL	http://oa.yaomaiche.com:89/client.do?method=getuser&userid=&sesson&sessionkey=abcSrZLZuYblb4zuqJCJv
     */
    @GET("client.do")
    Call<OaUserInfoBean> getUserInfo(@Query("method") String method,    //getuser
                                     @Query("sessionkey") String sessionKey);

    /**
     * 签退
     * GET
     * URL	http://oa.yaomaiche.com:89/client.do?method=checkin&type=checkout&latlng=31.221535,121.382851&addr=%E4%B8%8A%E6%B5%B7%E5%B8%82%E6%99%AE%E9%99%80%E5%8C%BA%E5%85%89%E5%A4%8D%E8%A5%BF%E8%B7%AF%E9%9D%A0%E8%BF%91%E6%B1%87%E9%93%B6%E9%93%AD%E5%B0%8A6%E5%8F%B7%E6%A5%BC&sessionkey=abc0byf76P2U36jq9hEJv
     */

    /**
     *
     * URL	http://oa.yaomaiche.com:89/client.do?method=checkin&type=getStatus&sessionkey=abc0byf76P2U36jq9hEJv
     */
    /**
     * post
     * URL	http://oa.yaomaiche.com:89/client.do
     *
     */


}
