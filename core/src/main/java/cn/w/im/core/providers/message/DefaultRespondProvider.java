package cn.w.im.core.providers.message;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.RespondMessage;
import cn.w.im.domains.messages.server.ServerToServerMessage;
import cn.w.im.exceptions.NotRegisterRespondMessageException;
import cn.w.im.exceptions.NotRegisterRespondServerException;
import cn.w.im.exceptions.RegisteredRespondMessageException;
import cn.w.im.exceptions.RegisteredRespondServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 上午10:16.
 * Summary: implement ResponseProvider.
 * <p/>
 * this provider cached a map,the map save the waiting for reply message flag, the map value is a map who save server node id and the server has allResponded response message.
 */
public class DefaultRespondProvider implements RespondProvider {

    private Logger logger;

    private Map<String, Map<String, Boolean>> responseMap;

    /**
     * constructor.
     */
    public DefaultRespondProvider() {
        logger = LoggerFactory.getLogger(this.getClass());
        responseMap = new ConcurrentHashMap<String, Map<String, Boolean>>();
    }

    @Override
    public boolean allResponded(String respondKey) throws NotRegisterRespondMessageException {
        if (!this.responseMap.containsKey(respondKey)) {
            throw new NotRegisterRespondMessageException(respondKey);
        }

        Map<String, Boolean> serverMap = this.responseMap.get(respondKey);
        for (String key : serverMap.keySet()) {
            if (!serverMap.get(key)) {
                return false;
            }
        }

        this.responseMap.remove(respondKey);
        return true;
    }

    @Override
    public void receivedRespondedMessage(RespondMessage responseMessage) throws NotRegisterRespondMessageException, NotRegisterRespondServerException {
        String messageFlag = responseMessage.getRespondKey();
        if (!this.responseMap.containsKey(messageFlag)) {
            throw new NotRegisterRespondMessageException(messageFlag);
        }
        if (responseMessage instanceof ServerToServerMessage) {
            ServerBasic respondingServer = ((ServerToServerMessage) responseMessage).getFromServer();
            Map<String, Boolean> serverMap = this.responseMap.get(messageFlag);
            if (serverMap.containsKey(respondingServer.getNodeId())) {
                serverMap.remove(respondingServer.getNodeId());
                serverMap.put(respondingServer.getNodeId(), true);
            } else {
                throw new NotRegisterRespondServerException(respondingServer.getNodeId());
            }
        }
    }

    @Override
    public void registerResponded(String respondKey, ServerBasic serverBasic) throws RegisteredRespondMessageException, RegisteredRespondServerException {
        if (this.responseMap.containsKey(respondKey)) {
            Map<String, Boolean> serverMap = this.responseMap.get(respondKey);
            if (!serverMap.containsKey(serverBasic.getNodeId())) {
                serverMap.put(serverBasic.getNodeId(), false);
            } else {
                throw new RegisteredRespondMessageException(respondKey);
            }
        } else {
            Map<String, Boolean> serverMap = new ConcurrentHashMap<String, Boolean>();
            if (serverMap.containsKey(serverBasic.getNodeId())) {
                throw new RegisteredRespondServerException(serverBasic.getNodeId());
            }
            serverMap.put(serverBasic.getNodeId(), false);
            this.responseMap.put(respondKey, serverMap);
        }
    }

    @Override
    public void interrupt(String respondKey) throws RespondInterruptException, NotRegisterRespondMessageException {
        if (this.responseMap.containsKey(respondKey)) {
            this.responseMap.remove(respondKey);
            throw new RespondInterruptException(respondKey);
        } else {
            throw new NotRegisterRespondMessageException(respondKey);
        }
    }
}
