package cn.khthink.easyapi.api.factory;

/*
    Create by KH at 2017/11/22 12:31 
 */

import cn.khthink.easyapi.action.BaseEasyAction;
import cn.khthink.easyapi.annotation.action.Action;
import cn.khthink.easyapi.annotation.action.ActionParam;
import cn.khthink.easyapi.api.bean.ActionBean;
import cn.khthink.easyapi.api.bean.ActionParamBean;
import cn.khthink.easyapi.api.bean.Group;
import cn.khthink.easyapi.bean.UriInfo;

/**
 * action实体工厂类
 *
 * @author kh
 */
public class ActionFactory {
    private static class Factory {
        private static ActionFactory instatnce = new ActionFactory();
    }

    private ActionFactory() {
    }

    public static ActionFactory getInstance() {
        return Factory.instatnce;
    }

    public ActionBean createAction(BaseEasyAction action) {
        return getActionInfo(action);
    }


    /**
     * 获取处理器信息
     *
     * @return
     */
    private ActionBean getActionInfo(BaseEasyAction action) {
        if (action.getClass().getAnnotation(Action.class) == null) {
            return null;
        }
        Action actionInfo = action.getClass().getAnnotation(Action.class);
        if (actionInfo.actionTag() == null || actionInfo.actionName() == null) {
            return null;
        }
        ActionParam[] actionParam = actionInfo.params();
        ActionParam[] returnParam = actionInfo.returnParams();
        ActionParamBean[] actionParams = new ActionParamBean[]{};
        ActionParamBean[] returnparams = new ActionParamBean[]{};
        if (actionParam.length > 0) {
            actionParams = new ActionParamBean[actionParam.length];
            for (int i = 0; i < actionParam.length; i++) {
                actionParams[i] = new ActionParamBean(actionParam[i]);
            }
        }
        if (returnParam.length > 0) {
            returnparams = new ActionParamBean[returnParam.length];
            for (int i = 0; i < returnParam.length; i++) {
                returnparams[i] = new ActionParamBean(returnParam[i]);
            }
        }
        return new ActionBean(actionInfo.actionName(), new UriInfo(actionInfo.actionTag(), actionInfo.actionName()), actionInfo.actionDesc(), actionInfo.Auther(), new Group(actionInfo.group().power(), actionInfo.group().groupName()), actionParams, returnparams, actionInfo.cache(), actionInfo.isSessionOpen(), actionInfo.isToken());
    }
}
