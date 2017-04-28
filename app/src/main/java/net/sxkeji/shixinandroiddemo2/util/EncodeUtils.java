package net.sxkeji.shixinandroiddemo2.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class EncodeUtils {
    private EncodeUtils(){}

    public static String urlEncode(String s){
        try {
            String encode = URLEncoder.encode(s, "UTF-8");
            encode = encode.replaceAll("%3A",":").replaceAll("%2F","/");
            return encode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String urlDecode(String encode){
        try {
            return URLDecoder.decode(encode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
