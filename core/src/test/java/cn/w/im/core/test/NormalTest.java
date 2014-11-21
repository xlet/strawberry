package cn.w.im.core.test;

import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.util.IpUtils;
import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

/**
 * normal function test.
 */
public class NormalTest {

    @Test
    public void test_enum_collection_contains() {
        Collection<MessageClientType> clientTypes = new CopyOnWriteArrayList<MessageClientType>();

        clientTypes.add(MessageClientType.Mobile);
        clientTypes.add(MessageClientType.Web);
        clientTypes.add(MessageClientType.WinForm);

        assertThat(clientTypes.contains(MessageClientType.Mobile)).isEqualTo(true);

    }

    @Test
    public void test_move_position() {
        long a = (long) (192. * 256 * 256 * 256 + 168 * 256 * 256 + 40 * 256 + 18);
        System.out.println(a);

        long c = 192L;
        long d = 168L;

        long b = (192L << 24) + (168L << 16) + (40L << 8) + 18L;
        System.out.println(b);

        assertThat(a).isEqualTo(b);
    }
}
