package net.sxkeji.shixinandroiddemo2.activity.eventbus;

import java.io.Serializable;

/**
 * <header>
 * Description:
 * </header>
 * <p>
 * Author: shixinzhang
 * </p>
 * <p/>
 * Create at: 6/10/2017
 */
public class MessageEvent implements Serializable {
    private static final long serialVersionUID = -1371779234999786464L;
    private String message;
    private String time;

    public MessageEvent(String message, String time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
