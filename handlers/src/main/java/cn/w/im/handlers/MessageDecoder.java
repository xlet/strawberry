package cn.w.im.handlers;

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
public class MessageDecoder extends MessageToMessageDecoder<String> {

    private final ObjectMapper MAPPER = new ObjectMapper();
    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String message, List<Object> objects) throws Exception {
        logger.debug("received message: [" + message + "]");
        Message messageObj = MAPPER.readValue(message, Message.class);
        objects.add(messageObj);
    }
}
