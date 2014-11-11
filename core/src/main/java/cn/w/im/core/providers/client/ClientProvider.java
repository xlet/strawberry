package cn.w.im.core.providers.client;

import cn.w.im.core.Channel;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.ServerType;
import cn.w.im.core.exception.*;
import cn.w.im.core.member.BasicMember;

import java.util.Collection;

/**
 * client manage provider.
 */
public interface ClientProvider {
    /**
     * register client.
     *
     * @param channel current channel.
     * @throws cn.w.im.core.exception.ClientRegisteredException client has registered exception.
     */
    void registerClient(Channel channel) throws ClientRegisteredException;

    /**
     * register message client.
     *
     * @param channel    current channel.
     * @param member     member.
     * @param clientType client type.
     * @throws cn.w.im.core.exception.MessageClientRegisteredException message client has registered exception.
     * @throws cn.w.im.core.exception.ClientNotRegisterException       client dit not register exception.
     */
    void registerClient(Channel channel, BasicMember member, MessageClientType clientType) throws MessageClientRegisteredException, ClientNotRegisterException;

    /**
     * register server.
     *
     * @param channel     current channel.
     * @param serverBasic server basic.
     * @throws ClientNotRegisterException client dit not register exception.
     * @throws cn.w.im.core.exception.ServerRegisteredException  server has registered exception.
     */
    void registerClient(Channel channel, ServerBasic serverBasic) throws ServerRegisteredException, ClientNotRegisterException;

    /**
     * remove a registered client.
     *
     * @param channel channel.
     * @throws ClientNotRegisterException client dit not register exception.
     * @throws ServerNotRegisterException server dit not register exception.
     */
    void removeClient(Channel channel) throws ServerNotRegisterException, ClientNotRegisterException;


    /**
     * get match client.
     *
     * @param host host.
     * @param port port.
     * @return client.
     * @throws cn.w.im.core.exception.ClientNotFoundException
     */
    Client getClient(String host, int port) throws ClientNotFoundException;

    /**
     * get match client.
     *
     * @param channel channel.
     * @return client.
     * @throws ClientNotFoundException not found exception.
     */
    Client getClient(Channel channel) throws ClientNotFoundException;

    /**
     * get matched client.
     *
     * @param serverBasic server basic.
     * @return Client.
     * @throws ClientNotFoundException client not found exception.
     */
    Client getClient(ServerBasic serverBasic) throws ClientNotFoundException;

    /**
     * get matched client.
     *
     * @param member     member.
     * @param clientType message client type.
     * @return Client.
     * @throws ClientNotFoundException client not found exception.
     */
    Client getClient(BasicMember member, MessageClientType clientType) throws ClientNotFoundException;

    /**
     * get matched client.
     *
     * @param member member.
     * @return clients.
     */
    Collection<Client> getClient(BasicMember member);

    /**
     * get all matched client.
     *
     * @param serverType server type.
     * @return clients.
     */
    Collection<Client> getClient(ServerType serverType);

    /**
     * get all server as client.
     *
     * @return clients.
     */
    Collection<Client> getAllServerClient();
}
