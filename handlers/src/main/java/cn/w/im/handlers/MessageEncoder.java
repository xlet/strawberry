package cn.w.im.handlers;

import cn.w.im.domains.messages.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-20 上午9:24.
 * Summary: 消息编码.
 */
public class MessageEncoder extends MessageToMessageEncoder<Message> {

    /**
     * json mapper.
     */
    private final static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 编码.
     * @param channelHandlerContext 当前上下文.
     * @param message 消息.
     * @param objects 编码后的消息.
     * @throws Exception 异常.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> objects) throws Exception {
        String messageStr = MAPPER.writeValueAsString(message);
        objects.add(messageStr);
    }
}
