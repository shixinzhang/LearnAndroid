package net.sxkeji.shixinandroiddemo2.mvc.model;

import net.sxkeji.shixinandroiddemo2.bean.PhoneInfo;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public interface OnPhoneInfoListener {
    void onSuccess(PhoneInfo phoneInfo);
    void onError(String errorMsg);
}
