package net.sxkeji.shixinandroiddemo2.bean;

/**
 * Created by zhangshixin on 8/30/2016.
 * Update on ${DATA}
 */
public class ActivityBean  {
    private String mName;
    private Class<?> mClz;

    public ActivityBean(String name, Class<?> aClass) {
        mName = name;
        mClz = aClass;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Class<?> getClaz() {
        return mClz;
    }

    public void setClaz(Class<?> aClass) {
        mClz = aClass;
    }

    @Override
    public String toString() {
        return "ActivityBean{" +
                "mName='" + mName + '\'' +
                ", mClz=" + mClz +
                '}';
    }
}
