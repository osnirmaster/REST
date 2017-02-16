package com.mkyong.client;


import javax.net.ssl.*;

import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


/**
 * Class neccessary for our https communication connections.
 * Will provide tools, and set defaults, so as to skip the certificate
 * verification part of SSL communication. ( Server currently does not
 * use a validated certificate. )
 * As inspired by: https://code.google.com/p/misc-utils/wiki/JavaHttpsUrl
 *
 * Alternative solution would be to download and add the cert manually,
 * using *keytool*.
 */
public class SSLTool {

    private static SSLContext sslContext;

    private static X509HostnameVerifier hostnameVerifier;
    
    
    /**
     * Disables all SSL certificate validation.
     * @return 
     */
    public static Client disableCertificateValidation() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs,
                    String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs,
                    String authType) {
            }
        } };

        hostnameVerifier = new X509HostnameVerifier() {

            @Override
            public void verify(String arg0, SSLSocket arg1) throws IOException {
                // TODO Auto-generated method stub

            }
            


            @Override
            public void verify(String arg0, X509Certificate arg1)
                    throws SSLException {
                // TODO Auto-generated method stub

            }

            @Override
            public void verify(String arg0, String[] arg1, String[] arg2)
                    throws SSLException {

            }

            @Override
            public boolean verify(String hostname, SSLSession session) {
                // TODO Auto-generated method stub
                return true;
            }
        };
        Client client;
        // Install the all-trusting trust manager
		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String str, SSLSession sslSession) {
				return true;
			}
		};
        try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
			client = ClientBuilder.newBuilder().hostnameVerifier(hv).sslContext(sc).build();
		} catch (Exception ex) {
			client = ClientBuilder.newClient();
		}

		return client;
    }
    
    

    public static SSLContext getSslContext() {
        return sslContext;
    }
    
    public static X509HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }
}