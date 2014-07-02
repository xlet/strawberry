package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.Message;

/**
 * @author jackie.
 */
public class WebNormalMessage extends Message implements ClientToServerMessage {

    private String from, to, content;

    private MessageClientType messageClientType;

    /**
     * constructor.
     *
     * @param from    from id.
     * @param to      to id.
     * @param content message context.
     */
    public WebNormalMessage(String from, String to, String content) {
        super(MessageType.WebNormalMessage);
        this.messageClientType = MessageClientType.Web;
        this.from = from;
        this.to = to;
        this.content = content;
    }

    /**
     * get from id.
     * @return from id.
     */
    public String getFrom() {
        return from;
    }

    /**
     * set from id.
     * @param from from id.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * get to id.
     * @return to id.
     */
    public String getTo() {
        return to;
    }

    /**
     * set to id.
     * @param to to id.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * get message content.
     * @return message content.
     */
    public String getContent() {
        return content;
    }

    /**
     * set message content.
     * @param content message context.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * get message client type.
     * @return message client type.
     */
    public MessageClientType getMessageClientType() {
        return messageClientType;
    }

    /**
     * set message client type.
     * @param messageClientType message client type.
     */
    public void setMessageClientType(MessageClientType messageClientType) {
        this.messageClientType = messageClientType;
    }
}
