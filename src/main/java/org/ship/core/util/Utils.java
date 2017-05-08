package org.ship.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wx on 2017/4/30.
 */
public class Utils {
    static public class ExecReturn {
        public int exitCode;
        public String stdout;
        public String stderr;

        public ExecReturn(int exitCode, String stdout, String stderr) {
            this.exitCode = exitCode;
            this.stdout = stdout;
            this.stderr = stderr;
        }

        @Override
        public String toString() {
            return "ExecReturn{" +
                    "exitCode='" + exitCode + '\'' +
                    ", stdout='" + stdout + '\'' +
                    ", stderr='" + stderr + '\'' +
                    '}';
        }
    }

    static public int zeroCount(long n) {
        long k;
        int count = 63;

        if (n == 0) {
            return 64;
        }

        k = n << 32;
        if (k != 0) {
            count -= 32;
            n = k;
        }

        k = n << 16;
        if (k != 0) {
            count -= 16;
            n = k;
        }

        k = n << 8;
        if (k != 0) {
            count -= 8;
            n = k;
        }

        k = n << 4;
        if (k != 0) {
            count -= 4;
            n = k;
        }

        k = n << 2;
        if (k != 0) {
            count -= 2;
            n = k;
        }

        k = n << 1;
        if (k != 0) {
            count -= 1;
            n = k;
        }

        return count;
    }

    // 一个正确的掩码高位部分应该全部为1，低位部分全部为0，例如：11111111000000000000000000000000（255.0.0.0）
    static public boolean checkMask(int mask) {
        int count = zeroCount(mask);
        return mask == -1 << count;
    }

    public static boolean checkIp(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        // 返回判断信息
        return false;
    }

    // 一个正确的掩码高位部分应该全部为1，低位部分全部为0，例如：11111111000000000000000000000000（255.0.0.0）
    static public void verifyMask(String mask) throws Exception {
        int nMask = IPv4.toIPv4Address(mask);
        int count = 32 - Integer.bitCount(nMask);
        if  (nMask != -1 << count) {
            throw new Exception("network mask is not valid");
        }
    }

    // 验证IP是否为回环地址或D, E类地址
    public static void verifyIp(String ip) throws Exception{
        int nIp = IPv4.toIPv4Address(ip);
        int firstByte = nIp >>> 24;
        if (firstByte == 0) {
            throw new Exception("ip is zero");
        }
        // loop address, class D and class E is not valid
        if (firstByte == 127) {
            throw new Exception("ip is loop address");
        }
        if (firstByte >= 224) {
            throw new Exception("ip is class D or E");
        }
        // ip不能落在保留子网内
        Subnet reserved = new Subnet("10.255.255.0", "255.255.255.0");
        if (reserved.includeIp(ip)) {
            throw new Exception("ip is reserved");
        }
    }

    public static void verifySubnet(String netAddr, String mask) throws Exception {
        Subnet subnet = new Subnet(netAddr, mask);
        if (subnet.isLocalLoop()) {
            throw new Exception("subnet is loop address");
        }
        if (subnet.isClassDE()) {
            throw new Exception("subnet is class D or E");
        }
        // 子网不能包含默认子网
        Subnet reserved = new Subnet("10.255.255.0", "255.255.255.0");
        if (subnet.intersect(reserved) && !subnet.equals(new Subnet("0.0.0.0", "0.0.0.0"))) {
            throw new Exception("subnet interchanges with reserved subnet");
        }
    }

    // 验证网关是否为子网的广播地址或网络地址
    public static void verifyGateway(String gateway, String subnet, String mask) throws Exception {
        // gateway must be a valid ip
        verifyIp(gateway);
        verifyMask(mask);
        long nGateway = IPv4.toIPv4Address(gateway) & 0xffffffffL;
        long nSubnet = IPv4.toIPv4Address(subnet) & 0xffffffffL;
        long nMask = IPv4.toIPv4Address(mask) & 0xffffffffL;
        long netAddr = nSubnet & nMask;
        if (nGateway == netAddr) {
            throw new Exception("gateway can't be subnet address");
        }
        int count = Integer.bitCount((int)nMask);
        int ipCount = 0xffffffff >>> count;
        if (nGateway == netAddr + ipCount) {
            throw new Exception("gateway can't be broadcast address");
        }
    }

    // execute a command and return
    static public ExecReturn exec(String[] cmd) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        Process process = rt.exec(cmd);
        process.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder stdoutBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stdoutBuilder.append('\n');
            stdoutBuilder.append(line);
        }
        br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder stderrBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            stderrBuilder.append('\n');
            stderrBuilder.append(line);
        }
        process.waitFor();
        return new ExecReturn(process.exitValue(), stdoutBuilder.toString(), stderrBuilder.toString());
    }

    public static int shiftMask(String mask) {
        StringBuffer sb;
        String str;
        int inetmask = 0;
        int count = 0;

        String[] ipSegment = mask.split("\\.");
        for (int n = 0; n < ipSegment.length; n++) {
            sb = toBin(Integer.parseInt(ipSegment[n]));
            str = sb.reverse().toString();
            count = 0;
            for (int i = 0; i < str.length(); i++) {
                i = str.indexOf("1", i);
                if (i == -1) {
                    break;
                }
                count++;
            }
            inetmask += count;
        }
        return inetmask;
    }

    public static StringBuffer toBin(int x) {
        StringBuffer result=new StringBuffer();
        result.append(x%2);
        x/=2;
        while(x>0){
            result.append(x%2);
            x/=2;
        }
        return result;
    }
}
