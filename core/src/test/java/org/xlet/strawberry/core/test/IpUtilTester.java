package org.xlet.strawberry.core.test;

import org.xlet.strawberry.core.util.IpUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ip utils test.
 */
public class IpUtilTester {

    @Test
    public void test_ip_format() {
        String ip = "10.11.1.1";
        Assertions.assertThat(IpUtils.isIp(ip)).isEqualTo(true);

        ip = "192.168.1.1";
        assertThat(IpUtils.isIp(ip)).isEqualTo(true);

        ip = "2345.566";
        assertThat(IpUtils.isIp(ip)).isEqualTo(false);

        ip = "256.256.256.256";
        assertThat(IpUtils.isIp(ip)).isEqualTo(false);
    }

    @Test
    public void test_inner_ip() {
        String ip = "10.0.40.18";
        assertThat(IpUtils.isInner(ip)).isEqualTo(true);

        ip = "192.168.40.18";
        assertThat(IpUtils.isInner(ip)).isEqualTo(true);

        ip = "172.16.40.38";
        assertThat(IpUtils.isInner(ip)).isEqualTo(true);

        ip = "192.161.39.225";
        assertThat(IpUtils.isInner(ip)).isEqualTo(false);
    }
}
