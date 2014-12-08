package cn.w.im.core.status.memberAll;

import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.client.Client;
import cn.w.im.core.client.ClientRemoveListener;
import cn.w.im.core.client.MessageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * message client remove process implement.
 */
public class MessageClientRemoveListener implements ClientRemoveListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageClientRemoveListener.class);

    private MemberAllProvider memberProvider;


    public MessageClientRemoveListener(MemberAllProvider memberProvider) {
        this.memberProvider = memberProvider;
    }

    @Override
    public void onClientRemove(Client client) throws ServerInnerException {
        if (client instanceof MessageClient) {
            MessageClient messageClient = (MessageClient) client;
            this.onMessageClientRemove(messageClient);
        }
    }

    private void onMessageClientRemove(MessageClient messageClient) throws ServerInnerException {
        MessageClientType clientType = messageClient.getClientType();
        BasicMember logoutMember = messageClient.getMember();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("receive message client[member:{},clientType:{} removing.", logoutMember.getId(), clientType);
        }

        try {
            MemberAll memberAll = this.memberProvider.getMember(messageClient.getMember());
            if (memberAll.isLogout(clientType)) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("member is not logout.invoke member logout.");
                }

                memberAll.logout(clientType);
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("member has logout.");
                }
            }
        } catch (MemberAllNotExisted e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("member all is not existed. perhaps member has logout.");
            }
        }


    }
}
