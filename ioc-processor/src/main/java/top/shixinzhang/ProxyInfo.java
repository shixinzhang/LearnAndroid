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

import javax.lang.model.element.PackageElement;
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
    private static final String SUFFIX = "ViewInjector";
    public Map<Integer, VariableElement> mInjectElements = new HashMap<>();
    private TypeElement mTypeElement;
    private String mPackageName;
    private String mProxyClassName;

    public ProxyInfo(final Elements elementUtils, final TypeElement typeElement) {
        mTypeElement = typeElement;
        PackageElement packageElement = elementUtils.getPackageOf(typeElement);
        mPackageName = packageElement.getQualifiedName().toString();
        String className = getClassName(typeElement, mPackageName);
        mProxyClassName = className + "$$" + SUFFIX;
        System.out.println("****** " + mProxyClassName + " \n" + mPackageName);
    }

    private String getClassName(final TypeElement typeElement, final String packageName) {
        int packageLength = packageName.length() + 1;   //

        return typeElement.getQualifiedName().toString().substring(packageLength).replace('.', '$');
    }

    public String generateJavaCode() {
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder 中不要再使用 + 拼接字符串
        stringBuilder.append("// Generate code. Do not modify it !\n")
                .append("package ").append(mPackageName).append(";\n\n")
                .append("import top.shixinzhang.ioc.*;\n\n")
                .append("public class ").append(mProxyClassName).append(" implements ").append(SUFFIX).append("<").append(mTypeElement.getQualifiedName()).append(">").append("{\n");
        generateMethod(stringBuilder);
        stringBuilder.append("\n}\n");
        return stringBuilder.toString();
    }

    private void generateMethod(final StringBuilder stringBuilder) {
        if (stringBuilder == null) {
            return;
        }
        stringBuilder.append("@Override\n")
                .append("public void inject(").append(mTypeElement.getQualifiedName()).append(" host, Object object )").append("{\n");

        for (Integer id : mInjectElements.keySet()) {
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();
            stringBuilder.append("if(object instanceof android.app.Activity)").append("{\n")
                    .append("host.").append(name).append(" = ")
                    .append("(").append(type).append(")((android.app.Activity)object).findViewById(").append(id).append(");")
                    .append("\n}\n")
                    .append("else").append("{\n")
                    .append("host.").append(name).append(" = ")
                    .append("(").append(type).append(")((android.view.View)object).findViewById(").append(id).append(");")
                    .append("\n}\n");
        }
        stringBuilder.append("\n}\n");
    }


    public String getProxyClassFullName() {
        return mPackageName + "." + mProxyClassName;
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }

}
