package cn.w.im.handler;

import cn.w.im.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Creator: JackieHan
 * DateTime: 13-12-20 上午9:24
 */
public class MessageDecoder extends MessageToMessageDecoder<String> {

    private static ObjectMapper mapper=new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String message, List<Object> objects) throws Exception {
        Message messageObj = mapper.readValue(message,Message.class);
        objects.add(messageObj);
    }
}
