package com.test;

import java.io.IOException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class NewPortlet
 */
public class NewPortlet extends MVCPortlet {
 
    public static void main(String[] args) throws ClientProtocolException, IOException {
        RequestConfig rConfig = RequestConfig.custom()
                .setConnectTimeout(100000)
        .setConnectionRequestTimeout(100000)
        .setSocketTimeout(100000)
        .build();
    javax.net.ssl.SSLContext sslContext = SSLContexts.createDefault();
    //The hub only allows TLSv1.1 and TLSv1.2 protocols
    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        sslContext,
        new String[] { "TLSv1.1" },
        null,
        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(rConfig)
                .setSSLSocketFactory(sslsf)
                .build();
    HttpPost post= new HttpPost("https://stagingtoken-service.2020ar.com/authorize/token");
    post.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYzgxMDg2Zi00ZTMzLTRkNGUtOTEzNC01Mjg3MmEyMmY0MTkiLCJ0ZW5hbnQiOnsiaWQiOiI5NzcwOWQxNS1lNzRhLTRmZDctODJlOC1hNTk2NTI4ZGJlMTMiLCJhbGlhcyI6Im5pYyJ9LCJleHAiOjEyMzUzMDkwNzg4NTUzLCJpYXQiOjE0NDEwNDA1NTB9.AqpeAIu8nAqdXxsp1AHvx6YApFZkMBn2g33uUgDnqXo");
    // add request header
    HttpResponse response = httpClient.execute(post);
    System.out.println("Status>>>"+response.getStatusLine());

    System.out.println("response>>>>>>"+EntityUtils.toString(response.getEntity()));

    ClassLoader classLoader = HubApiCall.class.getClassLoader();
    URL resource = classLoader.getResource("org/apache/http/message/BasicLineFormatter.class");
    System.out.println(resource);
    }
}
