package cn.w.im.plugins.message;

import cn.w.im.message.MessageType;
import cn.w.im.server.ServerInfo;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan
 * DateTime: 13-12-24 下午9:18
 */
public class MessageHandlerFactory{
    public static MessageHandler getHandler(MessageType messageType,ChannelHandlerContext ctx,ServerInfo serverInfo) throws Exception{
        switch (messageType){
            case Login:
                LoginMessageHandler loginHandler = new LoginMessageHandler();
                loginHandler.init(ctx,serverInfo);
                return loginHandler;
            case Normal:
                NormalMessageHandler normalHandler=new NormalMessageHandler();
                normalHandler.init(ctx,serverInfo);
                return normalHandler;
            default:
                throw new Exception("不支持的消息类型!");
        }
    }
}
