package net.sxkeji.shixinandroiddemo2.bean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/13.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class Room extends RealmObject {
    public RealmList<Dog> dogList;

    public RealmList<Dog> getDogList() {
        return dogList;
    }

    public void setDogList(RealmList<Dog> dogList) {
        this.dogList = dogList;
    }
}
