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

package top.shixinzhang.datacrawler.model;

import java.io.Serializable;

/**
 * Description:
 * <br> 供应商信息，包括：电话、姓名、公司、发布的车型
 * <p>
 * <br> Created by shixinzhang on 17/8/3.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class SupplierInfoBean implements Serializable{

    private String phone;
    private String name;
    private String company;
    private String publishInfo; //发布的信息

    public String getPhone() {
        return phone;
    }

    public SupplierInfoBean setPhone(final String phone) {
        this.phone = phone;
        return this;
    }

    public String getName() {
        return name;
    }

    public SupplierInfoBean setName(final String name) {
        this.name = name;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public SupplierInfoBean setCompany(final String company) {
        this.company = company;
        return this;
    }

    public String getPublishInfo() {
        return publishInfo;
    }

    public SupplierInfoBean setPublishInfo(final String publishInfo) {
        this.publishInfo = publishInfo;
        return this;
    }
}
