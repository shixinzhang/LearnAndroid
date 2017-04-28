package net.sxkeji.shixinandroiddemo2.bean;

/**
 * description:
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/5/2016
 */
public class EmployeeBean implements Comparable{
    private String name;
    private int age;
    private MyDateBean birthday;

    public EmployeeBean(String name, int age, MyDateBean birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MyDateBean getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDateBean birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "EmployeeBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof EmployeeBean){
            EmployeeBean employeeBean = (EmployeeBean) another;
            return getName().compareTo(employeeBean.getName());
        }
        return 0;
    }
}
