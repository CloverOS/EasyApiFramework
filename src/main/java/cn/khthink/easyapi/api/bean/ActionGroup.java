package cn.khthink.easyapi.api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 处理器分组信息
 *
 * @author kh
 */
@Data
@AllArgsConstructor
public class ActionGroup implements Serializable {
    private String actionGourpName;
    private int power;
    private List<Action> actionList;

    /**
     * 添加action到组
     *
     * @param action 处理器
     */
    public void addAction(Action action) {
        actionList.add(action);
    }
}
