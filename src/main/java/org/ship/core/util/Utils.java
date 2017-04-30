package org.ship.core.util;

/**
 * Created by wx on 2017/4/30.
 */
public class Utils {
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
}
