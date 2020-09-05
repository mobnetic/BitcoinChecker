package com.aneonex.bitcoinchecker.tester.volley.generic;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.aneonex.bitcoinchecker.tester.volley.CheckerErrorParsedError;
import com.aneonex.bitcoinchecker.tester.volley.UnknownVolleyError;

public abstract class GzipVolleyRequest<T> extends Request<T> {

	private final Listener<T> listener;
	private final ErrorListener errorListener;
	private final Map<String, String> headers;
	
	private RequestQueue requestQueue;
	private String redirectionUrl = null;
	private int redirectionCount;
	
	private Map<String, String> requestHeaders;
	private NetworkResponse networkResponse;
	private String responseString;
	
	private final static int MAX_REDIRECTION_COUNT = 3;
	
	public GzipVolleyRequest(String url, Listener<T> listener, ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		
		this.listener = listener;
		this.errorListener = errorListener;
		
		this.headers = new HashMap<String, String>();
		this.headers.put("Accept-Encoding", "gzip");
		this.headers.put("User-Agent", "Bitcoin Checker (gzip)");
	}
	
	public RequestQueue getRequestQueue() {
		return requestQueue;
	}
	
	@Override
	public String getUrl() {
		if(redirectionUrl!=null)
			return redirectionUrl;
		return super.getUrl();
	}
	
	@Override
    public Map<String, String> getHeaders() throws AuthFailureError {
		requestHeaders = headers!=null ? headers : super.getHeaders();
		return requestHeaders;
    }
	
	@Override
	public Request<?> setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
		return super.setRequestQueue(requestQueue);
	}

	@Override
	public void deliverError(VolleyError error) {
		if(error!=null && error.networkResponse!=null) {
			final int statusCode = error.networkResponse.statusCode;
			if(statusCode==HttpURLConnection.HTTP_MOVED_PERM || statusCode==HttpURLConnection.HTTP_MOVED_TEMP) {
				String location = error.networkResponse.headers.get("Location");
				if(location!=null && redirectionCount<MAX_REDIRECTION_COUNT) {
					++redirectionCount;
					redirectionUrl = location;
					requestQueue.add(this);
					return;
				}
			}
		}
		if(errorListener instanceof ResponseErrorListener)
			((ResponseErrorListener)errorListener).onErrorResponse(getUrl(), requestHeaders, networkResponse, responseString, error);
		else
			super.deliverError(error);
	}
	
	@Override
	protected void deliverResponse(final T response) {
		if(listener instanceof ResponseListener)
			((ResponseListener<T>)listener).onResponse(getUrl(), requestHeaders, networkResponse, responseString, response);
		else
			listener.onResponse(response);
	}
	
	protected abstract T parseNetworkResponse(Map<String, String> headers, String responseString) throws Exception;
	
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			networkResponse = response;
			String responseString = "";
			final String encoding = response.headers.get("Content-Encoding");
            if(encoding!=null && encoding.contains("gzip")) {
                responseString = decodeGZip(response.data);
            } else {
                responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            }
            this.responseString = responseString;
            final Map<String, String> headers = response.headers;
            final Entry cacheHeaders = HttpHeaderParser.parseCacheHeaders(response);
            response = null;
			return Response.success(parseNetworkResponse(headers, responseString), cacheHeaders);
		} catch (CheckerErrorParsedError checkerErrorParsedError) {
			return Response.error(checkerErrorParsedError);
		} catch (Exception e) {
			return Response.error(new ParseError(e));
		} catch (Throwable e) {
			return Response.error(new UnknownVolleyError(e));
		}
	}
	
	private String decodeGZip(byte[] data) throws Exception {
        String responseString = "";

        ByteArrayInputStream bais = null;
        GZIPInputStream gzis = null;
        InputStreamReader reader = null;
        BufferedReader in = null;

        try {
            bais = new ByteArrayInputStream(data);
            gzis = new GZIPInputStream(bais);
            reader = new InputStreamReader(gzis);
            in = new BufferedReader(reader);

            String readed;
            while ((readed = in.readLine()) != null) {
                responseString += readed+"\n";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if(bais!=null)
                    bais.close();
                if(gzis!=null)
                    gzis.close();
                if(reader!=null)
                    reader.close();
                if(in!=null)
                    in.close();
            } catch (Exception e) {};
        }

        return responseString;
    }
}
