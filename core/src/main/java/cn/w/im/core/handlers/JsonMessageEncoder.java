package cn.w.im.core.handlers;

import cn.w.im.domains.messages.Message;
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

    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> objects) throws Exception {
        String messageStr = mapper.writeValueAsString(message);
        String prettyMessageStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
        logger.debug("send message => "+channelHandlerContext.channel().remoteAddress().toString()+":");
        logger.debug(prettyMessageStr);
        objects.add(messageStr);
    }
}
