package cn.w.im.plugins.message;

import cn.w.im.message.LogoutMessage;
import cn.w.im.message.LogoutResponseMessage;
import cn.w.im.message.Message;

/**
 * Creator: JackieHan
 * DateTime: 13-12-26 上午10:07
 */
public class LogoutMessageHandler  extends AbstractMessageHandler{
    @Override
    public void process(Message message) {
        LogoutMessage logoutMessage=(LogoutMessage)message;
        String id=logoutMessage.getId();
        this.getServerInfo().removeClient(id);

        this.getCtx().writeAndFlush(new LogoutResponseMessage(true));
    }
}
