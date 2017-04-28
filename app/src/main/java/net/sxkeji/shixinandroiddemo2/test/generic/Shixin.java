package net.sxkeji.shixinandroiddemo2.test.generic;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/9.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class Shixin implements GenericErasure.Game, GenericErasure.Program{

    @Override
    public void play() {
        System.out.println("play");
    }

    @Override
    public void code() {
        System.out.println("code");
    }
}
