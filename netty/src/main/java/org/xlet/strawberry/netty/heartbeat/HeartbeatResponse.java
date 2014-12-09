package org.xlet.strawberry.netty.heartbeat;

import org.xlet.strawberry.core.message.MessageType;
import org.xlet.strawberry.core.message.NonePersistentMessage;
import org.xlet.strawberry.core.message.ResponseMessage;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 上午9:52
 * Summary: 心跳应答包
 */
public class HeartbeatResponse extends ResponseMessage implements Sequential, NonePersistentMessage {

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
