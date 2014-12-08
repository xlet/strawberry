package cn.w.im.netty.heartbeat;

import cn.w.im.core.message.MessageType;
import cn.w.im.core.message.NonePersistentMessage;
import cn.w.im.core.message.Message;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 上午9:43
 * Summary: 心跳请求包
 */
public class Heartbeat extends Message implements Sequential, NonePersistentMessage {

    /**
     * the flag if this message need to be replied.
     */
    private boolean reply = false;
    /**
     * the sequence
     * once this message was replied, the response should specified the same sequence.
     * <note>Sequence is effective in duplex channel, and it should be incremental</note>
     */
    private int seq;

    /**
     * constructor.
     */
    public Heartbeat() {
        super(MessageType.Heartbeat);
    }

    public Heartbeat(boolean reply) {
        super(MessageType.Heartbeat);
        this.reply = true;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
