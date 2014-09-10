package cn.w.im.web.vo.response;

import cn.w.im.web.vo.MessageStatusViewObject;

import java.util.ArrayList;
import java.util.List;

/**
 * get message status response.
 */
public class GetMessageStatusResponse extends BaseResponse {

    private List<MessageStatusViewObject> statuses= new ArrayList<MessageStatusViewObject>();

    public List<MessageStatusViewObject> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<MessageStatusViewObject> statuses) {
        this.statuses = statuses;
    }
}
