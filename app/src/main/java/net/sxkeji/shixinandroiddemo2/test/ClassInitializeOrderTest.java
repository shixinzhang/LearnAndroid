package net.sxkeji.shixinandroiddemo2.test;

/**
 * description: 测试类初始化顺序
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/5/2016
 */
public class ClassInitializeOrderTest {
    /**
     * 默认构造函数
     */
    public ClassInitializeOrderTest(){
        System.out.println(getClass().getName() + "调用默认构造函数");
    } 
    
    public ClassInitializeOrderTest(String s){
        System.out.println("");
    }
}
