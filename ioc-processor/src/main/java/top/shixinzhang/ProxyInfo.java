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

package top.shixinzhang;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/6/21.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class ProxyInfo {
    private static final String SUFFIX = "";
    public Map<Integer, VariableElement> mInjectElements = new HashMap<>();
    private String mProxyClassFullName;
    private TypeElement mTypeElement;
    private String mPackageName;
    private String mProxyClassName;

    public ProxyInfo(final Elements elementUtils, final TypeElement typeElement) {

    }

    public String getProxyClassFullName() {
        return mProxyClassFullName;
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }

    public String generateJavaCode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package " + mPackageName).append(";\n\n")
                .append("import top.shixinzhang.ioc.*;\n")
                .append("public class ").append(mProxyClassName).append(" implements ").append(SUFFIX).append("<").append(mTypeElement.getQualifiedName()).append(">")  //stringBuilder 中不要再使用 + 拼接字符串
                .append("{\n");
        generateMethod(stringBuilder);
        stringBuilder.append("\n}\n");
        return stringBuilder.toString();
    }

    private void generateMethod(final StringBuilder stringBuilder) {
        if (stringBuilder == null) {
            return;
        }
        stringBuilder.append("public void inject(").append(mTypeElement.getQualifiedName()).append(" host, Object object )")
                .append("{\n");
        for (Integer id : mInjectElements.keySet()) {
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();
            stringBuilder.append("if(object instanceof android.app.Activity)").append("{\n")
                    .append("host.").append(name).append(" = ")
                    .append("(").append(type).append(")((android.app.Activity)object).findViewById(").append(id).append("));")
                    .append("\n}\n")
                    .append("else").append("{\n")
                    .append("host.").append(name).append(" = ")
                    .append("(").append(type).append(")((android.view.View)object).findViewById(").append(id).append("));")
                    .append("\n}\n");
        }
        stringBuilder.append("\n}\n");
    }
}
