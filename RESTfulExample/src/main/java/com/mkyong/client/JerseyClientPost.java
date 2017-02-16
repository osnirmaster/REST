package com.mkyong.client;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class JerseyClientPost {

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {
		
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

			Builder webResource = client
					.resource("https://homologacao.brscan.com.br/brsafe-rest/v1/protocolo/consultar")
					.header("user", "bradesco@bradesco.com.br")
					.header("password", "bradesco@123");

			
			String input = "{\"cod_protocolo\":\"26811\"}";

			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class, input);

			if (response.getStatus() != 200) {
				System.out.println(response.getEntity(String.class));
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
