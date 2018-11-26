package cn.khthink.easyapi.api.bean;

/*
    Create by KH at 2017/10/25 13:18 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.annotation.config.cache.ActionCache;
import cn.khthink.easyapi.bean.UriInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 处理器信息实体类
 *
 * @author kh
 */
@Data
@AllArgsConstructor
public class ActionBean {
    private String actionname;
    private UriInfo info;
    private String desc;
    private String auther;
    private Group group;
    private ActionParamBean[] actionParam;
    private ActionParamBean[] returnParam;
    private ActionCache cache;
    private boolean isSessionOpen;
    private boolean isToken;


}
