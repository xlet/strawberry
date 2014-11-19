package cn.w.im.core.test.provider;

import cn.w.im.core.Channel;
import cn.w.im.core.client.provider.*;
import cn.w.im.core.member.TempMember;
import cn.w.im.core.client.*;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.core.server.LoginServer;
import cn.w.im.core.server.MessageServer;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.*;

/**
 * client provider test.
 */
public class ClientProviderTester {

    private static final int PORT = 13444;

    private static final AbstractServer THIS_SERVER = new MessageServer(PORT);

    private static final AbstractServer LOGIN_SERVER = new LoginServer(PORT);

    private static final Channel channel = new Channel() {

        private final Logger LOGGER = LoggerFactory.getLogger("tempChannel");
        private final ObjectMapper mapper = new ObjectMapper();
        private final ServerBasic serverBasic = THIS_SERVER.getServerBasic();

        @Override
        public String host() {
            return serverBasic.getHost();
        }

        @Override
        public int port() {
            return PORT;
        }

        @Override
        public void send(Message message) {
            try {
                String strMessage = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
                this.LOGGER.debug("send message:{}", strMessage);
            } catch (JsonProcessingException e) {
                this.LOGGER.error(e.getMessage(), e);
            }
        }

        @Override
        public void close() {
            this.LOGGER.debug("this channel closed.");
        }
    };

    @Test
    public void test_register_no_type_client() throws ClientRegisteredException {

        ClientProvider clientProvider = new DefaultClientProvider();
        clientProvider.registerClient(channel, THIS_SERVER);
    }

    @Test(expected = ClientRegisteredException.class)
    public void test_register_no_type_client_with_register_exception() throws ClientRegisteredException {
        ClientProvider clientProvider = new DefaultClientProvider();
        clientProvider.registerClient(channel, THIS_SERVER);
        clientProvider.registerClient(channel, THIS_SERVER);
    }

    @Test
    public void test_get_client() throws ClientRegisteredException, ClientNotFoundException {
        ClientProvider clientProvider = new DefaultClientProvider();
        clientProvider.registerClient(channel, THIS_SERVER);

        Client client = clientProvider.getClient(channel);
        assertThat(client).isInstanceOf(NoTypeClient.class);
    }

    @Test
    public void test_register_message_client()
            throws ClientRegisteredException, ClientNotRegisterException,
            MessageClientRegisteredException, ClientNotFoundException {
        ClientProvider clientProvider = new DefaultClientProvider();
        clientProvider.registerClient(channel, THIS_SERVER);

        Client basicClient = clientProvider.getClient(channel);
        assertThat(basicClient).isInstanceOf(NoTypeClient.class);

        BasicMember member = new TempMember();
        member.setId("wdemo1:admin");
        clientProvider.registerClient(channel.host(), channel.port(), member, MessageClientType.WinForm);

        Client client = clientProvider.getClient(member, MessageClientType.WinForm);
        assertThat(client).isInstanceOf(MessageClient.class);

        Client nowBasicClient = clientProvider.getClient(channel);
        assertThat(nowBasicClient).isInstanceOf(MessageClient.class);
    }

    @Test(expected = ClientNotRegisterException.class)
    public void test_register_message_client_with_clientNotRegister_error() throws ClientNotRegisterException, MessageClientRegisteredException {

        ClientProvider clientProvider = new DefaultClientProvider();

        BasicMember member = new TempMember();
        member.setId("wdemo1:admin");
        clientProvider.registerClient(channel.host(), channel.port(), member, MessageClientType.WinForm);
    }

    @Test(expected = MessageClientRegisteredException.class)
    public void test_register_message_client_with_clientRegistered_error()
            throws ClientRegisteredException, ClientNotRegisterException, MessageClientRegisteredException {
        ClientProvider clientProvider = new DefaultClientProvider();

        clientProvider.registerClient(channel, THIS_SERVER);

        BasicMember member = new TempMember();
        member.setId("wdemo1:admin");
        //register success.
        clientProvider.registerClient(channel.host(), channel.port(), member, MessageClientType.WinForm);

        //register again error.
        clientProvider.registerClient(channel.host(), channel.port(), member, MessageClientType.WinForm);

    }

    @Test
    public void test_register_server_as_client() throws ClientRegisteredException, ClientNotFoundException, ServerRegisteredException, ClientNotRegisterException {
        ClientProvider clientProvider = new DefaultClientProvider();
        clientProvider.registerClient(channel, THIS_SERVER);

        Client basicClient = clientProvider.getClient(channel);
        assertThat(basicClient).isInstanceOf(NoTypeClient.class);

        ServerBasic serverBasic = THIS_SERVER.getServerBasic();

        clientProvider.registerClient(channel.host(), channel.port(), serverBasic);
        Client client = clientProvider.getClient(serverBasic);

        assertThat(client).isInstanceOf(ServerAsClient.class);

        Client nowBasicClient = clientProvider.getClient(channel);
        assertThat(nowBasicClient).isInstanceOf(ServerAsClient.class);
    }

    @Test(expected = ClientNotRegisterException.class)
    public void test_register_server_as_client_with_clientNotRegister_error() throws ServerRegisteredException, ClientNotRegisterException {
        ClientProvider clientProvider = new DefaultClientProvider();

        ServerBasic serverBasic = LOGIN_SERVER.getServerBasic();
        clientProvider.registerClient(channel.host(), channel.port(), serverBasic);
    }

    @Test(expected = ServerRegisteredException.class)
    public void test_register_server_as_client_with_clientRegistered_error() throws ClientRegisteredException, ServerRegisteredException, ClientNotRegisterException {
        ClientProvider clientProvider = new DefaultClientProvider();

        clientProvider.registerClient(channel, THIS_SERVER);

        ServerBasic serverBasic = LOGIN_SERVER.getServerBasic();
        clientProvider.registerClient(channel.host(), channel.port(), serverBasic);

        clientProvider.registerClient(channel.host(), channel.port(), serverBasic);
    }
}
