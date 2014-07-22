package cn.w.im.domains.messages.heartbeat;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.ResponseMessage;
import cn.w.im.utils.netty.Sequential;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 上午9:52
 * Summary: 心跳应答包
 */
public class HeartbeatResponse extends ResponseMessage implements Sequential {

    /**
     * the sequence of heartbeat request
     * an sequence makes it sure if the
     */
    private int seq;

    public HeartbeatResponse() {
        super(MessageType.HeartbeatResponse);
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
