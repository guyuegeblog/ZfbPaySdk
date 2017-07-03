#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.annotation.**
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#忽略警告
-ignorewarning
#####################记录生成的日志数据,gradle build时在本项目根目录输出################
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#####################记录生成的日志数据，gradle build时 在本项目根目录输出-end################
################<span></span>混淆保护自己项目的部分代码以及引用的第三方jar包library#########################
#-libraryjars libs/umeng-analytics-v5.2.4.jar
#-libraryjars libs/alipaysd<span></span>k.jar
#<span></span>-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/wup-1.0.0-SNAPSHOT.jar
#-libraryjars libs/weibosdkcore.jar
#三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
#-libraryjars libs/sdk-v1.0.0.jar
#-libraryjars libs/look-v1.0.1.jar
#我是以libaray的形式引用了一个图片加载框架,如果不想混淆 keep 掉
-keep class com.nostra13.universalimageloader.** { *; }
#友盟
-keep class com.umeng.**{*;}
#支付宝
-keep class com.alipay.android.app.IAliPay{*;}
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.lib.ResourceMap{*;}

#微信 dom4j
-keep class com.tencent.** { *;}
-keep class org.dom4j.** {*;}
-keep class com.google.zxing.ResultPoint

#信鸽推送
-keep class com.tencent.android.tpush.**  {* ;}
-keep class com.tencent.mid.**  {* ;}
#自己项目特殊处理代码
#忽略警告
-dontwarn com.veidy.mobile.common.**
#保留一个完整的包
-keep class com.veidy.mobile.common.** {
    *;
 }
-keep class  com.veidy.activity.login.WebLoginActivity{*;}
-keep class  com.veidy.activity.UserInfoFragment{*;}
-keep class  com.veidy.activity.HomeFragmentActivity{*;}
-keep class  com.veidy.activity.CityActivity{*;}
-keep class  com.veidy.activity.ClinikActivity{*;}
#如果引用了v4或者v7包
-dontwarn android.support.**
############<span></span>混淆保护自己项目的部分代码以及引用的第三方jar包library-end##################
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
#避免混淆泛型 如果混淆报错建议关掉
#–keepattributes Signature
#移除log 测试了下没有用还是建议自己定义一个开关控制是否输出日志
#-assumenosideeffects class android.util.Log {
#    public static boolean isLoggable(java.lang.String, int);
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int d(...);
#    public static int e(...);
#}

-keep public class * extends android.app.Activity

-keep class com.zfbpaysdk.pay.starzfbsdk.model.* {

public <fields>;

public <methods>;
}
-keep class com.zfbpaysdk.pay.starzfbsdk.ok.* {

public <fields>;

public <methods>;
}
-keep class com.zfbpaysdk.pay.starzfbsdk.view.* {

public <fields>;

public <methods>;
}
-keep class com.zfbpaysdk.pay.starzfbsdk.zfbpay.* {

public <fields>;

public <methods>;
}
-keep class com.starpay.unifum.aiwx.wxapi.* {

public <fields>;

public <methods>;
}
-keep class com.starpay.unifum.aihd.wxapi.* {

public <fields>;

public <methods>;
}
-keep class com.zfbpaysdk.pay.starzfbsdk.service.* {

public <fields>;

public <methods>;
}
-keep class com.zfbpaysdk.pay.starzfbsdk.push.* {

public <fields>;

public <methods>;
}

#so忽略混淆
#-libraryjars ../starzfbsdk/gtjni/jniLibs/arm64-v8a/libgetuiext2.so
#-libraryjars ../starzfbsdk/gtjni/jniLibs/armeabi/libgetuiext2.so
#-libraryjars ../starzfbsdk/gtjni/jniLibs/armeabi-v7a/libgetuiext2.so
#-libraryjars ../starzfbsdk/gtjni/jniLibs/mips/libgetuiext2.so
#-libraryjars ../starzfbsdk/gtjni/jniLibs/mips64/libgetuiext2.so
#-libraryjars ../starzfbsdk/gtjni/jniLibs/x86/libgetuiext2.so
#-libraryjars ../starzfbsdk/gtjni/jniLibs/x86_64/libgetuiext2.so
#
#-dontwarn com.igexin.**
#-keep class com.igexin.**{* ;}
#-keep class org.json.**{ *; }

#-libraryjars ../starzfbsdk/libs/GetuiSDK2.10.2.0.jar
#-libraryjars ../starzfbsdk/libs/json_simple-1.1.jar

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-ignorewarning
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService

-keep class org.doubango.** {*;}
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#下面的类将不会被混淆，这样的类是需要被jar包使用者直接调用的
-keep public class com.switfpass.pay.activity.PayPlugin{
    public <fields>;
    public <methods>;
}
#jar包依赖的其他库的位置和名称
#-libraryjars 'E:\workspace\wftsdk\libs\httpmime-4.2.1.jar'
#-libraryjars 'E:\workspace\wftsdk\libs\alipaySDK-20150610.jar'
#-libraryjars 'E:\workspace\wftsdk\libs\libammsdk.jar'
#-libraryjars 'G:\android-sdks\android-sdks\platforms\android-14\android.jar'
#-libraryjars 'E:\workspace\wftsdk\libs\afinal_0.5_bin.jar'


