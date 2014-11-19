package cn.w.im.core.test.json;

import cn.w.im.core.ProductType;
import cn.w.im.core.exception.ErrorCodeDefine;
import cn.w.im.core.jackson.MapperCreator;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.forward.ForwardRequestMessage;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.server.ServerType;
import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.message.client.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.*;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-1 下午5:01
 * Summary:
 */

@ContextConfiguration(locations = "classpath*:testApplicationContext.xml")
public class MessageTest extends AbstractJUnit4SpringContextTests {

    private static final Logger LOG = LoggerFactory.getLogger(MessageTest.class);

    private static final String HOST = "10.0.40.38";
    private static final int PORT = 17021;

    @Autowired
    private MapperCreator mapperCreator;

    private static final MessageClientType CLIENT_TYPE = MessageClientType.WinForm;

    @Test
    public void test_TestMessage_serialize() throws JsonProcessingException {
        Message message = new TestMessage("bbbbbb");
        String jsonStr = this.mapperCreator.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(message);

        LOG.debug(jsonStr);

        assertThat(jsonStr).contains("@type");
    }

    @Test
    public void test_ForwardRequestMessage_serialize() throws IOException {
        Message message = new ForwardRequestMessage();

        String json = this.mapperCreator.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(message);

        LOG.debug(json);

        assertThat(json).contains("@type");

        Message deMessage = this.mapperCreator.mapper().readValue(json, Message.class);

        assertThat(deMessage).isInstanceOf(ForwardRequestMessage.class);
    }

    @Test
    public void test_normal_message_serialize() throws IOException {
        Message message = new NormalMessage(CLIENT_TYPE, "jackie", "jackie", "hello world!");

        String jsonStr = mapperCreator.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(message);

        LOG.debug("normal message:{}", jsonStr);

        Message normalMessage = mapperCreator.mapper().readValue(jsonStr, Message.class);
    }

    @Test
    public void gen_login_message() throws JsonProcessingException, UnsupportedEncodingException {

        LoginMessage loginMessage = new LoginMessage(CLIENT_TYPE, ProductType.OA, "13622882929", "w123456");
        ServerBasic serverBasic = new ServerBasic(ServerType.LoginServer, HOST, PORT);
        serverBasic.setStart(true);
        serverBasic.setStartDateTime(System.currentTimeMillis());
        LoginResponseMessage loginSuccessResponseMessage = new LoginResponseMessage();
        loginSuccessResponseMessage.setAllocateServer(serverBasic);
        loginSuccessResponseMessage.setMemberId("11223344");
        loginSuccessResponseMessage.setToken("dddddddddddd");
        LoginResponseMessage loginFailResponseMessage = new LoginResponseMessage(ErrorCodeDefine.ID_PASSWORD_ERROR_CODE, "ID_PASSWORD_ERROR_CODE");
        print(loginMessage);
        print(loginSuccessResponseMessage);
        print(loginFailResponseMessage);
    }

    @Test
    public void gen_connect_message() {
        String token = "fcf3e04db292408eb834d9c21eb432ce";
        ConnectMessage connectMessage = new ConnectMessage(ProductType.OA, CLIENT_TYPE, "13622882929", token);
        print(connectMessage);
        ConnectResponseMessage connectResponseMessage = new ConnectResponseMessage();
        // connectResponseMessage.setSelf();
        ConnectResponseMessage connectFailResponse = new ConnectResponseMessage(ErrorCodeDefine.TOKEN_ERROR_CODE, "TOKEN_ERROR_CODE");
        print(connectResponseMessage, connectFailResponse);
    }

    @Test
    public void gen_normal_message() {
        NormalMessage message = new NormalMessage(CLIENT_TYPE, "one", "another", "消息内容");
        print(message);
    }

