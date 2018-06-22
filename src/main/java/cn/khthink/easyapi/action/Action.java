package cn.khthink.easyapi.action;

/*
    Create by KH at 2017/10/26 23:03 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.bean.Request;

import java.io.Serializable;

/**
 * 处理器抽象类
 *
 * @author kh
 */
public interface Action extends Serializable {

    /**
     * 处理
     *
     * @param request 请求
     * @throws Exception 异常
     */
    void processData(Request request) throws Exception;

}
