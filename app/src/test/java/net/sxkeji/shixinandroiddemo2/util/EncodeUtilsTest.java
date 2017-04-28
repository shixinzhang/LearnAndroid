package net.sxkeji.shixinandroiddemo2.util;

import org.junit.Test;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class EncodeUtilsTest {
    @Test
    public void urlDecode() throws Exception {
        String s = "%E4%B8%8A%E6%B5%B7%E5%B8%82%E6%99%AE%E9%99%80%E5%8C%BA%E5%85%89%E5%A4%8D%E8%A5%BF%E8%B7%AF%E9%9D%A0%E8%BF%91%E6%B1%87%E9%93%B6%E9%93%AD%E5%B0%8A6%E5%8F%B7%E6%A5%BC";
        s = EncodeUtils.urlDecode(s);
        System.out.println(s);
    }

}