    @Test
    public void decode() throws IOException {
        String json = "{\"@type\":\"Normal\",\"clientType\":\"WinForm\",\"content\":\"who's your daddy\",\"from\":\"1004462060\",\"messageType\":\"Normal\",\"receivedTime\":1131313130.0,\"sendTime\":1404292690015.0,\"to\":\"1\"}\r\n";
        for (byte b : json.getBytes("utf-8")) {
            System.out.print(String.format("%X", b) + " ");
        }
        NormalMessage message = mapperCreator.mapper().readValue(json, NormalMessage.class);
        // System.out.println(message);
        print(message);

        String connectJson = "{\"@type\":\"ConnectResponse\",\"messageType\":\"ConnectResponse\",\"sendTime\":1405627690320,\"receivedTime\":0,\"success\":true,\"errorCode\":0,\"errorMessage\":null,\"self\":{\"status\":0,\"temp\":false,\"id\":\"11223344\",\"nickName\":\"敏了个敏\",\"address\":\"霞飞路198号\",\"mobile\":\"13859985404\",\"contractor\":null,\"shopName\":null,\"mobileValid\":true,\"realNameValid\":false,\"thumb\":\"http://img5.wcnimg.com/M00/4C/AE/wKgKYlO_jtTwFH7FAABH955dNLM371-100-100.jpg\",\"email\":\"315095859@qq.com\",\"merchant\":false},\"nearlyLinkmen\":[{\"status\":1,\"temp\":false,\"id\":\"1000015\",\"nickName\":null,\"address\":\"\",\"mobile\":\"15588329030\",\"contractor\":null,\"shopName\":null,\"mobileValid\":true,\"realNameValid\":false,\"thumb\":\"http://static.w.cn/images/member/photo.png\",\"email\":\"\",\"merchant\":false},{\"status\":1,\"temp\":false,\"id\":\"11223344\",\"nickName\":\"敏了个敏\",\"address\":\"霞飞路\",\"mobile\":\"13859985404\",\"contractor\":null,\"shopName\":null,\"mobileValid\":true,\"realNameValid\":false,\"thumb\":\"http://img5.wcnimg.com/M00/4C/AE/wKgKYlO_jtTwFH7FAABH955dNLM371-100-100.jpg\",\"email\":\"315095859@qq.com\",\"merchant\":false}],\"offlineMessages\":[{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"啊啊啊啊\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"噢噢噢噢\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"fk\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"wolegequa\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"你是我的小呀小苹果\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"ahbcd\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"表情你妹啊\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"我我我我\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"smsbsbm\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"wo lj asdjf \",\"clientType\":null}]}";

        ConnectResponseMessage connectResponseMessage = mapperCreator.mapper().readValue(connectJson, ConnectResponseMessage.class);
        print(connectResponseMessage);

        String hex = "7B224074797065223A22436F6E6E656374222C22636C69656E7454797065223A2257696E466F726D222C226C6F67696E4964223A223133363232383832393239222C226D65737361676554797065223A22436F6E6E656374222C22726563656976656454696D65223A302E302C2273656E6454696D65223A313430363031343837323338392E302C22746F6B656E223A223938346138653963366663373438636462333337303731636130386338313534227D0A";
        System.out.print(hex.length() / 2);


    }


    public void print(Object... o) {
        for (Object object : o) {
            print(object);
        }
    }

    public void print(Object o) {
        try {
            System.out.println(mapperCreator.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(o));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void main() throws UnsupportedEncodingException {
        System.out.println("1100000108".getBytes("UTF-8").length);
        byte[] head = "1100000108".getBytes("UTF-8");
        for (byte b : head) {
            System.out.print(String.format("%X", b) + " ");
        }
        byte[] head2 = new byte[10];
        head2[0] = 11;
        head2[9] = 108;
        System.out.println();
        for (byte b : head2) {
            System.out.print(String.format("%X", b) + " ");
        }
        byte[] header = new byte[4];
        String json = "{ \"a\" : \"b\"}";
        byte[] body = json.getBytes("utf-8");
        int lengh = body.length;


    }
}
