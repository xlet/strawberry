package org.xlet.strawberry.core.client.provider;

import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.server.ServerBasic;
import org.xlet.strawberry.core.client.MessageClientType;

import java.util.HashMap;
import java.util.Map;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-25 上午11:23.
 * Summary:客户端没有找到异常.
 */
public class ClientNotFoundException extends ServerInnerException {

    private Map<String, Object> searchConditions = new HashMap<String, Object>();

    /**
     * constructor.
     *
     * @param memberId   member id.
     e @param clientType client type.
     */
    public ClientNotFoundException(String memberId, MessageClientType clientType) {
        super("Client[memberId:" + memberId + ",clientType:" + clientType + "] not found.");
        this.searchConditions.put("memberId", memberId);
        this.searchConditions.put("clientType", clientType);
    }

    /**
     * constructor.
     *
     * @param host host.
     * @param port port.
     */
    public ClientNotFoundException(String host, int port) {
        super("Client[" + host + ":" + port + "] not found.");
        this.searchConditions.put("host", host);
        this.searchConditions.put("port", port);
    }

    /**
     * constructor.
     *
     * @param serverBasic server basic.
     */
    public ClientNotFoundException(ServerBasic serverBasic) {
        super("Client[nodeId:" + serverBasic.getNodeId() + "] not found.");
        this.searchConditions.put("serverBasic", serverBasic);
    }

    /**
     * get search conditions.
     *
     * @return search conditions.
     */
    public Map<String, Object> getSearchConditions() {
        return searchConditions;
    }
}
