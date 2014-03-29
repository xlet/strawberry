package cn.w.im.exceptions;

import cn.w.im.domains.ServerBasic;

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
     * @param loginId login id.
     */
    public ClientNotFoundException(String loginId) {
        super("Client[" + loginId + "] not found.");
        this.searchConditions.put("loginId", loginId);
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
