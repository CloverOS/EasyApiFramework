package cn.khthink.easyapi.api.bean;

/*
    Create by KH at 2017/11/22 12:51 
 */

/**
 * 处理器分组
 *
 * @author kh
 */
public class Group {
    private int power;

    private String groupName;

    public Group(int power, String groupName) {
        this.power = power;
        this.groupName = groupName;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
