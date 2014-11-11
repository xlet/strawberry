package cn.w.im.testClient;

import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.message.client.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-16 上午10:24.
 * Summary: 客户端main Class.
 */
public class ClientStarter {

    private final static MessageClientType MESSAGE_CLIENT_TYPE = MessageClientType.WinForm;

    private final static ProductType PRODUCT_TYPE = ProductType.OA;

    private static Thread mainThread;

    private static Thread loginThread;

    private static Thread messageThread;

    private static final ClientStarter clientStarter = new ClientStarter();

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientStarter.class);

    /**
     * 启动主函数.
     *
     * @param args 启动参数.
     * @throws Exception 异常.
     */
    public static void main(String[] args) throws Exception {
        mainThread = Thread.currentThread();
        clientStarter.inputIdPassword();
        while (true) {
            mainThread.wait(200);
        }
    }

    private void inputIdPassword() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入用户名:");
            String id = sc.nextLine();
            System.out.println("请输入密码:");
            String password = sc.nextLine();
            StartLoginThread(id, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loginError() {
        LOGGER.info("登陆失败!,重新登陆:");
        inputIdPassword();
    }

    public void StartLoginThread(final String id, final String password) {
        loginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Login login = new Login(clientStarter, id, password, MESSAGE_CLIENT_TYPE, PRODUCT_TYPE);
                login.login();
            }
        });
        loginThread.start();
    }

    public void StartMessageThread(final String token, final String memberId, final ServerBasic allocatedMessageServer) {
        messageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                MessageConnector messageConnector = new MessageConnector(clientStarter, token, memberId, MESSAGE_CLIENT_TYPE,
                        PRODUCT_TYPE, allocatedMessageServer);
                messageConnector.connect();
            }
        });
        messageThread.start();
    }


    public void loginSuccess(String token, String memberId, ServerBasic allocatedMessageServer) {
        LOGGER.debug("login success.");
        StartMessageThread(token, memberId, allocatedMessageServer);
    }
}
