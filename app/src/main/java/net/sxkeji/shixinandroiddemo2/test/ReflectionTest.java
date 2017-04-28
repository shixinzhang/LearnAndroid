package net.sxkeji.shixinandroiddemo2.test;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/4.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ReflectionTest {

    public void reflectFirst(){
        byte[] bytes = new byte[1024];
        Class<? extends byte[]> c = bytes.getClass();

//        Integer.class.newInstance();
//        int.class.newInstance();

        Class b = int[][].class;

        try {
            Class.forName("[D");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
