package org.xlet.strawberry.netty.handlers;

import org.xlet.strawberry.core.jackson.MapperCreator;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.spring.SpringContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-20 上午9:24.
 * Summary: 消息编码.
 */
public class JsonMessageEncoder extends MessageToMessageEncoder<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMessageEncoder.class);

    private final MapperCreator mapperCreator;

    public JsonMessageEncoder() {
        this.mapperCreator = SpringContext.context().getBean(MapperCreator.class);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> objects) throws Exception {
        String messageStr = this.mapperCreator.mapper().writeValueAsString(message);
        if (LOGGER.isDebugEnabled()) {
            String prettyMessageJsonStr = this.mapperCreator.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(message);
            LOGGER.debug("send message => " + channelHandlerContext.channel().remoteAddress().toString() + ":");
            LOGGER.debug(prettyMessageJsonStr);
        }
        objects.add(messageStr);
    }
}
