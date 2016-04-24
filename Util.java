/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.cis;

/**
 *
 * @author RAUHIL
 */
class Util {
    public static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    Util() {
    }

    public static byte[] short2byte(short[] arrs) {
        int n = arrs.length;
        byte[] arrby = new byte[n * 2];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            short s = arrs[n2++];
            arrby[n3++] = (byte)(s >>> 8 & 255);
            arrby[n3++] = (byte)(s & 255);
        }
        return arrby;
    }

    public static short[] byte2short(byte[] arrby) {
        int n = arrby.length;
        short[] arrs = new short[n / 2];
        int n2 = 0;
        int n3 = 0;
        while (n3 < n / 2) {
            arrs[n3++] = (short)((arrby[n2++] & 255) << 8 | arrby[n2++] & 255);
        }
        return arrs;
    }

    public static byte[] int2byte(int[] arrn) {
        int n = arrn.length;
        byte[] arrby = new byte[n * 4];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = arrn[n2++];
            arrby[n3++] = (byte)(n4 >>> 24 & 255);
            arrby[n3++] = (byte)(n4 >>> 16 & 255);
            arrby[n3++] = (byte)(n4 >>> 8 & 255);
            arrby[n3++] = (byte)(n4 & 255);
        }
        return arrby;
    }

    public static int[] byte2int(byte[] arrby) {
        int n = arrby.length;
        int[] arrn = new int[n / 4];
        int n2 = 0;
        int n3 = 0;
        while (n3 < n / 4) {
            arrn[n3++] = (arrby[n2++] & 255) << 24 | (arrby[n2++] & 255) << 16 | (arrby[n2++] & 255) << 8 | arrby[n2++] & 255;
        }
        return arrn;
    }

    public static String toHEX(byte[] arrby) {
        int n = arrby.length;
        char[] arrc = new char[n * 3];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            byte by = arrby[n2++];
            arrc[n3++] = HEX_DIGITS[by >>> 4 & 15];
            arrc[n3++] = HEX_DIGITS[by & 15];
            arrc[n3++] = 32;
        }
        return new String(arrc);
    }

    public static String toHEX(short[] arrs) {
        int n = arrs.length;
        char[] arrc = new char[n * 5];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            short s = arrs[n2++];
            arrc[n3++] = HEX_DIGITS[s >>> 12 & 15];
            arrc[n3++] = HEX_DIGITS[s >>> 8 & 15];
            arrc[n3++] = HEX_DIGITS[s >>> 4 & 15];
            arrc[n3++] = HEX_DIGITS[s & 15];
            arrc[n3++] = 32;
        }
        return new String(arrc);
    }

    public static String toHEX(int[] arrn) {
        int n = arrn.length;
        char[] arrc = new char[n * 10];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = arrn[n2++];
            arrc[n3++] = HEX_DIGITS[n4 >>> 28 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 24 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 20 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 16 & 15];
            arrc[n3++] = 32;
            arrc[n3++] = HEX_DIGITS[n4 >>> 12 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 8 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 4 & 15];
            arrc[n3++] = HEX_DIGITS[n4 & 15];
            arrc[n3++] = 32;
        }
        return new String(arrc);
    }

    public static String toHEX1(byte by) {
        char[] arrc = new char[2];
        int n = 0;
        arrc[n++] = HEX_DIGITS[by >>> 4 & 15];
        arrc[n++] = HEX_DIGITS[by & 15];
        return new String(arrc);
    }

    public static String toHEX1(byte[] arrby) {
        int n = arrby.length;
        char[] arrc = new char[n * 2];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            byte by = arrby[n2++];
            arrc[n3++] = HEX_DIGITS[by >>> 4 & 15];
            arrc[n3++] = HEX_DIGITS[by & 15];
        }
        return new String(arrc);
    }

    public static String toHEX1(short[] arrs) {
        int n = arrs.length;
        char[] arrc = new char[n * 4];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            short s = arrs[n2++];
            arrc[n3++] = HEX_DIGITS[s >>> 12 & 15];
            arrc[n3++] = HEX_DIGITS[s >>> 8 & 15];
            arrc[n3++] = HEX_DIGITS[s >>> 4 & 15];
            arrc[n3++] = HEX_DIGITS[s & 15];
        }
        return new String(arrc);
    }

    public static String toHEX1(int n) {
        char[] arrc = new char[8];
        int n2 = 0;
        arrc[n2++] = HEX_DIGITS[n >>> 28 & 15];
        arrc[n2++] = HEX_DIGITS[n >>> 24 & 15];
        arrc[n2++] = HEX_DIGITS[n >>> 20 & 15];
        arrc[n2++] = HEX_DIGITS[n >>> 16 & 15];
        arrc[n2++] = HEX_DIGITS[n >>> 12 & 15];
        arrc[n2++] = HEX_DIGITS[n >>> 8 & 15];
        arrc[n2++] = HEX_DIGITS[n >>> 4 & 15];
        arrc[n2++] = HEX_DIGITS[n & 15];
        return new String(arrc);
    }

    public static String toHEX1(int[] arrn) {
        int n = arrn.length;
        char[] arrc = new char[n * 8];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = arrn[n2++];
            arrc[n3++] = HEX_DIGITS[n4 >>> 28 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 24 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 20 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 16 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 12 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 8 & 15];
            arrc[n3++] = HEX_DIGITS[n4 >>> 4 & 15];
            arrc[n3++] = HEX_DIGITS[n4 & 15];
        }
        return new String(arrc);
    }

    public static byte[] hex2byte(String string) {
        int n = string.length();
        byte[] arrby = new byte[(n + 1) / 2];
        int n2 = 0;
        int n3 = 0;
        if (n % 2 == 1) {
            arrby[n3++] = (byte)Util.hexDigit(string.charAt(n2++));
        }
        while (n2 < n) {
            arrby[n3++] = (byte)(Util.hexDigit(string.charAt(n2++)) << 4 | Util.hexDigit(string.charAt(n2++)));
        }
        return arrby;
    }

    public static boolean isHex(String string) {
        int n = string.length();
        int n2 = 0;
        while (n2 < n) {
            char c;
            if ((c = string.charAt(n2++)) >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f') continue;
            return false;
        }
        return true;
    }

    public static int hexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        return 0;
    }
}
