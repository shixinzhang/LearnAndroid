package net.sxkeji.shixinandroiddemo2.helper;

/**
 * <br/> Description: 配置信息
 * <p>
 * <br/> Created by shixinzhang on 17/4/19.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ConfigHelper {
    public final static String ACTION_START_DAEMON_SERVICE = "top.shixinzhang.action.service.start.daemon";

    public final static String ACTION_START_SX_SERVICE = "top.shixinzhang.action.service.start.sx";

    public final static String ACTION_REPEAT_BROADCAST_RECEIVER = "top.shixinzhang.action.receiver.repeat";

    //重复服务的间隔
    public static final long DEFAULT_REPEAT_INTERVAL = 4000L;

    //SharedPreferences 数据的 key
    public static final String SP_START_TIME = "start_time";    //定时任务开始时间
    public static final String SP_STOP_TIME = "stop_time";      //定时任务结束时间
}