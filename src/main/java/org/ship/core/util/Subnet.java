package org.ship.core.util;

import com.google.common.base.Objects;

/**
 * Created by wx on 2017/5/5.
 */
public class Subnet {
    private int netAddr;
    private int mask;

    public Subnet(String netAddr, String mask) throws Exception {
        this.netAddr = IPv4.toIPv4Address(netAddr);
        this.mask = IPv4.toIPv4Address(mask);

        int testWildcard = 32 - Integer.bitCount(this.mask);

        if  (this.mask != (int)(0xFFFFFFFFL << testWildcard)) {
            throw new Exception("network mask is not valid");
        }
        this.netAddr = this.netAddr & this.mask;
    }

    // 检查子网是否包含ip, 包括网络地址与广播地址
    public boolean containIp(String ip) {
        int nIp = IPv4.toIPv4Address(ip);

        int broadAddr = netAddr | ~mask;
        return Integer.compareUnsigned(nIp, netAddr) >= 0 && Integer.compareUnsigned(nIp, broadAddr) <= 0;
    }

    // 检查子网是否包含ip, 不包括网络地址与广播地址
    public boolean includeIp(String ip) {
        int nIp = IPv4.toIPv4Address(ip);

        int broadAddr = netAddr | ~mask;
        return Integer.compareUnsigned(nIp, netAddr) > 0 && Integer.compareUnsigned(nIp, broadAddr) < 0;
    }

    // 该子网是否包含其它子网或被其它子网包含
    public boolean intersect(Subnet o) {
        int wildCount = 32 - Integer.bitCount(mask);
        int oWildCount = 32 - Integer.bitCount(o.mask);

        if (wildCount > oWildCount) {
            if ((o.netAddr & mask) == netAddr) {
                return  true;
            }
        } else {
            if ((netAddr & o.mask) == o.netAddr) {
                return true;
            }
        }
        return false;
    }

    public boolean isClassDE() {
        int firstByte = netAddr >>> 24;
        return firstByte >= 224;
    }

    public  boolean isLocalLoop() {
        int firstByte = netAddr >>> 24;
        return firstByte == 127;
    }

    public String getNetAddr() {
        return IPv4.fromIPv4Address(netAddr);
    }

    public String getBroadcastAddr() {
        return IPv4.fromIPv4Address(netAddr | ~mask);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subnet subnet = (Subnet) o;
        return netAddr == subnet.netAddr &&
                mask == subnet.mask;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(netAddr, mask);
    }
}
