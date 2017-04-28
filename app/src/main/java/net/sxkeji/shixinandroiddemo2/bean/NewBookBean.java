package net.sxkeji.shixinandroiddemo2.bean;

import java.io.Serializable;

/**
 * description: 测试用的实体类 书, 没有实现 自然排序 Comparable 接口
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/5/2016
 */
public class NewBookBean implements Serializable{
    private String name;
    private int count;


    public NewBookBean(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 重写 equals
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewBookBean)) return false;

        NewBookBean bean = (NewBookBean) o;

        if (getCount() != bean.getCount()) return false;
        return getName().equals(bean.getName());

    }

    /**
     * 重写 hashCode 的计算方法
     * 根据所有属性进行 迭代计算，避免重复
     * 计算 hashCode 时 计算因子 31 见得很多，是一个质数，不能再被除
     * @return
     */
    @Override
    public int hashCode() {
        //调用 String 的 hashCode(), 唯一表示一个字符串内容
        int result = getName().hashCode();
        //乘以 31, 再加上 count
        result = 31 * result + getCount();
        return result;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }

}
