# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

#------------------------------------------------------------------------->

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#------------------------------------------------------------------------->

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable
#------------------------------------------------------------------------->

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#------------------------------------------------------------------------->
#for avi library if needed
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
#------------------------------------------------------------------------->
-keep class ir.fararayaneh.erp.data.** { *; }
#------------------------------------------------------------------------->
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
#------------------------------------------------------------------------->
-ignorewarnings
#------------------------------------------------------------------------->
#for fab in report activity ('uk.co.markormesher:android-fab:2.5.0')
-dontwarn java.lang.invoke.*
#------------------------------------------------------------------------->
#for jacson
-dontwarn com.fasterxml.jackson.**
-dontwarn com.fasterxml.jackson.databind.**
-keep class com.fasterxml.jackson.** { *; }
-keep class org.codehaus.** { *; }
-keep public class your.class.** {*;}
-keepattributes *Annotation*,EnclosingMethod,Signature
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames interface com.fasterxml.jackson.** { *; }
-keepclassmembers class com.fasterxml.jackson.**  { *; }
-keepclassmembernames class com.fasterxml.jackson.**  { *; }
-keepclasseswithmembernames class com.fasterxml.jackson.** { *; }
-keepclasseswithmembers class com.fasterxml.jackson.**  { *; }
-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
    public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *; }


