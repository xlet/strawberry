package cn.w.im.core.providers.cache.client;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.ProductType;
import cn.w.im.exceptions.*;
import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午3:32.
 * Summary: client cache provider.
 */
public interface ClientCacheProvider {

    /**
     * register client.
     *
     * @param context client context.
     * @throws ClientRegisteredException client has registered exception.
     */
    void registerClient(ChannelHandlerContext context) throws ClientRegisteredException;

    /**
     * register message client.
     *
     * @param productType       product type.
     * @param messageClientType message client type.
     * @param loginId           login id.
     * @param linkedHost        linked host.
     * @param linkedPort        linked port.
     * @throws MessageClientRegisteredException message client has registered exception.
     * @throws ClientNotRegisterException       client dit not register exception.
     */
    void registerClient(ProductType productType, MessageClientType messageClientType, String loginId, String linkedHost, int linkedPort) throws MessageClientRegisteredException, ClientNotRegisterException;

    /**
     * register server.
     *
     * @param serverBasic server basic.
     * @param linkedHost  linked host.
     * @param linkedPort  linked port.
     * @throws ClientNotRegisterException client dit not register exception.
     * @throws ServerRegisteredException  server has registered exception.
     */
    void registerClient(ServerBasic serverBasic, String linkedHost, int linkedPort) throws ServerRegisteredException, ClientNotRegisterException;

    /**
     * register other server message client.
     *
     * @param messageClientBasic message client basic.
     * @param serverBasic        linked server basic.
     * @throws MessageClientRegisteredException message client has registered exception.
     * @throws ServerNotRegisterException       server not registered exception.
     */
    void registerClient(MessageClientBasic messageClientBasic, ServerBasic serverBasic) throws ServerNotRegisterException, MessageClientRegisteredException;

    /**
     * remove a registered client.
     *
     * @param context current context.
     * @throws ClientNotRegisterException client dit not register exception.
     * @throws ServerNotRegisterException server dit not register exception.
     */
    void removeClient(ChannelHandlerContext context) throws ServerNotRegisterException, ClientNotRegisterException;

    /**
     * remove a registered client.
     *
     * @param host linkedHost.
     * @param port linkedPort.
     * @throws ClientNotRegisterException client dit not register exception.
     * @throws ServerNotRegisterException server dit not register exception.
     */
    void removeClient(String host, int port) throws ServerNotRegisterException, ClientNotRegisterException;

    /**
     * get match client.
     *
     * @param host linkedHost.
     * @param port linkedPort.
     * @return client.
     * @throws ClientNotFoundException not found exception.
     */
    Client getClient(String host, int port) throws ClientNotFoundException;

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
     * @param messageClientType message client type.
     * @param loginId           login id.
     * @return Client.
     * @throws ClientNotFoundException client not found exception.
     */
    Client getClient(MessageClientType messageClientType, String loginId) throws ClientNotFoundException;

    /**
     * get matched client.
     *
     * @param loginId login id.
     * @return clients.
     */
    Collection<Client> getClients(String loginId);

    /**
     * get all matched client.
     *
     * @param serverType server type.
     * @return clients.
     */
    Collection<Client> getClients(ServerType serverType);

    /**
     * get all message clients.
     *
     * @return all message clients.
     */
    Collection<Client> getAllMessageClients();

    /**
     * get all server clients.
     *
     * @return get all server clients.
     */
    Collection<Client> getAllServerClients();
}
