package org.xlet.strawberry.core.status.memberAll;

import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.client.Client;
import org.xlet.strawberry.core.client.ClientRemoveListener;
import org.xlet.strawberry.core.client.MessageClient;
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
