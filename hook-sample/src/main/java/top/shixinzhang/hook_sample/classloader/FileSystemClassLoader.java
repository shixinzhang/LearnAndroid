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

package top.shixinzhang.hook_sample.classloader;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 * https://www.ibm.com/developerworks/cn/java/j-lo-classloader/
 * <p>
 * <br> 自定义类加载器，加载文件夹中的 class 文件
 * <p>          一般来说，自己开发的类加载器只需要覆写 findClass(String name)方法即可
 * <p>
 * <br> Created by shixinzhang on 17/8/29.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class FileSystemClassLoader extends ClassLoader {
    private String rootDir;

    public FileSystemClassLoader(final String dir) {
        this.rootDir = dir;
    }

    /**
     * 三层从上往下：启动类加载器、拓展类加载器、应用类加载器
     * <p>
     * java.lang.ClassLoader 类的 loadClass() 封装了双亲委派思想，先检查两个父类是否加载
     * <p>
     * 双亲都不加载一个类时，会调用当前类加载器的 findClass() 方法
     * <p>
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("class name can't be empty !");
        }
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        }

        // defineClass 可以将字节数组转换为 Class 实例
        return defineClass(name, classData, 0, classData.length);
    }

    /**
     * 读取字节码的二进制数组
     *
     * @param className
     * @return
     */
    private byte[] getClassData(@NonNull final String className) {
        String path = classNameToPath(className);
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            inputStream = new FileInputStream(path);
            baos = new ByteArrayOutputStream();
            int bufferSize = 1024 * 4;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead;
            while ((bytesNumRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();

            Utils.closeQuietly(inputStream);
            Utils.closeQuietly(baos);
        }

        return null;
    }

    /**
     * 拼接要加载类的全路径
     *
     * @param className
     * @return
     */
    private String classNameToPath(@NonNull final String className) {
        return rootDir + File.separator + className.replace(".", File.separator) + ".class";
    }
}
