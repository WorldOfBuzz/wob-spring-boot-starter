package com.ziffit.autoconfigure.spring.web;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class UnsafeHttpRequestFactory extends SimpleClientHttpRequestFactory {

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        try {
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection).setHostnameVerifier((x, y) -> true);
                ((HttpsURLConnection) connection).setSSLSocketFactory(buildUnsafeSSLContext().getSocketFactory());
                connection.setAllowUserInteraction(true);
            }
            super.prepareConnection(connection, httpMethod);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IOException(e.getMessage());
        }
    }

    private SSLContext buildUnsafeSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext unsafeSSLContext = SSLContext.getInstance("TLS");
        X509ExtendedTrustManager unsafeTrustManager = new X509ExtendedTrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        unsafeSSLContext.init(null, new TrustManager[]{unsafeTrustManager}, null);
        SSLContext.setDefault(unsafeSSLContext);
        return unsafeSSLContext;
    }
}
