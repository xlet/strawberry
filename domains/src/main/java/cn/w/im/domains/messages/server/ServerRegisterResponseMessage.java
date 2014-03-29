package cn.w.im.domains.messages.server;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-10 下午2:31.
 * Summary: 注册回复消息.
 */
public class ServerRegisterResponseMessage extends ResponseMessage implements ServerToServerMessage {

    private List<ServerBasic> startedServers;

    private ServerBasic fromServer;

    /**
     * 构造函数.
     */
    public ServerRegisterResponseMessage() {
        super(MessageType.ServerRegisterResponse);
        startedServers = new ArrayList<ServerBasic>();
    }

    /**
     * constructor.
     *
     * @param startedServers started message service basic.
     * @param fromServer     from server basic.
     */
    public ServerRegisterResponseMessage(List<ServerBasic> startedServers, ServerBasic fromServer) {
        this();
        this.startedServers = startedServers;
        this.fromServer = fromServer;
    }

    /**
     * error constructor.
     *
     * @param errorCode
     * @param errorMessage
     * @param fromServer   from server basic.
     */
    public ServerRegisterResponseMessage(int errorCode, String errorMessage, ServerBasic fromServer) {
        super(MessageType.ServerRegisterResponse, errorCode, errorMessage);
        this.fromServer = fromServer;
    }

    /**
     * get started message services basic.
     *
     * @return started message services basic.
     */
    public List<ServerBasic> getStartedServers() {
        return startedServers;
    }

    /**
     * set started message services basic.
     *
     * @param startedServers started message service basic.
     */
    public void setStartedServers(List<ServerBasic> startedServers) {
        this.startedServers = startedServers;
    }

    @Override
    public ServerBasic getFromServer() {
        return fromServer;
    }

    /**
     * set from server basic.
     *
     * @param fromServer from server basic.
     */
    public void setFromServer(ServerBasic fromServer) {
        this.fromServer = fromServer;
    }
}
