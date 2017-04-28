package net.sxkeji.shixinandroiddemo2.test;

import net.sxkeji.shixinandroiddemo2.util.StringUtils;

import org.junit.Test;

import java.util.Random;

/**
 * description:
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/21/2016
 */
class JavaSETest {

    @Test
    public void testTestEqual() throws Exception {
        Integer a = 200;
        Integer b = 200;
        System.out.println(a == b);
    }

    /**
     * 测试 Random 的含参、不含参构造方法
     */
    @Test
    public void testRandomParameter() {
        System.out.println("Random 不含参构造方法：");
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + random.nextInt(100) + ", ");
            }

            System.out.println("");
        }

        System.out.println("");

        System.out.println("Random 含参构造方法：");
        for (int i = 0; i < 5; i++) {
            Random random = new Random(50);
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + random.nextInt(100) + ", ");
            }
            System.out.println("");
        }
    }

    /**
     * 测试 逻辑运算符 与 按位运算符
     *
     * 逻辑运算符： &&、|| ，短路
     * 按位运算符：&、|、~、^，非短路
     *
     * 对于布尔值，按位运算符与逻辑运算符有一样的结果
     */
    @Test
    public void testShortCircuitLogic() {
        System.out.println("&& 逻辑运算符，短路：");
        boolean shortCircuit = test1(2) && test2(1) && test3(7);
        System.out.println(shortCircuit);

        System.out.println("& 按位运算符，不短路：");
        boolean notShortCircuit = test1(2) & test2(1) & test3(7);
        System.out.println(notShortCircuit);

    }

    boolean test1(int v) {
        System.out.println("test1");
        return v > 0;
    }

    boolean test2(int v) {
        System.out.println("test2");
        return v > 2;
    }

    boolean test3(int v) {
        System.out.println("test3");
        return v < 6;
    }

    /**
     * 按位运算符 shift/bitwise operation
     * 左移
     *      <<，低位 补 0
     * 右移
     *      >>，有符号，正数高位补 0；负数高位补 1
     *      >>>，无符号，直接补 0
     *
     */
    @Test
    public void testShiftOperation(){
        int a = 5;
        System.out.println(Integer.toBinaryString(a));
        a <<= 3;    // a = a >> 3
        System.out.println(Integer.toBinaryString(a));

        a *= -1;
        System.out.println(Integer.toBinaryString(a));

        int c = a;

        a >>= 3;
        System.out.println(Integer.toBinaryString(a));

        c >>>= c;
        System.out.println(Integer.toBinaryString(c));

        int b = 1 << 32;   //最多 31（也就是4位，2进制里5位就到了32），否则就会有警告:超出 int 最大值 2 ^ 31

    }

    @Test
    public void testBitWiseOperation(){
        System.out.println("" + tableSizeFor(5));
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private static int MAXIMUM_CAPACITY = 1 << 30;

    @Test
    public void testString(){
        boolean s = StringUtils.isRgbValue("000000");
        System.out.println(s);
    }
}