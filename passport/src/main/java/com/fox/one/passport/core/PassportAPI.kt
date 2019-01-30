package com.fox.one.passport.core

import com.fox.one.passport.core.model.*
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.common.utils.SecurityUtils
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import com.fox.one.support.passport.core.IPassportAPI
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.io.Serializable
import java.net.URL

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
object PassportAPI: IPassportAPI, IKYCAPI {
    const val ALPHA_URL = "https://dev-cloud.fox.one"
    const val BETA_URL = "https://cloudapi.fox.one"
    const val RELEASE_URL = "https://cloudapi.fox.one"

    var accountInfo: AccountInfo = AccountInfo()
    var apiLoader = APILoader()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiLoader.setOkHttp(okHttpClient)
        apiLoader.setBaseUri(APILoader.BaseUrl(ALPHA_URL, BETA_URL, RELEASE_URL))
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

        val newHttpUrl = httpUrlBuilder?.build()
        val newUrl = newHttpUrl?.url().toString()
        val host = if (newHttpUrl?.isHttps == true) "https://${newHttpUrl?.host()}"
            else "http://${newHttpUrl?.host()}"

        val urlExcludeHost = newUrl.substring(host.length)

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

    override fun requestValidCodeOfRegister(request: SMSCodeReqBody): FoxCall<ValidCodeResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestValidCodeOfRegister(request)
    }

    override fun requestValidCodeOfRegister(request: EmailCodeReqBody): FoxCall<ValidCodeResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestValidCodeOfRegister(request)
    }

    override fun register(request: RegisterReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .register(request)
    }

    override fun login(request: LoginWithPhoneReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .login(request)
    }

    override fun login(request: LoginWithEmailReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .login(request)
    }

    override fun requestValidCodeOfResetPassword(request: SMSCodeReqBody): FoxCall<ValidCodeResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestValidCodeOfResetPassword(request)
    }

    override fun requestValidCodeOfResetPassword(request: EmailCodeReqBody): FoxCall<ValidCodeResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestValidCodeOfResetPassword(request)
    }

    override fun getAccountInfo(): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .getAccountInfo()
    }

    override fun requestCaptcha(): FoxCall<CaptchaInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestCaptcha()
    }

    override fun loginWithSMSCode(request: LoginWithSMSReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .loginWithSMSCode(request)
    }

    override fun requestCodeOfSMSLogin(request: SMSCodeReqBody): FoxCall<ValidCodeResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestCodeOfSMSLogin(request)
    }

    override fun logout(): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .logout()
    }

    override fun modifyPassword(request: ModifyPasswordReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .modifyPassword(request)
    }

    override fun resetPassword(request: ResetPasswordReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .resetPassword(request)
    }

    override fun createKYCProfile(request: KYCProfileReqBody): FoxCall<KYCStatusResponse> {
        return apiLoader.load(IKYCAPI::class.java)
            .createKYCProfile(request)
    }

    override fun updateKYCProfile(request: KYCProfileReqBody): FoxCall<KYCStatusResponse> {
        return apiLoader.load(IKYCAPI::class.java)
            .updateKYCProfile(request)
    }

    override fun getKYCProfile(): FoxCall<KYCProfileResponse> {
        return apiLoader.load(IKYCAPI::class.java)
            .getKYCProfile()
    }

    data class SignResult(var newUrl: String, var sign: String): Serializable {

    }
}