package com.aneonex.bitcoinchecker.tester.util

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object HttpsHelper {
    fun newRequestQueue(context: Context?): RequestQueue {
        return Volley.newRequestQueue(context, HurlStack(null, mySSLSocketFactory))
    }

    private val mySSLSocketFactory: SSLSocketFactory?
        get() = try {
            val sslContext = SSLContext.getInstance("TLS")
            val tm: TrustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate>? {
                    return null
                }
            }
            sslContext.init(null, arrayOf(tm), null)
            sslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
}