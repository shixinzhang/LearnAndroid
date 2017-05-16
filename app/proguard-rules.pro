# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\zsx\AndroidSdk1/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-ignorewarnings
-keep public class * extends android.os.Binder
-keep class sun.misc.Unsafe { *; }
-dontwarn java.lang.invoke.*
-keepclassmembers enum * {
    **[] $VALUES;
    public *;
}

# weex
-keep class com.taobao.weex.WXDebugTool{*;}
-keep class com.taobao.weex.devtools.common.LogUtil{*;}
-keep public class * extends com.taobao.weex.ui.component.WXComponent{*;}
-keepclassmembers class ** {
  @com.taobao.weex.ui.component.WXComponentProp public *;
}
-keep class com.taobao.weex.bridge.**{*;}
-keep class com.taobao.weex.dom.**{*;}
-keep class com.taobao.weex.adapter.**{*;}
-keep class com.taobao.weex.common.**{*;}
-keep class * implements com.taobao.weex.IWXObject{*;}
-keep class com.taobao.weex.ui.**{*;}
-keep class com.taobao.weex.ui.component.**{*;}
-keep class com.taobao.weex.utils.**{
    public <fields>;
    public <methods>;
    }
-keep class com.taobao.weex.view.**{*;}
-keep class com.taobao.weex.module.**{*;}

# butterknife
-keep public class * implements butterknife.Unbinder { public <init>(...); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

# v7
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

# retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# okhttp
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**

# log
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int d(...);
    public static int w(...);
    public static int v(...);
    public static int i(...);
}

# gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
