package cn.khthink.easyapi.tools;

/**
 * Created by kh on 2017/7/4.
 */

/**
 * 字符处理工具
 */
public class TextUtils {

    /**
     * 判断是否为空
     *
     * @return
     */
    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null)
            return true;
        if ("".equals(charSequence))
            return true;
        return false;
    }
}
