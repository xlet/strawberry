package cn.w.im.domains.messages.responses;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-10 下午2:31.
 * Summary: 注册回复消息.
 */
public class ServerRegisterResponseMessage extends ResponseMessage {

    private List<ServerBasic> startedMessageServers;

    private List<ServerBasic> startedLoginServers;

    /**
     * 构造函数.
     */
    public ServerRegisterResponseMessage() {
        super(MessageType.ServerRegisterResponse);
        startedMessageServers = new ArrayList<ServerBasic>();
        startedLoginServers = new ArrayList<ServerBasic>();
    }

    /**
     * 构造函数.
     *
     * @param success 注册成功:true.
     */
    public ServerRegisterResponseMessage(boolean success) {
        this();
        this.setSuccess(success);
    }

    /**
     * get started message services basic.
     *
     * @return started message services basic.
     */
    public List<ServerBasic> getStartedMessageServers() {
        return startedMessageServers;
    }

    /**
     * set started message services basic.
     *
     * @param startedMessageServers started message service basic.
     */
    public void setStartedMessageServers(List<ServerBasic> startedMessageServers) {
        this.startedMessageServers = startedMessageServers;
    }

    /**
     * get started login servers basic.
     *
     * @return started login servers basic.
     */
    public List<ServerBasic> getStartedLoginServers() {
        return startedLoginServers;
    }

    /**
     * set started login servers basic.
     *
     * @param startedLoginServers started login servers basic.
     */
    public void setStartedLoginServers(List<ServerBasic> startedLoginServers) {
        this.startedLoginServers = startedLoginServers;
    }

    /**
     * add started message service basic.
     *
     * @param startedMessageServerBasic started message server basic.
     */
    public void addStartedMessageServer(ServerBasic startedMessageServerBasic) {
        this.startedMessageServers.add(startedMessageServerBasic);
    }

    /**
     * add started login server basic.
     *
     * @param startedLoginServerBasic started login server basic.
     */
    public void addStartedLoginServer(ServerBasic startedLoginServerBasic) {
        this.startedLoginServers.add(startedLoginServerBasic);
    }

}
