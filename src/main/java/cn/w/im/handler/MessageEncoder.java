package cn.w.im.handler;

import cn.w.im.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Creator: JackieHan
 * DateTime: 13-12-20 上午9:24
 */
public class MessageEncoder extends MessageToMessageEncoder<Message> {

    private final static ObjectMapper MAPPER=new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> objects) throws Exception {
        String messageStr=MAPPER.writeValueAsString(message);
        objects.add(messageStr);
    }
}
