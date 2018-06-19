package cn.khthink.easyapi.kit;

/**
 * 多平台组件
 *
 * @author kh
 */
public interface EasyPlatformKit {

    /**
     * 创建平台密钥
     *
     * @param platformName
     * @param power
     * @return
     */
    boolean createPlatformKey(String platformName, int power);

    /**
     * 删除平台密钥
     *
     * @param platformName
     * @return
     */
    boolean deletePlanformKey(String platformName);

    /**
     * 验证平台密钥
     *
     * @param platformName
     * @param key
     * @return
     */
    boolean verifyPlanformKey(String platformName, String key);
}
