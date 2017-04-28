package net.sxkeji.shixinandroiddemo2.view.sortlistview;

import java.io.Serializable;

public class SortModel implements Serializable {

    private String name;   //显示的数据
    private String firstLetter;  //显示数据拼音的首字母
    public String logoUrl = "";
    public String sellCount;    //在售品牌数量
    public String id;
    public boolean inventory;
    public String displayOrder;

    public String getSellCount() {
        return sellCount;
    }

    public void setSellCount(String sellCount) {
        this.sellCount = sellCount;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLetters() {
        return firstLetter;
    }

}
