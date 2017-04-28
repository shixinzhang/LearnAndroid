package net.sxkeji.shixinandroiddemo2.test;

import net.sxkeji.shixinandroiddemo2.bean.ActivityBean;

/**
 * description:
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 10/4/2016
 */
public class ThreadLocalTest {

    private static ThreadLocal<ActivityBean> activityBeanThreadLocal = new ThreadLocal<ActivityBean>(){
        @Override
        protected ActivityBean initialValue() {
            return super.initialValue();
        }
    };

    public static ActivityBean getActivityBean(){
        return activityBeanThreadLocal.get();
    }
}
