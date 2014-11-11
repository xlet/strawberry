package cn.w.im.thirdparty.wcn.sdk;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码MD5加密（两次）
 *
 * @author Alon Cai
 */
public class MD5Util {
    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        }
        return message;
    }

    public static String twiceMd5(String str) {
        return md5Hex(md5Hex(str));
    }
}
