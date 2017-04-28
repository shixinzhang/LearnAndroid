package net.sxkeji.shixinandroiddemo2.bean;

import java.io.Serializable;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class OaLoginResultBean implements Serializable{

    /**
     * ryudidNew : 528D4tMS
     * hrmorgshow : true
     * headpic : /messager/images/icon_m_wev8.jpg
     * sessionkey : abc4vPejWNAIYblmR7CJv
     * navigation : [{"id":"1","default":"0","url":"","displayname":"消息"},{"id":"2","default":"1","url":"","displayname":"应用"},{"id":"3","default":"0","url":"","displayname":"通讯录"},{"id":"4","default":"0","url":"","displayname":"我"}]
     * rongAppKey : pwe86ga5emv16
     * createworkflow : 1
     * version : 6.0
     */

    private String ryudidNew;
    private String hrmorgshow;
    private String headpic;
    private String sessionkey;
    private String rongAppKey;
    private String createworkflow;
    private String version;

    public String getRyudidNew() {
        return ryudidNew;
    }

    public void setRyudidNew(String ryudidNew) {
        this.ryudidNew = ryudidNew;
    }

    public String getHrmorgshow() {
        return hrmorgshow;
    }

    public void setHrmorgshow(String hrmorgshow) {
        this.hrmorgshow = hrmorgshow;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getRongAppKey() {
        return rongAppKey;
    }

    public void setRongAppKey(String rongAppKey) {
        this.rongAppKey = rongAppKey;
    }

    public String getCreateworkflow() {
        return createworkflow;
    }

    public void setCreateworkflow(String createworkflow) {
        this.createworkflow = createworkflow;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
