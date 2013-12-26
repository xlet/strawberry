package cn.w.im.plugins.message;

import cn.w.im.message.Message;
import cn.w.im.message.NormalMessage;
import cn.w.im.server.ClientInfo;
import cn.w.im.utils.exceptions.ClientNotFoundException;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan
 * DateTime: 13-12-24 下午9:40
 * Summary:一般消息处理
 */
public class NormalMessageHandler extends AbstractMessageHandler {

    @Override
    public void process(Message message) {
        NormalMessage normalMessage = (NormalMessage) message;
        //TODO:jackie 序列化
        String toId = normalMessage.getTo();
        try {
            ChannelHandlerContext toCtx = getToCtx(toId);
            toCtx.writeAndFlush(normalMessage);
        } catch (ClientNotFoundException ex) {
            //TODO:jackie
        }
    }

    private ChannelHandlerContext getToCtx(String toId) {
        ClientInfo clientInfo = this.getServerInfo().getClient(toId);
        if (clientInfo == null)
            throw new ClientNotFoundException(toId);
        return clientInfo.getContext();
    }
}
