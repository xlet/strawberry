package org.xlet.strawberry.core.test.json;

import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.message.MessageType;

/**
 * test message.
 */
public class TestMessage extends Message {

    private String content;

    /**
     * constructor.
     */
    public TestMessage(String content) {
        super(MessageType.Custom);
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
