package cn.w.im.core.handlers;

import cn.w.im.domains.messages.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-20 上午9:24.
 * Summary: 消息解码.
 */
public class JsonMessageDecoder extends MessageToMessageDecoder<String> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String message, List<Object> objects) throws Exception {
        Message messageObj = mapper.readValue(message, Message.class);
        messageObj.setReceivedTime(System.currentTimeMillis());
        logger.debug("receive message <= "+channelHandlerContext.channel().remoteAddress().toString()+":");
        logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageObj));
        objects.add(messageObj);
    }
}
