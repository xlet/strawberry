package cn.w.im.core.test.server;

import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.core.server.BusServer;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 *
 */
public class BusServerStartTester {

    private static final int PORT = 16021;

    @Test
    public void test_bus_server_start() throws UnknownHostException, ServerInnerException {
        AbstractServer server = new BusServer("bus.im.w.cn", PORT);
        server.start();
    }
}

