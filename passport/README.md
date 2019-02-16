# Passport

提供用户手机、邮箱注册，登录，找回密码，修改密码等功能。

## 接入

* 依赖

```
implementation 'com.fox.one:passport:0.2.4'
```
查看[最新版本](http://jcenter.bintray.com/com/fox/one/passport)

## 功能说明

* [PassportAPI](src/main/java/com/fox/one/passport/core/PassportAPI.kt)

```
//请求图片验证码
requestCaptcha

//获取图片验证码的图片url地址
getCaptchaUrl

//请求注册用的验证码(短信验证码&邮箱验证码)
requestValidCodeOfRegister

//注册(手机&邮箱)
register

//登录(手机&邮箱)
login

//请求找回密码的验证码(短信验证码&邮箱验证码)
requestValidCodeOfResetPassword

//获取用户信息
getAccountInfo

//获取短信登录的短信验证码
requestCodeOfSMSLogin

//短信登录
loginWithSMSCode

//登出
logout

//修改密码
modifyPassword

//重置密码
resetPhonePassword

//第一次申请并创建KYC用户认证资料
createKYCProfile

//当用户已申请用户认证资料时，调用该接口去更新用户认证资料
updateKYCProfile

//获取用户认证信息(资料)
getKYCProfile

//签名接口，用于业务接口的请求签名
sign

```

* [文件上传API](src/main/java/com/fox/one/passport/core/UploadAPI.kt) 目前用于KYC用户证件照上传

```
UploadAPI.uploadFile(UploadAPI.createUploadReqBody(File))

```

* 注意：当登录成功后，需要把登录后的账号信息赋值给`PassportAPI.accountInfo`。

## 关于JSBridge调用原生接口生成JWT Token说明

* API: `PassportAPI.sign`

* 参数说明：

```

/**
     * 生成jwt签名
     * @param method 请求的方法，e.g., GET, POST, PUT, DELETE....
     * @param url 请求的url, e.g., https://dev-cloud.fox.one/api/account/detail
     * @param timeInSecond 当前时间，以秒为单位
     * @param nonce 唯一随机字串，以UUID生成
     * @param bodyString 如果请求有Body，把body转成json字符串（body一般都是json对象）
     * @return [SignResult] 返回新的url及签名(sign)，后续请求使用新的url请求[SignResult.newUrl]，在请求的header加上jwt token签名[SignResult.sign]，e.g., Authorization:Bearer 12334ea234323534
     */
    fun sign(method: String, url: String, timeInSecond: Long, nonce: String, bodyString: String): SignResult
    
```