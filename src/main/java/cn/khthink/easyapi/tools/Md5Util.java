package cn.khthink.easyapi.tools;

import cn.khthink.easyapi.kit.EasyLogger;

import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5加密工具
 *
 * @author kh
 */
public class Md5Util {

    /**
     * 返回md5加密字符串(大小写)
     *
     * @param s 字符串
     * @return
     */
    public static String md5(String s) {
        return md5(s.getBytes());
    }

    /**
     * 返回小写MD5加密字符串
     *
     * @param s 字符串
     * @return
     */
    public static String md5LowerCase(String s) {
        return md5(s).toLowerCase();
    }


    /**
     * 返回md5加密字符串(大小写)
     *
     * @param btInput 字节
     * @return
     */
    public static String md5(byte[] btInput) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            EasyLogger.info(e);
            return null;
        }
    }

    /***
     * 随机生成n位数短信验证码(纯数字)
     * @param n 位数
     * @return
     */
    public static String getRandomTelVerify(int n) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            int number = random.nextInt(10);
            sb.append(number);
        }
        return sb.toString();
    }

    /***
     * 随机生成n位数验证码(a-z A-Z 0-9)
     * @param n 位数
     * @return
     */
    public static String getRandomImgVerify(int n) {
        String base = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
