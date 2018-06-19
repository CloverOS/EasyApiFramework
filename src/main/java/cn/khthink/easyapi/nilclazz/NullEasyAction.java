package cn.khthink.easyapi.nilclazz;

/*
    Create by KH at 2017/11/14 22:45 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.action.BaseEasyAction;
import cn.khthink.easyapi.bean.Request;

import java.io.IOException;

import static cn.khthink.easyapi.config.ResultCode.NULLACTION;

/**
 * 空处理器
 *
 * @author kh
 */
import cn.khthink.easyapi.annotation.action.Action;
import cn.khthink.easyapi.annotation.action.ActionGroup;
import cn.khthink.easyapi.annotation.action.ActionParam;

@Action(actionName = "",
        actionTag = "",
        group = @ActionGroup(power = 100, groupName = "null"),
        actionDesc = "",
        params = {@ActionParam(param = "", paramDesc = "", paramExmple = "")},
        returnParams = {@ActionParam(param = "", paramDesc = "", paramExmple = "")})
public class NullEasyAction extends BaseEasyAction {
    @Override
    public void processData(Request request) throws IOException {
        sendEmptyDataMsg(request, "空接口", NULLACTION);
    }
}
