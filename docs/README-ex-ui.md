# Exchange with UI

`交易模块` 提供集成交易钱包功能，交易功能，币对行情功能，用户收藏功能，实时数据流订阅功能等及相关交互UI，快速接入带UI功能的交易模块，省去了交互和UI的繁琐工作。

## 接入

* 依赖

```groovy
implementation 'com.fox.one:ex-ui:1.2.6'
```
查看[最新版本](http://jcenter.bintray.com/com/fox/one/ex-ui)

* 初始化

```kotlin
//在application.onCreate方法里添加如下代码
ExModule.init(this)
```

* 设置登录状态失效时跳转登录界面逻辑

```kotlin
//当接口返回KYC认证失败时，通知用户相应的状态信息，并根据业务需求做相应处理
ExModule.setOnKYCCheckListener { context, status ->
            when(status) {
                KYCStatus.NOT_YET -> {
                    ToastUtil.show(context, "kyc not yet")
                }
                KYCStatus.HAVE_CREATED -> {
                    ToastUtil.show(context, "kyc been created")
                }
                KYCStatus.IN_PROGRESS -> {
                    ToastUtil.show(context, "kyc in progress")
                }
                KYCStatus.AUTO_VERIFICATION_PASSED -> {
                    ToastUtil.show(context, "kyc auto verification passed")
                }
                KYCStatus.HUMAN_VERIFICATION_PASSED -> {
                    ToastUtil.show(context, "kyc human verification passed")
                }
                KYCStatus.REJECTED -> {
                    ToastUtil.show(context, "kyc rejected")
                }
            }
        }
```

* 设置KYC认证失败时对应错误处理逻辑

```kotlin
//当接口返回http code为401错误（用户认证失败）时，跳转登录界面叫用户重新登录
ExModule.setOnLaunchLoginUIListener {
            LoginActivity.start(it)
        }
```

* 注入Account信息

```kotlin
//当用户登录成功时，注入登录信息
AccountManager.login(AccountInfo)

```

* 登出

```kotlin
//当用户登出时，清除登录状态信息
AccountManager.logout()

```

* 清除缓存Cache

```kotlin
//当切换新的用户登录时，调用该接口清除上个用户的缓存数据
ExModule.cleanCache()

```

* 接入用户收藏界面，[参考](../app/src/main/java/com/fox/one/demo/ExchangeActivity.kt)

```kotlin

supportFragmentManager.beginTransaction()
            .replace(R.id.container_exchange, favFragment)
            .commitAllowingStateLoss()

        favFragment.onEmptyViewClick = {
            if (AccountManager.isLogin()) {
                MarketActivity.start(this@ExchangeActivity)
            } else {
                AccountManager.launchLoginUI(this)
            }
        }

```

* 接入用户钱包界面

```kotlin

WalletActivity.start(this@MainActivity)

```

* 接入资金划转功能

```kotlin
TransferActivity.start(Context)
```

* 设置UI样式。自定义UI样式，继承自Theme.F1EX.Day，并设置相应属性

```xml
 <style name="Theme.F1EX.Day" parent="Theme.F1EX.Base">
         <!--app 主背景色-->
         <item name="f1exBackgroundColor">@color/background_color_day</item>
         <!--app 第二背景色，一般为浮层块背景色，如卡片-->
         <item name="f1exBackgroundSecondColor">@color/background_second_color_day</item>
         <!--app 第三背景色，一般为浮层块上面的浮层块背景色(第三层)-->
         <item name="f1exBackgroundThirdColor">@color/background_third_color_day</item>
         <!--主前景色-->
         <item name="f1exPrimaryColor">@color/primary_color_day</item>
         <!--第二前景色-->
         <item name="f1exSecondColor">@color/second_color_day</item>
         <!--主文字前景色-->
         <item name="f1exTextPrimaryColor">@color/text_primary_color_day</item>
         <!--第二文字前景色，比如副标-->
         <item name="f1exTextSecondColor">@color/text_second_color_day</item>
         <!--分割线颜色-->
         <item name="f1exDividerColor">@color/divider_color_day</item>
         <!--错误警告色-->
         <item name="f1exErrorColor">@color/error_color_day</item>
         <!--状态栏颜色-->
         <item name="f1exStatusBarColor">@color/status_color_day</item>
         <!--ActionBar颜色-->
         <item name="f1exActionBarColor">@color/action_bar_color_day</item>
         <!--添加按钮图标-->
         <item name="f1exIconAdd">@drawable/ic_add_black</item>
         <!--搜索按钮图标-->
         <item name="f1exIconSearch">@drawable/ic_search_black</item>
         <!--历史按钮图标-->
         <item name="f1exIconHistory">@drawable/ic_history_black</item>
         <!--左上角返回按钮图标-->
         <item name="f1exIconBack">@drawable/ic_back_black</item>
         <!--loading图标-->
         <item name="f1exIconLoading">@drawable/spinner_black</item>
         <!--设置：交易图标-->
         <item name="f1exIconExchange">@drawable/ic_me_my_orders</item>
         <!--设置：通用设置图标-->
         <item name="f1exIconSetting">@drawable/ic_setting_black</item>
         <!--withdraw图标-->
         <item name="f1exIconWithdraw">@drawable/ic_withdraw_day</item>
         <!--deposit图标-->
         <item name="f1exIconDeposit">@drawable/ic_deposit_day</item>
         <!--向下箭头图标-->
         <item name="f1exIconArrowDown">@drawable/ic_arrow_down_day</item>
         <!--CAMERA图标-->
         <item name="f1exIconCamera">@drawable/ic_camera_day</item>
         <!--QRCode图标-->
         <item name="f1exIconQRCode">@drawable/ic_qrcode_day</item>
     </style>

```

* 在AndroidManifest.xml里应用样式

```xml
<!--在Manifest里设置样式-->
<application
            android:name=".DemoApp"
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/DemoTheme"></application>
```