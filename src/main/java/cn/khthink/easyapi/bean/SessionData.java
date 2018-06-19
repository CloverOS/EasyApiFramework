package cn.khthink.easyapi.bean;

/*
	Create by KH at 2017/12/27 13:06 
 */

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * session类
 *
 * @author kh
 */
@Data
public class SessionData {
    private JSONObject data;
    private String creattime;
    private String uuid;
}
