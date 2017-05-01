package org.ship.core.vo.node;

/**
 * Created by wx on 2017/5/1.
 */
public enum ConnType {
    TCP(0),
    HTTP(1);

    private final int value;

    private ConnType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ConnType findByValue(int value) {
        switch (value) {
            case 0:
                return TCP;
            case 1:
                return HTTP;
            default:
                return null;
        }
    }
}
