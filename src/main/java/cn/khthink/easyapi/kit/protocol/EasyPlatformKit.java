package cn.khthink.easyapi.kit.protocol;

/**
 * 多平台组件
 *
 * @author kh
 */
public interface EasyPlatformKit {

    /**
     * 创建平台密钥
     *
     * @param platformName 平台名
     * @param power        权限
     * @return boolean
     */
    boolean createPlatformKey(String platformName, int power);

    /**
     * 删除平台密钥
     *
     * @param platformName 平台名
     * @return boolean
     */
    boolean deletePlanformKey(String platformName);

    /**
     * 验证平台密钥
     *
     * @param platformName 平台名
     * @param key          密钥
     * @return boolean
     */
    boolean verifyPlanformKey(String platformName, String key);
}
