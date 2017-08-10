package com.ksyun.media.streamer.util;

/* compiled from: Hex */
public class e {
    private static final char[] a;
    private static final char[] b;

    static {
        a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public static char[] a(byte[] bArr) {
        return a(bArr, true);
    }

    public static char[] a(byte[] bArr, boolean z) {
        return a(bArr, z ? a : b);
    }

    protected static char[] a(byte[] bArr, char[] cArr) {
        int i = 0;
        int length = bArr.length;
        char[] cArr2 = new char[(length << 1)];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return cArr2;
    }

    public static String b(byte[] bArr) {
        return b(bArr, true);
    }

    public static String b(byte[] bArr, boolean z) {
        return b(bArr, z ? a : b);
    }

    protected static String b(byte[] bArr, char[] cArr) {
        return new String(a(bArr, cArr));
    }

    public static byte[] a(char[] cArr) {
        int i = 0;
        int length = cArr.length;
        if ((length & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] bArr = new byte[(length >> 1)];
        int i2 = 0;
        while (i < length) {
            i++;
            i++;
            bArr[i2] = (byte) (((a(cArr[i], i) << 4) | a(cArr[i], i)) & 255);
            i2++;
        }
        return bArr;
    }

    protected static int a(char c, int i) {
        int digit = Character.digit(c, 16);
        if (digit != -1) {
            return digit;
        }
        throw new RuntimeException("Illegal hexadecimal character " + c + " at index " + i);
    }
}
