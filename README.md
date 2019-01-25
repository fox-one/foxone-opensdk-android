# foxone-opensdk-android
Fox.ONE开放平台SDK是一套区块链支付、结算、交易的系统解决方案，包括如下SDK：

* **passport**  提供用户账户系统能力及鉴权能力
* **payment**   提供数字货币钱包功能，包含支付，转账等能力
* **ex**  提供数字货币交易功能
* **otc** (待开发)

## 快速接入

* 依赖 `cloud` sdk

```
implementation 'com.fox.one:cloud:0.0.7'
```

* 在`Application.onCreate(..)`初始化`cloud`sdk

```
//MERCHANT_ID:平台分配的商户ID
//Options.logEnable: log开关
//Options.debugEnable: debug开关
//Options.env: 环境设置，建议开发中设置为Enviroment.ALPHA，在产品代码中设置为Enviroment.ALPHA
FoxSDK.init(this, MERCHANT_ID, FoxSDK.Options(logEnable = true, debugEnable = true, env = Enviroment.ALPHA))

```

到此，Fox.ONE的`cloud`sdk接入完成。接下来就可以根据自己的业务需求接入对应的SDK并使用相关API，具体参考如下：

* [账号系统接入说明](passport/README.md)
* [钱包功能接入数据](payment/README.md)
* [交易所功能接入说明](ex/README.md)

* **API调用举例**

```
PassportAPI.login(LoginWithPhoneReqBody(countryCode = "86", phoneNumber = phone, password = pwd))
                .enqueue(object: Callback<AccountInfo> {
                override fun onFailure(call: Call<AccountInfo>, t: Throwable) {
                     //异常处理
                    HttpErrorHandler.handle(t)
                }

                override fun onResponse(call: Call<AccountInfo>, response: Response<AccountInfo>) {
                    DemoApp.onLogin(this@LoginActivity, response.body() ?: AccountInfo())
                    LogUtils.i("foxone", "login::${JsonUtils.optToJson(response.body())}")
                }
            })
```

* **[错误码](cloud/src/main/java/com/fox/one/cloud/ErrorCode.kt)**
* **异常处理** [HttpErrorHandler](support-framework/src/main/java/com/fox/one/support/framework/network/HttpErrorHandler.kt)

```
//绑定错误码对应的提示文本
addErrorHint

//添加特定业务错误码的监听器，可以在监听器里自定义错误处理逻辑
addErrorListener

//添加http code错误监听器，自定义错误处理逻辑，比如httpCode == 401，说明用户token过去，可以提醒用户去重新登录
addHttpCodeListener

//捕获异常，在API接口onFailure时调用
handle(Throwable)

//捕获异常，自定义异常处理逻辑，在API接口onFailure时调用
handle(Throwable, Listener)

//通用网络错误提示语
networkErrorToast
```


## 注意事项

### 混淆

```
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes JavascriptInterface
-keepattributes InnerClasses
-keepattributes SourceFile
-keepattributes LineNumberTable
-keepattributes Deprecated
-keepattributes EnclosingMethod

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

-keep class android.support.v4.** {*;}

-keepclasseswithmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class * implements java.io.Serializable{*;}
-keepnames class * implements java.io.Serializable
-keepclasseswithmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class kotlin.**

-keepclasseswithmembers class * implements android.arch.lifecycle.GenericLifecycleObserver {
<init>(...);
}
# keep Lifecycle State and Event enums with fields
-keepclasseswithmembers class android.arch.lifecycle.Lifecycle$* { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclasseswithmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclasseswithmembers class * extends android.arch.lifecycle.ViewModel {
<init>(...);
}
-keepclasseswithmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
-keepclasseswithmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
-keep class * extends android.arch.persistence.room.RoomDatabase {*;}


-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keepclasseswithmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
```


### 注：文档中提到的相关SDK的版本可能比较低，建议接入[最新版本](http://jcenter.bintray.com/com/fox/one/)