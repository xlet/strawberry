package cn.w.im.core.test;

import cn.w.im.core.MessageClientType;
import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.CopyOnWriteArrayList;

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
    public void test_get_local_ip_address() throws UnknownHostException {
        InetAddress localAddress = this.getLocalHostLANAddress();
        assertThat(localAddress.toString()).isEqualTo("10.0.40.38");
    }

    private InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                for (Enumeration inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (inetAddress.isSiteLocalAddress()) {
                            return inetAddress;
                        } else if (candidateAddress == null) {
                            candidateAddress = inetAddress;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }

            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address:" + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
