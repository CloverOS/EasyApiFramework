package cn.khthink.easyapi.protocol;

/*
    Create by KH at 2017/11/10 14:14 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.bean.Request;

/**
 * 协议接口
 *
 * @author kh
 */
public interface IEasyProtocol {
    /**
     * 协议验证
     *
     * @param request 请求
     * @return boolean
     */
    boolean verify(Request request);
}
