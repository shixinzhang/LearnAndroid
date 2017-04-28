package net.sxkeji.shixinandroiddemo2.test.generic;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/9.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class FavoriteTest {
    @Test
    public void putFavorite() throws Exception {
        Favorite f = new Favorite();
        f.putFavorite(String.class, "shixin");
        f.putFavorite(Object.class, "23");
        System.out.println(f.getFavorite(String.class));
        System.out.println(f.getFavorite(Object.class));
    }

}