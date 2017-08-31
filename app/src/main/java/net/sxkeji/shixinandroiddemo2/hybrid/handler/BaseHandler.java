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

package net.sxkeji.shixinandroiddemo2.hybrid.handler;

import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridEvent;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridHandlerResult;

/**
 * Description:
 * <br> 处理器接口
 * <p>
 * <br> Created by shixinzhang on 17/8/31.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public abstract class BaseHandler {

    public abstract HybridHandlerResult reactEvent(HybridEvent event);
}
