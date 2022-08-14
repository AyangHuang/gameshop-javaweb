package utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Util {

    /**
     * 获取md5，长度在32位以下
     */
    public static String getMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] digest = null;
        MessageDigest md5 = MessageDigest.getInstance("md5");
        digest  = md5.digest(str.getBytes(StandardCharsets.UTF_8));
        //16是表示转换为16进制数
        System.out.println(digest);
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }

    /**
     * 通过需要加密的字符串和salt，返回消息摘要
     */
    public static String getMD5BySalt(String dataStr, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String md5Str = dataStr + salt;
        return getMD5(md5Str);
    }

    /**
     * return 指定 length 的随机字符串
     */
    public  static String randomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
