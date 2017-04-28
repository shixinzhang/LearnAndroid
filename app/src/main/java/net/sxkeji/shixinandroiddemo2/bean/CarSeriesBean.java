package net.sxkeji.shixinandroiddemo2.bean;

import java.io.Serializable;

/**
 * 车系实体类
 * Created by zhangshixin on 7/11/2016.
 */
public class CarSeriesBean implements Serializable {
    private String logoUrl;
    private String sellCount;    //在售车系数量
    private String id;
    private String name;

    public CarSeriesBean(String logoUrl, String sellCount, String id, String name) {
        this.logoUrl = logoUrl;
        this.sellCount = sellCount;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSellCount() {
        return sellCount;
    }

    public void setSellCount(String sellCount) {
        this.sellCount = sellCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
