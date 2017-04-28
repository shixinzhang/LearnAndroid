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

public class GenericErasure {
    interface Game {
        void play();
    }
    interface Program{
        void code();
    }

    public static class People<T extends Program & Game>{
        public T mPeople;

        public People(T people){
            mPeople = people;
        }

        public void habit(){
            mPeople.code();
            mPeople.play();
        }
    }
}
