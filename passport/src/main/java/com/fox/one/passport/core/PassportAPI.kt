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

    const val ALPHA_URL = "https://dev-gateway.fox.one"
    const val BETA_URL = "https://openapi.fox.one"
    const val RELEASE_URL = "https://openapi.fox.one"

    var accountInfo: AccountInfo = AccountInfo()
    var apiLoader = APILoader().apply {
        this.setBaseUri(APILoader.BaseUrl(ALPHA_URL, BETA_URL, RELEASE_URL))
    }

    fun isLogin(): Boolean {
        return accountInfo.user.id > 0
    }

    /**
     * 生成jwt签名
     * @param method 请求的方法，e.g., GET, POST, PUT, DELETE....
     * @param url 请求的url, e.g., https://dev-cloud.fox.one/api/account/detail
     * @param timeInSecond 当前时间，以秒为单位
     * @param nonce 唯一随机字串，以UUID生成
     * @param bodyString 如果请求有Body，把body转成json字符串（body一般都是json对象）
     * @return [SignResult] 返回新的url及签名(sign)，后续请求使用新的url请求[SignResult.newUrl]，在请求的header加上jwt token签名[SignResult.sign]，e.g., Authorization:Bearer 12334ea234323534
     */
    fun sign(method: String, url: String, timeInSecond: Long, nonce: String, bodyString: String): SignResult {
        //sign
        try {
            LogUtils.i("foxone", "method:$method, url:$url, time: $timeInSecond, nonce:$nonce, body: $bodyString")
            val httpUrlBuilder = HttpUrl.get(URL(url))?.newBuilder()
            httpUrlBuilder?.addQueryParameter("_ts", "$timeInSecond")
                ?.addQueryParameter("_nonce", nonce)

            val newHttpUrl = httpUrlBuilder?.build()
            val newUrl = newHttpUrl?.url().toString()
            val host = if (newHttpUrl?.isHttps == true) "https://${newHttpUrl.host()}"
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
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return SignResult(url, "")
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

    override fun login(request: LoginWithSMSReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .login(request)
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

    override fun requestTFA(): FoxCall<TFAResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestTFA()
    }

    override fun enableTFA(request: TFAReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .enableTFA(request)
    }

    override fun disableTFA(request: TFAReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .disableTFA(request)
    }

    override fun login(request: TFALoginReqBody): FoxCall<AccountInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .login(request)
    }

    override fun requestLoginSessions(): FoxCall<LoginSessionResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestLoginSessions()
    }

    override fun removeLoginSession(sessionKey: String): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .removeLoginSession(sessionKey)
    }

    override fun createAPIKey(request: CreateAPIKeyReqBody): FoxCall<CreateAPIKeyResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .createAPIKey(request)
    }

    override fun requestAPIKeys(): FoxCall<List<APIKeyInfo>> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestAPIKeys()
    }

    override fun removeAPIKey(key: String): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .removeAPIKey(key)
    }

    override fun updateUserProfile(request: UpdateUserProfileReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .updateUserProfile(request)
    }

    override fun requestUserAuthInfo(): FoxCall<UserAuthInfo> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestUserAuthInfo()
    }

    override fun requestAuthSMSCode(request: AuthReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestAuthSMSCode(request)
    }

    override fun requestAuthEmailCode(request: AuthReqBody): FoxCall<BasePassportResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestAuthEmailCode(request)
    }

    override fun verifyAuth(request: VerifyAuthReqBody): FoxCall<VerifyAuthResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .verifyAuth(request)
    }

    override fun requestWithdrawStatus(): FoxCall<WithdrawStatusResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestWithdrawStatus()
    }

    override fun requestWithdraw(request: RequestWithdrawReqBody): FoxCall<RequestWithdrawResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestWithdraw(request)
    }

    override fun authWithdraw(requestId: Long, request: AuthReqBody): FoxCall<RequestWithdrawResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .authWithdraw(requestId, request)
    }

    override fun requestWithdrawFee(
        assetId: String,
        service: String,
        publicKey: String,
        accountName: String,
        accountTag: String
    ): FoxCall<WithdrawFeeResponse> {
        return apiLoader.load(IPassportAPI::class.java)
            .requestWithdrawFee(assetId, service, publicKey, accountName, accountTag)
    }

    data class SignResult(var newUrl: String, var sign: String): Serializable {

    }
}