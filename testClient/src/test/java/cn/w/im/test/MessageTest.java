package cn.w.im.test;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.ErrorCodeDefine;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.*;
import cn.w.im.domains.messages.heartbeat.Heartbeat;
import cn.w.im.domains.messages.heartbeat.HeartbeatResponse;
import cn.w.im.server.DefaultTokenProvider;
import cn.w.im.server.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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

    private TokenProvider tokenProvider = new DefaultTokenProvider();

    private ByteBufAllocator allocator = new PooledByteBufAllocator();

    @Test
    public void gen_login_message() throws JsonProcessingException, UnsupportedEncodingException {

        LoginMessage loginMessage = new LoginMessage(CLIENT_TYPE, "13622882929", "w123456");
        String token = UUID.randomUUID().toString().replace("-", "");
        ServerBasic serverBasic = new ServerBasic();
        serverBasic.setHost("10.0.41.104");
        serverBasic.setNodeId("1");
        serverBasic.setPort(17021);
        serverBasic.setStart(true);
        serverBasic.setStartDateTime(System.currentTimeMillis());
        serverBasic.setServerType(ServerType.LoginServer);
        ConnectToken connectToken = new ConnectToken("10.0.40.18", "username", token, serverBasic);
        LoginResponseMessage loginSuccessResponseMessage = new LoginResponseMessage(connectToken);
        LoginResponseMessage loginFailResponseMessage = new LoginResponseMessage(ErrorCodeDefine.IDPASSWORDERRORCODE, "IDPASSWORDERRORCODE");
        print(loginMessage);
        print(loginSuccessResponseMessage);
        print(loginFailResponseMessage);
    }

    @Test
    public void gen_connect_message(){
        String token = "099e328eddc04f3ba0afe9895f5457ef";
        ConnectMessage connectMessage = new ConnectMessage(CLIENT_TYPE, "13622882929", token);
        print(connectMessage);
        ConnectResponseMessage connectResponseMessage = new ConnectResponseMessage();
        // connectResponseMessage.setSelf();
        ConnectResponseMessage connectFailResponse = new ConnectResponseMessage(ErrorCodeDefine.TOKENERROR, "TOKENERROR");
        print(connectResponseMessage, connectFailResponse);
    }

    @Test
    public void gen_heartbeat_message(){
        Heartbeat heartbeat = new Heartbeat(true);
        heartbeat.setSeq(1);
        HeartbeatResponse response = new HeartbeatResponse();
        response.setSeq(heartbeat.getSeq());

        print(heartbeat, response);
    }


    @Test
    public void gen_logout_message() {
        LogoutMessage logoutMessage = new LogoutMessage(CLIENT_TYPE, "11223344");
        print(logoutMessage);
        LogoutResponseMessage logoutResponseMessage = new LogoutResponseMessage(true);
        LogoutResponseMessage logoutFailResponseMessage = new LogoutResponseMessage(false);
        print(logoutResponseMessage, logoutFailResponseMessage);
    }



    @Test
    public void gen_normal_message() {
        NormalMessage message = new NormalMessage(CLIENT_TYPE, "one", "another", "消息内容");
        print(message);
    }


    @Test
    public void gen_get_profile_message(){
        GetProfileRequestMessage request = new GetProfileRequestMessage("11223344","13622882929");
        print(request);

        GetProfileResponseMessage response = new GetProfileResponseMessage();
        List<Member> members  = new ArrayList<Member>();
        Member member = new Member();
        member.setThumb("http://static.w.cn/images/member/photo.png");
        member.setNickName("溜溜");
        member.setMobileValid(true);
        member.setMobile("13859985404");
        member.setRealNameValid(false);
        member.setEmail("315095859@qq.com");
        member.setAddress("广州市黄埔区鱼珠物流基地外贸中心A区");
        member.setId("13622882929");
        members.add(member);
        member.setId("11223344");
        members.add(member);
        response.setMembers(members);
        print(response);
    }


    @Test
    public void decode() throws IOException {
        String json = "{\"@type\":\"Normal\",\"clientType\":\"WinForm\",\"content\":\"who's your daddy\",\"from\":\"1004462060\",\"messageType\":\"Normal\",\"receivedTime\":1131313130.0,\"sendTime\":1404292690015.0,\"to\":\"1\"}\r\n";
        for (byte b : json.getBytes("utf-8")) {
            System.out.print(String.format("%X", b) + " ");
        }
        NormalMessage message = mapper.readValue(json, NormalMessage.class);
        // System.out.println(message);
        print(message);

        String connectJson = "{\"@type\":\"ConnectResponse\",\"messageType\":\"ConnectResponse\",\"sendTime\":1405627690320,\"receivedTime\":0,\"success\":true,\"errorCode\":0,\"errorMessage\":null,\"self\":{\"status\":0,\"temp\":false,\"id\":\"11223344\",\"nickName\":\"敏了个敏\",\"address\":\"霞飞路198号\",\"mobile\":\"13859985404\",\"contractor\":null,\"shopName\":null,\"mobileValid\":true,\"realNameValid\":false,\"thumb\":\"http://img5.wcnimg.com/M00/4C/AE/wKgKYlO_jtTwFH7FAABH955dNLM371-100-100.jpg\",\"email\":\"315095859@qq.com\",\"merchant\":false},\"nearlyLinkmen\":[{\"status\":1,\"temp\":false,\"id\":\"1000015\",\"nickName\":null,\"address\":\"\",\"mobile\":\"15588329030\",\"contractor\":null,\"shopName\":null,\"mobileValid\":true,\"realNameValid\":false,\"thumb\":\"http://static.w.cn/images/member/photo.png\",\"email\":\"\",\"merchant\":false},{\"status\":1,\"temp\":false,\"id\":\"11223344\",\"nickName\":\"敏了个敏\",\"address\":\"霞飞路\",\"mobile\":\"13859985404\",\"contractor\":null,\"shopName\":null,\"mobileValid\":true,\"realNameValid\":false,\"thumb\":\"http://img5.wcnimg.com/M00/4C/AE/wKgKYlO_jtTwFH7FAABH955dNLM371-100-100.jpg\",\"email\":\"315095859@qq.com\",\"merchant\":false}],\"offlineMessages\":[{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"啊啊啊啊\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"噢噢噢噢\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"fk\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"wolegequa\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"你是我的小呀小苹果\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"ahbcd\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"表情你妹啊\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"[哼]\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"我我我我\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"smsbsbm\",\"clientType\":null},{\"@type\":\"Normal\",\"messageType\":\"Normal\",\"sendTime\":1405627690350,\"receivedTime\":0,\"from\":\"1000015\",\"to\":\"11223344\",\"content\":\"wo lj asdjf \",\"clientType\":null}]}";

        ConnectResponseMessage connectResponseMessage = mapper.readValue(connectJson, ConnectResponseMessage.class);
        print(connectResponseMessage);

        String hex = "7B224074797065223A22436F6E6E656374222C22636C69656E7454797065223A2257696E466F726D222C226C6F67696E4964223A223133363232383832393239222C226D65737361676554797065223A22436F6E6E656374222C22726563656976656454696D65223A302E302C2273656E6454696D65223A313430363031343837323338392E302C22746F6B656E223A223938346138653963366663373438636462333337303731636130386338313534227D0A";
        System.out.print(hex.length()/2);

    }

    public void print(Object... o) {
        for (Object object : o) {
            print(object);
        }
    }

    public void print(Object o) {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
            System.out.println(jsonToHexWithHeader(mapper.writeValueAsString(o)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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

    private String jsonToHexWithHeader(String json) throws UnsupportedEncodingException {
        ByteBuf byteBuf = allocator.directBuffer();
        byte[] content = json.getBytes("utf-8");
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);
        return ByteBufUtil.hexDump(byteBuf);
    }

}
