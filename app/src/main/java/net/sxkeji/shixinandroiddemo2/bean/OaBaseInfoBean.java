package net.sxkeji.shixinandroiddemo2.bean;

import java.util.List;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/8.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class OaBaseInfoBean {

    /**
     * ryudidNew : 528D4tMS
     * hrmorgshow : true
     * headpic : /messager/images/icon_m_wev8.jpg
     * sessionkey : abcbgaSu7DihX9YAXLCJv
     * quicknews : [{"id":"1","module":"1","scope":"1","iconname":"","componentid":"1","label":"建流程","url":"/mobile/plugin/1/createlist.jsp"},{"id":"4","module":"4","scope":"6","iconname":"","componentid":"4","label":"建日程"}]
     * navigation : [{"id":"1","default":"0","url":"","displayname":"消息"},{"id":"2","default":"1","url":"","displayname":"应用"},{"id":"3","default":"0","url":"","displayname":"通讯录"},{"id":"4","default":"0","url":"","displayname":"我"}]
     * rongAppKey : pwe86ga5emv16
     * createworkflow : 1
     * modules : [{"id":"1","module":"1","scope":"1","iconname":"","count":"","component":"1","unread":"","label":"待办事宜","isPush":"1","group":"1"},{"id":"2","module":"9","scope":"2","iconname":"","count":"","component":"9","unread":"","label":"我的请求","isPush":"1","group":"1"},{"id":"3","module":"8","scope":"3","iconname":"","count":"","component":"8","unread":"","label":"已办事宜","isPush":"1","group":"1"},{"id":"4","module":"7","scope":"4","iconname":"","count":"","component":"7","unread":"","label":"归档事宜","isPush":"1","group":"1"},{"id":"5","module":"10","scope":"5","iconname":"","count":"","component":"10","unread":"","label":"抄送事宜","isPush":"1","group":"1"},{"id":"6","module":"4","scope":"6","iconname":"","count":"","component":"4","unread":"","label":"日程","isPush":"1","group":"2"},{"id":"7","module":"5","scope":"7","iconname":"","count":"","component":"5","unread":"","label":"会议","isPush":"1","group":"2"},{"id":"8","module":"6","scope":"8","iconname":"","count":"","component":"6","unread":"","label":"通讯录","isPush":"1","group":"2"},{"id":"9","module":"-2","scope":"9","iconname":"","count":"","component":"14","unread":"","label":"协作","isPush":"1","group":"2"},{"id":"13","module":"-1003","scope":"13","iconname":"","count":"","component":"-1003","unread":"","label":"考勤","isPush":"1","group":"2"}]
     * wctmodules : [{"categoryid":"1","categoryname":"流程"},{"categoryid":"2","categoryname":"日程"},{"categoryid":"3","categoryname":"会议"}]
     * groups : [{"id":"1","iconname":"","description":"","name":"流程","showorder":"1"},{"id":"2","iconname":"","description":"","name":"模块","showorder":"2"},{"id":"3","iconname":"","description":"","name":"移动签到","showorder":"3"}]
     * version : 6.0
     */

    private String ryudidNew;
    private String hrmorgshow;
    private String headpic;
    private String sessionkey;
    private String rongAppKey;
    private String createworkflow;
    private String version;
    private List<QuicknewsBean> quicknews;
    private List<NavigationBean> navigation;
    private List<ModulesBean> modules;
    private List<WctmodulesBean> wctmodules;
    private List<GroupsBean> groups;

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

    public List<QuicknewsBean> getQuicknews() {
        return quicknews;
    }

    public void setQuicknews(List<QuicknewsBean> quicknews) {
        this.quicknews = quicknews;
    }

    public List<NavigationBean> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<NavigationBean> navigation) {
        this.navigation = navigation;
    }

    public List<ModulesBean> getModules() {
        return modules;
    }

    public void setModules(List<ModulesBean> modules) {
        this.modules = modules;
    }

    public List<WctmodulesBean> getWctmodules() {
        return wctmodules;
    }

    public void setWctmodules(List<WctmodulesBean> wctmodules) {
        this.wctmodules = wctmodules;
    }

    public List<GroupsBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsBean> groups) {
        this.groups = groups;
    }

    public static class QuicknewsBean {
        /**
         * id : 1
         * module : 1
         * scope : 1
         * iconname :
         * componentid : 1
         * label : 建流程
         * url : /mobile/plugin/1/createlist.jsp
         */

        private String id;
        private String module;
        private String scope;
        private String iconname;
        private String componentid;
        private String label;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getIconname() {
            return iconname;
        }

        public void setIconname(String iconname) {
            this.iconname = iconname;
        }

        public String getComponentid() {
            return componentid;
        }

        public void setComponentid(String componentid) {
            this.componentid = componentid;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class NavigationBean {
        /**
         * id : 1
         * default : 0
         * url :
         * displayname : 消息
         */

        private String id;
        @com.google.gson.annotations.SerializedName("default")
        private String defaultX;
        private String url;
        private String displayname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }
    }

    public static class ModulesBean {
        /**
         * id : 1
         * module : 1
         * scope : 1
         * iconname :
         * count :
         * component : 1
         * unread :
         * label : 待办事宜
         * isPush : 1
         * group : 1
         */

        private String id;
        private String module;
        private String scope;
        private String iconname;
        private String count;
        private String component;
        private String unread;
        private String label;
        private String isPush;
        private String group;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getIconname() {
            return iconname;
        }

        public void setIconname(String iconname) {
            this.iconname = iconname;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public String getUnread() {
            return unread;
        }

        public void setUnread(String unread) {
            this.unread = unread;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getIsPush() {
            return isPush;
        }

        public void setIsPush(String isPush) {
            this.isPush = isPush;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }
    }

    public static class WctmodulesBean {
        /**
         * categoryid : 1
         * categoryname : 流程
         */

        private String categoryid;
        private String categoryname;

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }
    }

    public static class GroupsBean {
        /**
         * id : 1
         * iconname :
         * description :
         * name : 流程
         * showorder : 1
         */

        private String id;
        private String iconname;
        private String description;
        private String name;
        private String showorder;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIconname() {
            return iconname;
        }

        public void setIconname(String iconname) {
            this.iconname = iconname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShoworder() {
            return showorder;
        }

        public void setShoworder(String showorder) {
            this.showorder = showorder;
        }
    }
}
