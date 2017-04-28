package net.sxkeji.shixinandroiddemo2.test.reflection;

import java.lang.reflect.Field;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/15.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class FieldTrouble extends BaseTestClass {
    public Integer value;
    private boolean wannaPlayGame = true;

    public static void main(String[] args) {
        FieldTrouble fieldTrouble = new FieldTrouble();
        Class<? extends FieldTrouble> cls = fieldTrouble.getClass();
        try {
//            Field value = cls.getField("value");
//            value.setInt(fieldTrouble, 23);
//            value.set(fieldTrouble, new Integer(23));

            Field wannaPlayGame = cls.getDeclaredField("wannaPlayGame");
//            wannaPlayGame.setAccessible(true);
            wannaPlayGame.setBoolean(fieldTrouble, false);
            System.out.format("Field：%s    %s\n", wannaPlayGame.getName(),fieldTrouble.wannaPlayGame);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
