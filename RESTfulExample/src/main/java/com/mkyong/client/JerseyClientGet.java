package com.mkyong.client;

import javax.net.ssl.X509TrustManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class JerseyClientGet {

	public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
	    final X509TrustManager cert = new X509TrustManager() {
	        public X509Certificate[] getAcceptedIssuers() {
	          return null;
	        }
	         
	        public void checkServerTrusted(X509Certificate[] certs,
	          String authType)
	        throws java.security.cert.CertificateException {
	          return;
	        }
	         
	        public void checkClientTrusted(X509Certificate[] certs,
	          String authType)
	        throws java.security.cert.CertificateException {
	          return;
	        }
	      };
	   
	      //cria socket ssl
	      SSLContext sc = SSLContext.getInstance("SSL");
	      sc.init(null, new TrustManager[] { cert }, null);
	   
	      //ativa o socket para a requisicao
	      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	       
	      final HostnameVerifier hv = new HostnameVerifier() {
	        public boolean verify(String urlHostName, SSLSession session) {
	          return true;
	        }
	      };
	   
	      HttpsURLConnection.setDefaultHostnameVerifier(hv);
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("https:\\localhost");

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}