package com.ziffit.autoconfigure.spring.web;

import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class UnsafeHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    private static final Logger logger = LogManager.getLogger();

    private HttpClient unsafeHttpClient;

    {
        try {
            SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true)
                .build();

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

            Collection<Header> headers = Lists.newArrayList(new BasicHeader("Connection", "close"));

            this.unsafeHttpClient = HttpClientBuilder
                .create()
                .setDefaultHeaders(headers)
                .setSSLSocketFactory(socketFactory)
                .setMaxConnPerRoute(5000)
                .setMaxConnTotal(7000)
                .disableCookieManagement()
                .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            logger.error(e.getMessage());
        }
    }

    public UnsafeHttpRequestFactory() {
        super();
        super.setHttpClient(unsafeHttpClient);
    }
}
