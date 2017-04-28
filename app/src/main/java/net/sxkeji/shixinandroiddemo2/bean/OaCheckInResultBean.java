package net.sxkeji.shixinandroiddemo2.bean;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class OaCheckInResultBean {

    /**
     * result : success
     * msg : 如因工作原因迟到或早退请提交相应流程，签到（签退）时间：2016-12-08 19:22:13
     */

    private String result;
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "签到/签退结果:\n" +
                result + '\n' +
                msg ;
    }
}
