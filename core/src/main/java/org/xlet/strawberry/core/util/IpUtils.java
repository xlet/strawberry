package org.xlet.strawberry.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ip utils.
 */
public class IpUtils {

    public static boolean isIp(String s) {
        String ipRegex = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(ipRegex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * is ip a inner ip.
     *
     * @param ip ip.
     * @return true:inner ip.
     */
    public static boolean isInner(String ip) {
        if (!IpUtils.isIp(ip)) {
            throw new IllegalArgumentException("ip:" + ip + " is not ip string.");
        }

        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");

        long ipNum = getIpNum(ip);

        return isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || ip.equals("127.0.0.1");
    }

    private static boolean isInner(long ip, long begin, long end) {
        return (ip >= begin) && (ip <= end);
    }


    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        return (a << 24) + (b << 16) + (c << 8) + d;
    }

    /**
     * get local host lan address.
     *
     * @return lan ip address.
     * @throws UnknownHostException
     */
    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            for (Enumeration interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
                NetworkInterface anInterface = (NetworkInterface) interfaces.nextElement();
                for (Enumeration addresses = anInterface.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) addresses.nextElement();
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
