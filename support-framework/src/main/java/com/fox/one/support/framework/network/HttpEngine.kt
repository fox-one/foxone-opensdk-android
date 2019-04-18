package com.fox.one.support.framework.network

import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.Util.assertionError
import okhttp3.internal.platform.Platform
import okhttp3.internal.tls.OkHostnameVerifier
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-07-13
 */
class HttpEngine {

    var okHttpClient: OkHttpClient = OkHttpClient()
    private var isDebugable = false

    fun debugable(enable: Boolean) {
        okHttpClient = if (enable) {
            if (isDebugable) {
                okHttpClient
            } else {
                okHttpClient.newBuilder()
                    .sslSocketFactory(getDefaultSSLSocketFactory())
                    .hostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER)
                    .build()
            }
        } else {
            if (isDebugable) {
                okHttpClient.newBuilder()
                    .sslSocketFactory(systemDefaultSslSocketFactory(systemDefaultTrustManager()))
                    .hostnameVerifier(DEFAULT_HOSTNAME_VERIFIER)
                    .build()
            } else {
                okHttpClient
            }
        }

        isDebugable = enable
    }

    companion object {
        const val HEADER_KEY_AUTHORIZATION = "Authorization"
        const val HEADER_KEY_IF_MODIFIED_SINCE = "If-Modified-Since"
        const val HEADER_KEY_ACCEPT_LANGUAGE = "Accept-Language"
        const val HEADER_KEY_CONNECTION = "Connection"

        const val HEADER_VALUE_AUTHORIZATION_PREFIX = "Bearer "
        const val HEADER_VALUE_CONNECTION_CLOSE = "close"

        /**
         * allow all hostname verifier
         */
        val ALLOW_ALL_HOSTNAME_VERIFIER = HostnameVerifier { _, _ -> true }
        /**
         * default hostname verifier
         */
        val DEFAULT_HOSTNAME_VERIFIER = OkHostnameVerifier.INSTANCE

        fun getDefaultSSLSocketFactory(): SSLSocketFactory {
            val sslContext = SSLContext.getInstance("SSL").apply {
                this.init(null, arrayOf(object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }), java.security.SecureRandom())
            }

            return sslContext.socketFactory
        }

        fun systemDefaultTrustManager(): X509TrustManager {
            try {
                val trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm()
                )
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers = trustManagerFactory.trustManagers
                if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                    throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
                }
                return trustManagers[0] as X509TrustManager
            } catch (e: GeneralSecurityException) {
                throw assertionError("No System TLS", e) // The system has no TLS. Just give up.
            }

        }

        fun systemDefaultSslSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
            try {
                val sslContext = Platform.get().sslContext
                sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
                return sslContext.socketFactory
            } catch (e: GeneralSecurityException) {
                throw assertionError("No System TLS", e) // The system has no TLS. Just give up.
            }

        }

        var defaultInterceptor: Interceptor = Interceptor {
            return@Interceptor it.proceed(it.request())
        }

        private var defaultEngine :HttpEngine = HttpEngine()

        fun getDefaultEngine(): HttpEngine {
            if (defaultEngine.okHttpClient.interceptors().isEmpty()) {
                defaultEngine.okHttpClient = defaultEngine.okHttpClient.newBuilder().addInterceptor(defaultInterceptor).build()
            }

            return defaultEngine
        }
    }
}