package cn.w.im.netty.handlers;

import cn.w.im.core.jackson.MapperCreator;
import cn.w.im.core.message.Message;
import cn.w.im.core.spring.SpringContext;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final MapperCreator mapperCreator;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JsonMessageEncoder() {
        this.mapperCreator = SpringContext.context().getBean(MapperCreator.class);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> objects) throws Exception {
        String messageStr = this.mapperCreator.mapper().writeValueAsString(message);
        String prettyMessageStr = this.mapperCreator.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(message);
        logger.debug("send message => " + channelHandlerContext.channel().remoteAddress().toString() + ":");
        logger.debug(prettyMessageStr);
        objects.add(messageStr);
    }
}
