package cn.khthink.easyapi.kit;

import cn.khthink.easyapi.tools.Md5Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Token组件
 *
 * @author kh
 */
public class EasyTokenKit {
    /**
     * Token存放同步list
     */
    private final List<String> tokenLists = Collections.synchronizedList(new ArrayList<>());

    private static class EasyToken {
        private static EasyTokenKit easySessionKit = new EasyTokenKit();
    }

    private EasyTokenKit() {
    }

    public static EasyTokenKit getInstance() {
        return EasyToken.easySessionKit;
    }

    /**
     * 消费token
     *
     * @param token
     * @return
     */
    public synchronized boolean userToken(String token) {
        synchronized (tokenLists) {
            if (tokenLists.contains(token)) {
                tokenLists.remove(token);
                return true;
            }
            return false;
        }
    }

    /**
     * 创建一个Token
     *
     * @return
     */
    public synchronized String createToken() {
        String token;
        synchronized (tokenLists) {
            token = Md5Util.md5LowerCase(System.currentTimeMillis() + "");
            tokenLists.add(token);
        }
        return token;
    }

}
