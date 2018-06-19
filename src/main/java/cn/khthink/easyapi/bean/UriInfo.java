package cn.khthink.easyapi.bean;

/*
    Create by KH at 2017/10/19 13:36 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import lombok.Data;

/**
 * 路由信息
 *
 * @author kh
 */
@Data
public class UriInfo {
    String tag;
    String actionname;

    public UriInfo(String tag, String actionname) {
        this.tag = tag;
        this.actionname = actionname;
    }

    public String getInfo() {
        return tag + actionname;
    }
}
