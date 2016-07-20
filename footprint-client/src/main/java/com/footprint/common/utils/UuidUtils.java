package com.footprint.common.utils;

import java.util.UUID;

public class UuidUtils {

    public static byte[] uuidBytes() {
        UUID uuid = UUID.randomUUID();
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();

        byte[] uuidBytes = new byte[16];

        for (int i = 0; i < 8; i++) {
            uuidBytes[i] = (byte) (msb >>> 8 * (7 - i));
        }

        for (int i = 8; i < 16; i++) {
            uuidBytes[i] = (byte) (lsb >>> 8 * (7 - i));
        }

        return uuidBytes;
    }

    public static String uuidHexString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").toUpperCase();
    }

    public static void main(String[] args) {

    }

}
