package org.xlet.strawberry.core.test.server;

import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.server.AbstractServer;
import org.xlet.strawberry.core.server.BusServer;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 *
 */
public class BusServerStartTester {

    private static final int PORT = 16021;

    @Test
    public void test_bus_server_start() throws UnknownHostException, ServerInnerException {
        AbstractServer server = new BusServer("bus.strawberry.xlet.cn", PORT);
        server.start();
    }
}

