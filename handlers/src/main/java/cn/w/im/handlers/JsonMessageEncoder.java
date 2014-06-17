package cn.w.im.handlers;

import cn.w.im.domains.messages.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-20 上午9:24.
 * Summary: 消息编码.
 */
public class JsonMessageEncoder extends MessageToMessageEncoder<Message> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> objects) throws Exception {
        String messageStr = mapper.writeValueAsString(message);
        String prettyMessageStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
        logger.debug("send message:");
        logger.debug(prettyMessageStr);
        objects.add(messageStr);
    }
}
