package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-22 上午10:52
 * Summary: get member profile request message
 */
public class GetProfileRequestMessage extends Message {

    private List<String> ids = new ArrayList<String>();

    public GetProfileRequestMessage() {
        super(MessageType.GetProfileRequest);
    }

    public GetProfileRequestMessage(String... ids) {
        this();
        for (String id : ids) {
            addId(id);
        }
    }


    public GetProfileRequestMessage addId(String id) {
        ids.add(id);
        return this;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
