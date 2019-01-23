package com.fox.one.support.passport.core

import com.fox.one.support.common.extension.closeSilently
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.common.utils.SecurityUtils
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import com.fox.one.support.passport.core.models.*
import com.foxone.exchange.account.core.model.KYCProfileInfo
import com.foxone.exchange.account.core.model.KYCProfileReqBody
import com.foxone.exchange.account.core.model.KYCStatusResponse
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okio.Buffer
import java.io.Serializable
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
object PassportAPI: IPassportAPI, IKYCAPI {
    private const val HOST = "https://dev-cloud.fox.one"
    const val HEADER_MERCHANT_ID = "fox-cloud-merchant-id"
    private const val MERCHANT_ID = "5c8a9491dca25af694004d5e1711b217"
    private val apiLoader = APILoader()
    var accountInfo: AccountInfo = AccountInfo()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val newRequestBuilder = it.request().newBuilder()
                newRequestBuilder.addHeader(HEADER_MERCHANT_ID, MERCHANT_ID)

                if (isLogin()) {
                    //sign
                    val timeInSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
                    val nonce = UUID.randomUUID().toString()

                    val sink = Buffer()
                    request.body()?.writeTo(sink)
                    val bodyString = sink.readString(Charset.defaultCharset())
                    sink.closeSilently()

                    val signResult = PassportAPI.sign(request.method(), request.url().url().toString(), timeInSecond, nonce, bodyString)

                    newRequestBuilder.header(HttpEngine.HEADER_KEY_AUTHORIZATION, HttpEngine.HEADER_VALUE_AUTHORIZATION_PREFIX + signResult.sign)
                    newRequestBuilder.url(signResult.newUrl)
                }

                return@addInterceptor it.proceed(newRequestBuilder.build())
            }.build()

        apiLoader.setOkHttp(okHttpClient)
        apiLoader.setBaseUri(APILoader.BaseUrl(HOST, HOST, HOST))
    }

    fun isLogin(): Boolean {
        return accountInfo.user.id > 0
    }

    fun sign(method: String, url: String, timeInSecond: Long, nonce: String, bodyString: String): SignResult {
        //sign

        LogUtils.i("foxone", "method:$method, url:$url, time: $timeInSecond, nonce:$nonce, body: $bodyString")
        val httpUrlBuilder = HttpUrl.get(URL(url))?.newBuilder()
        httpUrlBuilder?.addQueryParameter("_ts", "$timeInSecond")
            ?.addQueryParameter("_nonce", nonce)

        val newUrl = httpUrlBuilder?.build()?.url().toString()

        val urlExcludeHost = newUrl.substring(apiLoader.getBaseUrl().length)

        val toSignMessage = StringBuilder(method).append(urlExcludeHost).append(bodyString).toString()
        val sign = SecurityUtils.SHA256.encryptWithBase64(toSignMessage)
        LogUtils.i("foxone", "toSign: $toSignMessage,  secret: ${accountInfo.session.secret}")
        LogUtils.i("foxone", "sign: $sign")

        val token = Jwts.builder().setHeaderParam("alg", "HS256")
            .setHeaderParam("typ", "JWT")
            .claim("exp", accountInfo.session.expired)
            .claim("key", accountInfo.session.key)
            .claim("sign", sign)
            .signWith(Keys.hmacShaKeyFor(accountInfo.session.secret.toByteArray()))
            .compact()

        LogUtils.i("foxone", "newUrl: $newUrl")
        LogUtils.i("foxone", "jwtToken: $token")

        return SignResult(newUrl=newUrl, sign = token)
    }

    fun getCaptchaUrl(captchaId: String): String {
        return "${apiLoader.getBaseUrl()}/api/captcha/$captchaId.png"
    }

    override fun getAccountInfo(): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .getAccountInfo()
    }

    override fun requestCaptcha(): FoxCall<CaptchaInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestCaptcha()
    }

    override fun requestSMSCodeOfPhoneRegister(request: SMSCodeReqBody): FoxCall<SMSCodeResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestSMSCodeOfPhoneRegister(request)
    }

    override fun registerWithPhone(request: PhoneRegisterReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .registerWithPhone(request)
    }

    override fun loginWithPhone(request: LoginWithPhoneReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .loginWithPhone(request)
    }

    override fun loginWithSMSCode(request: LoginWithSMSReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .loginWithSMSCode(request)
    }

    override fun requestCodeOfSMSLogin(request: SMSCodeReqBody): FoxCall<SMSCodeResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestCodeOfSMSLogin(request)
    }

    override fun logout(): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .logout()
    }

    override fun requestModifyPassword(request: SMSCodeReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestModifyPassword(request)
    }

    override fun modifyPassword(request: ModifyPasswordReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .modifyPassword(request)
    }

    override fun requestSMSCodeOfResetPhonePassword(request: SMSCodeReqBody): FoxCall<SMSCodeReqBody> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestSMSCodeOfResetPhonePassword(request)
    }

    override fun resetPhonePassword(request: ResetPhonePasswordReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .resetPhonePassword(request)
    }

    override fun createKYCProfile(request: KYCProfileReqBody): FoxCall<KYCStatusResponse> {
        return apiLoader.load(IKYCAPI::class.java)
            .createKYCProfile(request)
    }

    override fun updateKYCProfile(request: KYCProfileReqBody): FoxCall<KYCStatusResponse> {
        return apiLoader.load(IKYCAPI::class.java)
            .updateKYCProfile(request)
    }

    override fun getKYCProfile(): FoxCall<KYCProfileInfo> {
        return apiLoader.load(IKYCAPI::class.java)
            .getKYCProfile()
    }

    data class SignResult(var newUrl: String, var sign: String): Serializable {

    }
}