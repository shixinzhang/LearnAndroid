/*
 * Copyright (c) 2017. shixinzhang (shixinzhang2016@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sxkeji.shixinandroiddemo2.activity.eventbus;

import top.shixinzhang.sxframework.eventsubscribe.third.eventbus.EventBus;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/6/12.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class EventBusManager {
    private static volatile EventBusManager mInstance;
    private EventBus mIntervalInstance;

    private EventBusManager() {
        mIntervalInstance = EventBus.builder()
//                .addIndex(new MyEventBusIndex())
                .build();
    }

    public static synchronized EventBusManager getInstance() {
        if (mInstance == null) {
            mInstance = new EventBusManager();
        }
        return mInstance;
    }

    public void register(Object subscriber){
        if (!mIntervalInstance.isRegistered(subscriber)) {
            mIntervalInstance.register(subscriber);
        }
    }

    public void unRegister(Object subscriber){
        mIntervalInstance.unregister(subscriber);
    }

    public void post(final Object event){
        mIntervalInstance.post(event);
    }
}
