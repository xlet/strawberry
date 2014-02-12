package cn.w.im.utils;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-15 上午9:56.
 * Summary: 关于字符串操作的一些扩展
 */
public class StringExtension {

    /**
     * 首字母小写.
     * @param s 需要改变的字符串.
     * @return 改好的字符串.
     */
    public static String toLowerCaseFistOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder().append(Character.toLowerCase(s.charAt(0))).append(s.substring(1))).toString();
        }
    }

    /**
     * 首字母大小.
     * @param s 需要改变的字符串.
     * @return 改变好的字符串.
     */
    public static String toUpperCaseFistOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder().append(Character.toUpperCase(s.charAt(0))).append(s.substring(1))).toString();
        }
    }
}
