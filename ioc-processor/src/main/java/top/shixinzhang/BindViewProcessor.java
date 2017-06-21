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

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
@SupportedAnnotationTypes("top.shixinzhang.BindView")
@SupportedSourceVersion(SourceVersion.RELEASE_7)    //支持的源码版本
public class BindViewProcessor extends AbstractProcessor{
    private Elements mElementUtils; //操作元素的工具方法
    private Filer mFileCreator;     //代码创建者
    private Messager mMessager;     //日志，提示者，提示错误、警告

    private Map<String, ProxyInfo> mProxyMap = new HashMap<>();

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mFileCreator = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    /**
     * 1.收集信息   2.生成代理类
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        //避免生成重复的代理类
        mProxyMap.clear();

        //拿到被 @BindView 注解修饰的元素，应该是 VariableElement
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);
        //1.收集信息
        for (Element element : elements) {
            if (!checkAnnotationValid(element)){
                return false;
            }

            //类中的成员变量
            VariableElement variableElement = (VariableElement) element;
            //类或者接口
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            //完整的名称
            String qualifiedName = typeElement.getQualifiedName().toString();

            ProxyInfo proxyInfo = mProxyMap.get(qualifiedName);
            if (proxyInfo == null){
                //将该类中被注解修饰的变量加入到 ProxyInfo 中
                proxyInfo = new ProxyInfo(mElementUtils, typeElement);
                mProxyMap.put(qualifiedName, proxyInfo);
            }

            BindView annotation = variableElement.getAnnotation(BindView.class);
            int id = annotation.value();
            proxyInfo.mInjectElements.put(id, variableElement);

        }


        //2.生成代理类
        for (String key : mProxyMap.keySet()) {
            ProxyInfo proxyInfo = mProxyMap.get(key);
            try {
                JavaFileObject sourceFile = mFileCreator.createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.getTypeElement());   //创建文件对象
                Writer writer = sourceFile.openWriter();
                writer.write(proxyInfo.generateJavaCode());     //写入文件
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 检查 element 类型
     * @param element
     * @return
     */
    private boolean checkAnnotationValid(final Element element) {
        return false;
    }

//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> annotationTypes = new LinkedHashSet<>();
//        annotationTypes.add(BindView.class.getCanonicalName());
//        return annotationTypes;
//    }


//    /**
//     * 支持的源码版本
//     * @return
//     */
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }

}
