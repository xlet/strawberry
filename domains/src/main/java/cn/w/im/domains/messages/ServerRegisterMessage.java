package cn.w.im.domains.messages;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.server.ServerType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 上午11:35.
 * Summary: 服务注册消息.
 */
public class ServerRegisterMessage extends Message {

    private ServerBasic serverBasic;

    private ServerType serverType;

    /**
     * 构造函数.
     */
    public ServerRegisterMessage() {
        super(MessageType.ServerRegister);
    }

    /**
     * 构造函数.
     *
     * @param serverBasic 服务基础信息.
     * @param serverType  服务类型.
     */
    public ServerRegisterMessage(ServerBasic serverBasic, ServerType serverType) {
        this();
        this.serverBasic = serverBasic;
        this.serverType = serverType;
    }

    /**
     * 获取MessageServer基础信息.
     *
     * @return MessageServer基础信息.
     */
    public ServerBasic getServerBasic() {
        return serverBasic;
    }

    /**
     * 设置MessageServer基础信息.
     *
     * @param serverBasic MessageServer基础信息.
     */
    public void setServerBasic(ServerBasic serverBasic) {
        this.serverBasic = serverBasic;
    }

    /**
     * 获取服务类型.
     *
     * @return 服务类型.
     */
    public ServerType getServerType() {
        return serverType;
    }

    /**
     * 设置服务类型.
     *
     * @param serverType 服务类型.
     */
    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
