package cn.w.im.netty.handlers;

import cn.w.im.core.jackson.MapperCreator;
import cn.w.im.core.message.Message;
import cn.w.im.core.spring.SpringContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-20 上午9:24.
 * Summary: 消息解码.
 */
public class JsonMessageDecoder extends MessageToMessageDecoder<String> {

    private final MapperCreator mapperCreator;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JsonMessageDecoder() {
        this.mapperCreator = SpringContext.context().getBean(MapperCreator.class);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String message, List<Object> objects) throws Exception {
        Message messageObj = this.mapperCreator.mapper().readValue(message, Message.class);
        messageObj.setReceivedTime(System.currentTimeMillis());
        logger.debug("receive message <= " + channelHandlerContext.channel().remoteAddress().toString() + ":");
        logger.debug(this.mapperCreator.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(messageObj));
        objects.add(messageObj);
    }
}
