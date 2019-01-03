package cn.newcapec.city.smart.common.utils.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 */
public final class MD5Util {

    private final static String algorithm = System.getProperty("MD5.algorithm", "MD5");

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    //region 字节转换

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String bytesToHex(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (byte aB : b) {
            resultSb.append(byteToHex(aB));
        }
        return resultSb.toString();
    }

    private static String byteToHex(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    //endregion

    //region MD5加密

    //进行16位或32位MD5加密
    private static String md5(String data, int bit) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] datasource = data.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance(algorithm);
        if (bit == 16) {
            return bytesToHex(md.digest(datasource)).substring(8, 24);
        } else {
            return bytesToHex(md.digest(datasource));
        }
    }

    /**
     * 32位MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String md5(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return md5(data, 32);
    }

    /**
     * 16位MD5加密
     *
     * @param data
     * @return
     */
    public static String md5_16(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return md5(data, 16);
    }

    /**
     * 进行3次MD5加密
     *
     * @param data
     * @return
     */
    public static String md5_3(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] datasource = data.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] a = md.digest(datasource);
        a = md.digest(a);
        a = md.digest(a);
        return bytesToHex(a);
    }

    //endregion

    //region sha256摘要算法

    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param data 要加密的数据
     * @return
     */
    public static String sha256(String data) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(data.getBytes("UTF-8"));
        return bytesToHex(messageDigest.digest());
    }

    /**
     * HmacSHA256签名算法
     *
     * @param message 要签名的数据
     * @param secret  密钥
     * @return
     * @throws Exception
     */
    public static String hmacSha256(String message, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] key = secret.getBytes("UTF-8");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        byte[] data = message.getBytes("UTF-8");
        return bytesToHex(mac.doFinal(data));
    }

    //endregion

}
