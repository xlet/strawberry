package cn.w.im.test;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.ErrorCodeDefine;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-1 下午5:01
 * Summary:
 */
public class MessageTest {

    private Logger LOG = Logger.getLogger(this.getClass());

    private ObjectMapper mapper = new ObjectMapper();

    private static final MessageClientType CLIENT_TYPE = MessageClientType.WinForm;


    @Test
    public void gen_login_message() {
        LoginMessage loginMessage = new LoginMessage(CLIENT_TYPE,"username","password");
        String token = UUID.randomUUID().toString().replace("-", "");
        ServerBasic serverBasic = new ServerBasic();
        serverBasic.setHost("10.0.41.104");
        serverBasic.setNodeId("1");
        serverBasic.setPort(17021);
        serverBasic.setStart(true);
        serverBasic.setStartDateTime(System.currentTimeMillis());
        serverBasic.setServerType(ServerType.LoginServer);
        ConnectToken connectToken = new ConnectToken("10.0.40.18","username",token,serverBasic);
        LoginResponseMessage loginSuccessResponseMessage = new LoginResponseMessage(connectToken);
        LoginResponseMessage loginFailResponseMessage = new LoginResponseMessage(ErrorCodeDefine.IDPASSWORDERRORCODE, "IDPASSWORDERRORCODE");


        ConnectMessage connectMessage = new ConnectMessage(CLIENT_TYPE, "username", token);

        ConnectResponseMessage connectResponseMessage = new ConnectResponseMessage();
        ConnectResponseMessage connectFailResponse = new ConnectResponseMessage(ErrorCodeDefine.TOKENERROR, "TOKENERROR");

        print(loginMessage);
        print(loginSuccessResponseMessage);
        print(loginFailResponseMessage);
        print(connectMessage);

        print(connectResponseMessage, connectFailResponse);
    }


    @Test
    public void gen_logout_message(){
        LogoutMessage logoutMessage = new LogoutMessage(CLIENT_TYPE, "username");
        print(logoutMessage);
        LogoutResponseMessage logoutResponseMessage = new LogoutResponseMessage(true);
        LogoutResponseMessage logoutFailResponseMessage = new LogoutResponseMessage(false);
        print(logoutResponseMessage, logoutFailResponseMessage);
    }

    @Test
    public void gen_normal_message(){
        NormalMessage message = new NormalMessage(CLIENT_TYPE, "one", "another", "消息内容");
        print(message);
    }

    @Test
    public void decode() throws IOException {
        String json = "{\"@type\":\"Normal\",\"clientType\":\"WinForm\",\"content\":\"who's your daddy\",\"from\":\"1004462060\",\"messageType\":\"Normal\",\"receivedTime\":1131313130.0,\"sendTime\":1404292690015.0,\"to\":\"1\"}\r\n";
        for(byte b: json.getBytes("utf-8")){
            System.out.print(String.format("%X",b)+" ");
        }
        NormalMessage message = mapper.readValue(json, NormalMessage.class);
       // System.out.println(message);

        print(message);

    }

    public void print(Object... o){
        for(Object object : o){
            print(object);
        }
    }

    public void print(Object o){
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
