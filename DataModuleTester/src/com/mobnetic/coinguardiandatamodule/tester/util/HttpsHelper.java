package com.mobnetic.coinguardiandatamodule.tester.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpsHelper {

	public static SSLSocketFactory getMySSLSocketFactory() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
	        };
	        sslContext.init(null, new TrustManager[] { tm }, null);
	        return sslContext.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
