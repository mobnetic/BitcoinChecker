package com.aneonex.bitcoinchecker.tester.volley.generic

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.aneonex.bitcoinchecker.datamodule.model.PostRequestInfo
import com.aneonex.bitcoinchecker.tester.volley.CheckerErrorParsedError
import com.aneonex.bitcoinchecker.tester.volley.UnknownVolleyError
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.zip.GZIPInputStream

abstract class GzipVolleyRequest<T>(url: String?, private val postRequestInfo: PostRequestInfo?, private val listener: Response.Listener<T>, errorListener: Response.ErrorListener)
    : Request<T>(if (postRequestInfo == null) Method.GET else Method.POST, url, errorListener) {

    companion object {
        private const val MAX_REDIRECTION_COUNT = 3

        /** Default charset for JSON request.  */
        private val PROTOCOL_CHARSET = Charsets.UTF_8
    }

    private val initialErrorListener = errorListener
    private val headers: Map<String, String>
    var requestQueue: RequestQueue? = null
        private set
    private var redirectionUrl: String? = null
    private var redirectionCount = 0
    private var requestHeaders: Map<String, String>? = null
    private var networkResponse: NetworkResponse? = null
    private var responseString: String? = null

    init {
        if(method == Method.POST && postRequestInfo == null) throw IllegalArgumentException("Invalid arguments: postRequestInfo cannot be null for POST method")

        headers = mutableMapOf (
                "Accept-Encoding" to "gzip",
                "User-Agent" to "Bitcoin Checker (gzip)"
        )

        postRequestInfo?.headers?.let {
            for (item in it){
                headers[item.key] = item.value
            }
        }
    }

    override fun getUrl(): String {
        return if (redirectionUrl != null) redirectionUrl.toString() else super.getUrl()
    }

    override fun getBodyContentType(): String {
        return if(postRequestInfo != null) "application/json; charset=$PROTOCOL_CHARSET" else super.getBodyContentType()
    }

    override fun getBody(): ByteArray {
        return if(postRequestInfo != null) return postRequestInfo.body.toByteArray(PROTOCOL_CHARSET) else super.getBody()
    }

    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        requestHeaders = headers //: super.getHeaders()
        return requestHeaders!!
    }

    override fun setRequestQueue(requestQueue: RequestQueue): Request<*> {
        this.requestQueue = requestQueue
        return super.setRequestQueue(requestQueue)
    }

    override fun deliverError(error: VolleyError) {
        if (error.networkResponse != null) {
            val statusCode = error.networkResponse.statusCode
            if (statusCode == HttpURLConnection.HTTP_MOVED_PERM || statusCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                val location = error.networkResponse.headers["Location"]
                if (location != null && redirectionCount < MAX_REDIRECTION_COUNT) {
                    ++redirectionCount
                    redirectionUrl = location
                    requestQueue!!.add(this)
                    return
                }
            }
        }
        if (initialErrorListener is ResponseErrorListener)
            initialErrorListener.onErrorResponse(url, requestHeaders, networkResponse, responseString, error)
            else super.deliverError(error)
    }

    override fun deliverResponse(response: T) {
        if (listener is ResponseListener<*>) (listener as ResponseListener<T>).onResponse(url, requestHeaders, networkResponse, responseString, response) else listener.onResponse(response)
    }

    @Throws(Exception::class)
    protected abstract fun parseNetworkResponse(headers: Map<String?, String?>?, responseString: String?): T?

    override fun parseNetworkResponse(response: NetworkResponse): Response<T> {
//        var response: NetworkResponse? = response
        return try {
            networkResponse = response
            val encoding = response.headers["Content-Encoding"]
            val responseString = if (encoding != null && encoding.contains("gzip")) {
                decodeGZip(response.data)
            } else {
                String(response.data, charset(HttpHeaderParser.parseCharset(response.headers)))
            }
            this.responseString = responseString
            val headers = response.headers
            val cacheHeaders = HttpHeaderParser.parseCacheHeaders(response)
//            response = null
            Response.success(parseNetworkResponse(headers, responseString), cacheHeaders)
        } catch (checkerErrorParsedError: CheckerErrorParsedError) {
            Response.error(checkerErrorParsedError)
        } catch (e: Exception) {
            Response.error(ParseError(e))
        } catch (e: Throwable) {
            Response.error(UnknownVolleyError(e))
        }
    }

    @Throws(Exception::class)
    private fun decodeGZip(data: ByteArray): String {
        var responseString = ""
        var bais: ByteArrayInputStream? = null
        var gzis: GZIPInputStream? = null
        var reader: InputStreamReader? = null
        var bufReader: BufferedReader? = null
        try {
            bais = ByteArrayInputStream(data)
            gzis = GZIPInputStream(bais)
            reader = InputStreamReader(gzis)
            bufReader = BufferedReader(reader)
            var readed: String?
            while (bufReader.readLine().also { readed = it } != null) {
                responseString += """
                    $readed

                    """.trimIndent()
            }
        } catch (e: Exception) {
            throw e
        } finally {
            try {
                bais?.close()
                gzis?.close()
                reader?.close()
                bufReader?.close()
            } catch (e: Exception) {
            }
        }
        return responseString
    }
}