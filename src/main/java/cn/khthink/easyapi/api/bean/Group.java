package cn.khthink.easyapi.api.bean;

/*
    Create by KH at 2017/11/22 12:51 
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 处理器分组
 *
 * @author kh
 */
@Data
@AllArgsConstructor
public class Group {
    private int power;

    private String groupName;

}
