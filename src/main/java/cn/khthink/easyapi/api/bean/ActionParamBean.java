package cn.khthink.easyapi.api.bean;

/*
    Create by KH at 2017/11/22 13:48 
 */

import cn.khthink.easyapi.api.ParamType;
import lombok.Data;

/**
 * 处理器参数
 *
 * @author kh
 */
@Data
public class ActionParamBean {
    /**
     * 参数名
     */
    private String param;

    /**
     * 参数类型
     */
    private ParamType type;

    /**
     * 参数描述
     */
    private String paramDesc;

    /**
     * 参数例子
     */
    private String paramExmple;

    /**
     * 参数默认值
     */
    private String defaultValue;

    /**
     * 参数是否必须
     */
    private boolean isNeed;

    public ActionParamBean(cn.khthink.easyapi.annotation.action.ActionParam actionParam) {
        this.param = actionParam.param();
        this.paramDesc = actionParam.paramDesc();
        this.paramExmple = actionParam.paramExmple();
        this.type = actionParam.type();
        this.defaultValue = actionParam.defaultValue();
        this.isNeed = actionParam.isNeed();
    }

}
