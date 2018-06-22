package cn.khthink.easyapi.action;

/*
    Create by KH at 2017/10/25 13:21 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.annotation.action.Action;
import cn.khthink.easyapi.api.bean.ActionGroup;
import cn.khthink.easyapi.api.factory.ActionFactory;
import cn.khthink.easyapi.bean.UriInfo;
import cn.khthink.easyapi.config.Constant;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.kit.EasyLogger;
import cn.khthink.easyapi.nilclazz.NullEasyAction;
import cn.khthink.easyapi.tools.ClassScannerTools;

import java.util.*;

/**
 * 处理器池
 *
 * @author kh
 */
public class EasyActionPool {

    /**
     * 处理池
     */
    private static Map<String, BaseEasyAction> actionMap = new HashMap<>();

    /**
     * 处理信息列表
     */
    private Map<String, cn.khthink.easyapi.api.bean.Action> actionInfoMap = new HashMap<>();

    /**
     * 处理器信息列表
     */
    private Map<String, ActionGroup> actionGroupMap = new HashMap<>();

    /**
     * 处理器列表
     */
    @Deprecated
    private List<BaseEasyAction> actionList = new ArrayList<>();

    private static class ActionPool {
        private static EasyActionPool pool = new EasyActionPool();
    }

    private EasyActionPool() {
    }

    public static EasyActionPool getInstance() {
        return ActionPool.pool;
    }


    /**
     * 初始化处理器
     */
    public void init() {
        addAction(Constant.NULLACTION, new NullEasyAction());
        List<String> clazzNames = new ArrayList<>();
        if (CoreConfig.actionPackage != null) {
            for (String name : CoreConfig.actionPackage) {
                ClassScannerTools.getInstance().getClazzs(name, clazzNames);
            }
            for (String clazz : clazzNames) {
                if (checkAction(clazz)) {
                    try {
                        BaseEasyAction c = (BaseEasyAction) Class.forName(clazz).newInstance();
                        UriInfo info = getActionInfo(c);
                        if (info != null) {
                            addAction(info.getInfo(), c);
                        }
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                        EasyLogger.trace(e);
                    }
                }
            }
            EasyLogger.info("--->载入处理器完毕");
        }
    }

    /**
     * 添加处理器
     *
     * @param info           路由
     * @param baseEasyAction 处理器
     */
    private void addAction(String info, BaseEasyAction baseEasyAction) {
        actionMap.put(info, baseEasyAction);
        if (!info.equals(Constant.NULLACTION)) {
            cn.khthink.easyapi.api.bean.Action action = ActionFactory.getInstance().createAction(baseEasyAction);
            if (actionGroupMap.containsKey(action.getGroup().getGroupName())) {
                actionGroupMap.get(action.getGroup().getGroupName()).addAction(action);
            } else {
                List<cn.khthink.easyapi.api.bean.Action> list = new ArrayList<>();
                list.add(action);
                actionGroupMap.put(action.getGroup().getGroupName(), new ActionGroup(action.getGroup().getGroupName(), action.getGroup().getPower(), list));
            }
            actionInfoMap.put(info, action);
            EasyLogger.info("--->载入" + info + "处理器成功");
        }
    }

    /**
     * 删除处理器
     *
     * @param tag  标记
     * @param name 处理器名称
     */
    private void deleteAction(String tag, String name) {
        actionMap.remove(tag + name);
        EasyLogger.info("--->删除" + tag + name + "处理器成功");
    }

    /**
     * 检查是否为处理器子类
     *
     * @param clazz 类路径
     * @return boolean
     */
    private boolean checkAction(String clazz) {
        try {
            return Class.forName(clazz).getSuperclass().getName().equals(Constant.EASYACTION);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 获取处理器信息
     *
     * @return UriInfo
     */
    private UriInfo getActionInfo(BaseEasyAction action) {
        if (action.getClass().getAnnotation(Action.class) == null) {
            return null;
        }
        Action actionInfo = action.getClass().getAnnotation(Action.class);
        return new UriInfo(actionInfo.actionTag(), actionInfo.actionName());
    }

    /**
     * 获取处理器
     *
     * @param uri 路由bean
     * @return BaseEasyAction
     */
    public BaseEasyAction getAction(UriInfo uri) {
        return getAction(uri.getInfo());
    }

    /**
     * 获取处理器信息
     *
     * @param uriInfo 路由信息
     * @return cn.khthink.easyapi.api.bean.Action
     */
    public cn.khthink.easyapi.api.bean.Action getActionInfo(UriInfo uriInfo) {
        return getActionInfo(uriInfo.getInfo());
    }

    /**
     * 获取处理器
     *
     * @param uri 路由地址
     * @return BaseEasyAction
     */
    public BaseEasyAction getAction(String uri) {
        return actionMap.getOrDefault(uri, actionMap.get(Constant.NULLACTION));
    }

    /**
     * 获取处理器信息
     *
     * @param uriInfo 路由信息
     * @return cn.khthink.easyapi.api.bean.Action
     */
    public cn.khthink.easyapi.api.bean.Action getActionInfo(String uriInfo) {
        return actionInfoMap.getOrDefault(uriInfo, null);
    }

    /**
     * 获取处理器列表
     *
     * @return List
     */
    @Deprecated
    public List<BaseEasyAction> getAllAction() {
        actionList.clear();
        for (String s : actionMap.keySet()) {
            if (!s.equals(Constant.NULLACTION)) {
                actionList.add(actionMap.get(s));
            }
        }
        return actionList;
    }

    /**
     * 获取所有处理器
     *
     * @return Map
     */
    @Deprecated
    public Map<String, BaseEasyAction> getActionMap() {
        return actionMap;
    }

    /**
     * 通过分组名获取接口列表
     *
     * @param groupName 组名
     * @return List
     */
    public List<cn.khthink.easyapi.api.bean.Action> getActionsByGroup(String groupName) {
        return actionGroupMap.get(groupName).getActionList();
    }

    /**
     * 获取接口分组列表
     *
     * @return Map
     */
    public Map<String, ActionGroup> getActionGroupList() {
        return actionGroupMap;
    }

    /**
     * 获取api action列表
     *
     * @return List
     */
    public List<cn.khthink.easyapi.api.bean.Action> getAllActions() {
        List<cn.khthink.easyapi.api.bean.Action> list = new ArrayList<>();
        for (String s : actionMap.keySet()) {
            if (!s.equals(Constant.NULLACTION)) {
                list.add(ActionFactory.getInstance().createAction(actionMap.get(s)));
            }
        }
        return list;
    }

}
