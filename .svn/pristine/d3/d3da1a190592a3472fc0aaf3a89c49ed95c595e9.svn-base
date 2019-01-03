package cn.newcapec.city.smart.common.utils.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

/**
 * 3DES加解密算法
 */
public final class DES3Utils {

    //region 3DES加解密算法

    //加密
    private static String encryptDES(String keyStr, String data, String model) throws GeneralSecurityException, IOException {
        byte[] key = new BASE64Decoder().decodeBuffer(keyStr);
        byte[] datasource = data.getBytes("UTF-8");
        DESedeKeySpec desKey = new DESedeKeySpec(key);
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESEDE");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DESEDE" + model);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
        // 现在，获取数据并加密
        byte[] result = cipher.doFinal(datasource);
        return new BASE64Encoder().encode(result);
    }

    //解密
    private static String decryptDES(String keyStr, String data, String model) throws GeneralSecurityException, IOException {
        byte[] key = new BASE64Decoder().decodeBuffer(keyStr);
        byte[] datasource = new BASE64Decoder().decodeBuffer(data);
        DESedeKeySpec desKey = new DESedeKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESEDE");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DESEDE" + model);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
        // 现在，获取数据并解密
        byte[] result = cipher.doFinal(datasource);
        return new String(result, "UTF-8");
    }
    //endregion

    //region 默认3des加解密算法，ECB/PKCS5Padding

    /**
     * 3des加密，默认模式 ECB/PKCS5Padding
     *
     * @param key
     * @param datasource
     * @return
     * @throws GeneralSecurityException
     */
    public static String encryptDES(String keyStr, String data) throws GeneralSecurityException, IOException {
        return encryptDES(keyStr, data, "");
    }

    /**
     * 3des解密，默认模式 ECB/PKCS5Padding
     *
     * @param key
     * @param datasource
     * @return
     * @throws GeneralSecurityException
     */
    public static String decryptDES(String keyStr, String data) throws GeneralSecurityException, IOException {
        return decryptDES(keyStr, data, "");
    }
    //endregion

    //region ECB模式的3DES加解密算法

    /**
     * des加密算法，ECB/PKCS5Padding模式，数据字节必须是8的整数倍
     *
     * @param key
     * @param data 数据字节必须是8的整数倍
     * @return
     * @throws Exception
     */
    public static String encryptECBPKCS5Padding(String keyStr, String data) throws GeneralSecurityException, IOException {
        return encryptDES(keyStr, data, "/ECB/PKCS5Padding");
    }

    /**
     * des解密算法，ECB/PKCS5Padding模式，数据字节必须是8的整数倍
     *
     * @param key
     * @param data 数据字节必须是8的整数倍
     * @return
     * @throws GeneralSecurityException
     * @throws @throws                  Exception
     */
    public static String decryptECBPKCS5Padding(String keyStr, String data) throws GeneralSecurityException, IOException {
        return decryptDES(keyStr, data, "/ECB/PKCS5Padding");
    }

    //endregion

    //region CBC模式的3DES加解密算法

    /**
     * des加密算法，CBC/PKCS5Padding模式
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static String encryptCBCPKCS5Padding(String keyStr, String data) throws GeneralSecurityException, IOException {
        byte[] key = new BASE64Decoder().decodeBuffer(keyStr);
        byte[] datasource = data.getBytes("UTF-8");
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        SecretKey deskey = keyfactory.generateSecret(spec);
        //获得初始向量IV
        byte[] bs = new byte[8];
        System.arraycopy(key, 0, bs, 0, 8);
        IvParameterSpec ips = new IvParameterSpec(bs);
        //初始化加密
        Cipher cipher = Cipher.getInstance("DESEDE/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] result = cipher.doFinal(datasource);
        return new BASE64Encoder().encode(result);
    }

    /**
     * des解密算法，CBC/PKCS5Padding模式
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static String decryptCBCPKCS5Padding(String keyStr, String data) throws GeneralSecurityException, IOException {
        byte[] key = new BASE64Decoder().decodeBuffer(keyStr);
        byte[] datasource = new BASE64Decoder().decodeBuffer(data);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        SecretKey deskey = keyfactory.generateSecret(spec);
        //获得初始向量IV
        byte[] bs = new byte[8];
        System.arraycopy(key, 0, bs, 0, 8);
        IvParameterSpec ips = new IvParameterSpec(bs);
        //初始化加密
        Cipher cipher = Cipher.getInstance("DESEDE/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] result = cipher.doFinal(datasource);
        return new String(result, "UTF-8");
    }

    //endregion

}
