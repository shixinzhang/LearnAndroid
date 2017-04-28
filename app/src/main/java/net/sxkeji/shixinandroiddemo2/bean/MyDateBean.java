package net.sxkeji.shixinandroiddemo2.bean;

/**
 * description:
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/5/2016
 */
public class MyDateBean implements Comparable{
    private String year;
    private String month;
    private String day;

    public MyDateBean(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "MyDateBean{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof MyDateBean){
            MyDateBean another1 = (MyDateBean) another;
            return getYear().compareTo(another1.getYear());
        }
        return 0;
    }
}
