package cn.khthink.easyapi.bean;

/*
    Create by KH at 2017/11/9 14:01 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServelt信息类
 *
 * @author kh
 */
@Data
public class HttpServletInfo {
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    public HttpServletInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }
}
