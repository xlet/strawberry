package cn.w.im.core.message.server;

import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.MessageType;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.MustRespondMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 上午11:35.
 * Summary: 服务注册消息.
 */
public class ServerRegisterMessage extends Message implements MustRespondMessage, ServerToServerMessage {

    private ServerBasic serverBasic;

    private String respondKey;

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
     */
    public ServerRegisterMessage(ServerBasic serverBasic) {
        this();
        this.serverBasic = serverBasic;
        this.respondKey = UUID.randomUUID().toString();
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

    @JsonIgnore
    @Override
    public ServerBasic getFromServer() {
        return this.serverBasic;
    }

    @Override
    public String getRespondKey() {
        return this.respondKey;
    }

    /**
     * set respond key.
     *
     * @param respondKey respond key.
     */
    public void setRespondKey(String respondKey) {
        this.respondKey = respondKey;
    }
}
