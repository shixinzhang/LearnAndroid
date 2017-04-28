package net.sxkeji.shixinandroiddemo2.designpattern;

import net.sxkeji.shixinandroiddemo2.bean.Person;
import net.sxkeji.shixinandroiddemo2.test.designpattern.PersonThree;
import net.sxkeji.shixinandroiddemo2.test.designpattern.PersonTwo;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/11/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class BuilderTest {
    @Test
    public void build() throws Exception {
        new PersonThree.Builder("shixinzhang")
                .setLocation("Shanghai")
                .setJob("Android Develop")
                .setHabit("LOL")
                .build();


        PersonTwo personTwo = new PersonTwo("shixin");
        personTwo.setJob("洗剪吹");
        personTwo.setLocation("温州");
        personTwo.setHabit("嘿嘿嘿");

        Stack<String> stack = new Stack();
        stack.add(null);
        System.out.println(stack);

        Deque<Person> deque = new LinkedList<>();
        deque.addFirst(null);

        PriorityBlockingQueue<Person> p = new PriorityBlockingQueue<>();
        p.add(null);

//        ArrayBlockingQueue
    }

}