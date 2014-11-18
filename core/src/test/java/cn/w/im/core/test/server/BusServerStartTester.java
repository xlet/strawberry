package cn.w.im.core.test.server;

import cn.w.im.core.server.AbstractServer;
import cn.w.im.core.server.BusServer;
import org.junit.Test;

/**
 *
 */
public class BusServerStartTester {

    private static final int PORT = 16021;

    @Test
    public void test_bus_server_start() {
        AbstractServer server = new BusServer(PORT);
        server.start();
    }
}

