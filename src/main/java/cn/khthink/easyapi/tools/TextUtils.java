package cn.khthink.easyapi.tools;


/**
 * 字符处理工具
 *
 * @author kh
 */
public class TextUtils {

    /**
     * 判断是否为空
     *
     * @param charSequence 传入的字符串
     * @return boolean
     */
    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        return "".contentEquals(charSequence);
    }
}
