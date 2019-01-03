package cn.newcapec.city.smart.common.utils.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by es on 2016/8/8.
 */
public class MyX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        Log.info("cert: " + chain[0].toString() + ", authType: " + authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
