package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/14.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

enum MyHabit {
    LOL,
    CODE
}

public class People extends BaseTestClass {
    public long idCarNumber = 1000000000;
    public String[] name = {"shi", "xin"};
    public MyHabit habit = MyHabit.CODE;

    public static void main(String[] args) {
        People shixin = new People();
        String fmt = "%6s:  %s  = %s \n";
        Class<? extends People> cls = shixin.getClass();
        try {
            Field idCarNumber = cls.getDeclaredField("idCarNumber");
            System.out.format(fmt, "before", idCarNumber.getName(), shixin.idCarNumber);
            idCarNumber.setLong(shixin, 123456);
            System.out.format(fmt, "after", idCarNumber.getName(), shixin.idCarNumber);

            Field name = cls.getDeclaredField("name");
            System.out.format(fmt, "before", name.getName(), Arrays.asList(shixin.name));
            name.set(shixin, new String[]{"hei", "hei"});
            System.out.format(fmt, "after", name.getName(), Arrays.asList(shixin.name));

            Field habit = cls.getDeclaredField("habit");
            System.out.format(fmt, "before", habit.getName(), shixin.habit);
            habit.set(shixin, MyHabit.LOL);
            System.out.format(fmt, "after", habit.getName(), shixin.habit);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
