package com.yd.java.jvm.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 获取真实本机IP&主机名.
 *
 * @author Zeb灬D
 */
public class LocalHost {

    private static final Logger logger = LoggerFactory.getLogger(LocalHost.class);

    private static volatile String cachedIpAddress;

    private static volatile String cachedHostName;

    private static InetAddress getLocalHost() {
        InetAddress result = null;
        try {
            result = InetAddress.getLocalHost();
        } catch (Throwable ex) {
            //ErrorLogCollector.errorLog(logger, "get Localhost Error", ex);
        }
        return result;
    }

    /**
     * 获取本机IP地址.
     * 有限获取外网IP地址. 也有可能是链接着路由器的最终IP地址.
     *
     * @return 本机IP地址
     */
    public String getIp() {
        if (null != cachedIpAddress) {
            return cachedIpAddress;
        }

        String localIpAddress = null;

        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();

            localIpAddress = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = netInterfaces.nextElement();
                Enumeration<InetAddress> ipAddresses = netInterface.getInetAddresses();
                while (ipAddresses.hasMoreElements()) {
                    InetAddress ipAddress = ipAddresses.nextElement();
                    if (isPublicIpAddress(ipAddress)) {
                        String publicIpAddress = ipAddress.getHostAddress();
                        cachedIpAddress = publicIpAddress;
                        return publicIpAddress;
                    }
                    if (isLocalIpAddress(ipAddress)) {
                        localIpAddress = ipAddress.getHostAddress();
                    }
                }
            }
            cachedIpAddress = localIpAddress;
        } catch (Throwable ex) {
            //ErrorLogCollector.errorLog(logger, "localhost getIp Error", ex);
            localIpAddress = "127.0.0.1";
        }
        return localIpAddress;
    }

    /**
     * 获取本机Host名称.
     *
     * @return 本机Host名称
     */
    public String getHostName() {
        if (cachedHostName != null) {
            return cachedHostName;
        }

        try {
            cachedHostName = getLocalHost().getHostName();
        } catch (Exception e) {
            cachedHostName = "localhost";
            //ErrorLogCollector.errorLog(logger, "getHostName Fail", e);
        }
        return cachedHostName;
    }

    private boolean isPublicIpAddress(final InetAddress ipAddress) {
        return !ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private boolean isLocalIpAddress(final InetAddress ipAddress) {
        return ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private boolean isV6IpAddress(final InetAddress ipAddress) {
        return ipAddress.getHostAddress().contains(":");
    }
}
