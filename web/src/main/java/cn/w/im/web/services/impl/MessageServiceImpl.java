package cn.w.im.web.services.impl;

import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.messages.client.WebNormalMessage;
import cn.w.im.web.imserver.MessageServerClient;
import cn.w.im.web.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jackie.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageServerClient messageServerClient;


    @Override
    public void sendMessage(WebNormalMessage message) {
        messageServerClient.getCtx().writeAndFlush(message);
    }
}
