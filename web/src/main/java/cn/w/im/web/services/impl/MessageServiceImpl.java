package cn.w.im.web.services.impl;

import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.persistent.mongo.message.client.MongoNormalMessageDao;
import cn.w.im.web.imserver.MessageServerClient;
import cn.w.im.web.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author jackie.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageServerClient messageServerClient;

    @Autowired
    private MongoNormalMessageDao mongoNormalMessageDao;


    @Override
    public List<NormalMessage> sendMessage(NormalMessage message) {
        messageServerClient.sendMessage(message);
        return this.getNotReceivedMessage(message.getFrom(), message.getTo());
    }

    @Override
    public List<NormalMessage> getNotReceivedMessage(String loginId, String toId) {
        return mongoNormalMessageDao.getNotReceivedMessage(toId, loginId);
    }

    @Override
    public List<NormalMessage> getNotReceivedMessage(String loginId) {
        return mongoNormalMessageDao.getNotReceivedMessage(loginId);
    }
}
