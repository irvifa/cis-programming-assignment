/**
 * @author Rauhil Fahmi (1206208145)
 * @author Irvi Firqotul Aini (1306463591)
 * 
 * 
 * @version 2016/04/24
 */

package cis.assignment.lib;

import cis.assignment.commons.Utils;
import java.util.Arrays;

public class AES {
    public int traceLevel = 0;
    public String traceInfo = "";
    public static final int ROUNDS = 14;
    public static final int BLOCK_SIZE = 16;
    public static final int KEY_LENGTH = 32;
    int numRounds;
    byte[][] Ke;
    byte[][] Kd;
    static final byte[] S;
    static final byte[] Si;
    static final byte[] rcon;
    public static final int COL_SIZE = 4;
    public static final int NUM_COLS = 4;
    public static final int ROOT = 283;
    static final int[] row_shift;
    static final int[] alog;
    static final int[] log;

    public static int getRounds(int n) {
        switch (n) {
            case 16: {
                return 10;
            }
            case 24: {
                return 12;
            }
        }
        return 14;
    }

    static final int mul(int n, int n2) {
        return n != 0 && n2 != 0 ? alog[(log[n & 255] + log[n2 & 255]) % 255] : 0;
    }

    public static void trace_static() {
        int n;
        int n2;
        System.out.print("AES Static Tables\n");
        System.out.print("S[] = \n");
        for (n = 0; n < 16; ++n) {
            for (n2 = 0; n2 < 16; ++n2) {
                System.out.print(Utils.toHEX1(S[n * 16 + n2]) + ", ");
            }
            System.out.println();
        }
        System.out.print("Si[] = \n");
        for (n = 0; n < 16; ++n) {
            for (n2 = 0; n2 < 16; ++n2) {
                System.out.print(Utils.toHEX1(Si[n * 16 + n2]) + ", ");
            }
            System.out.println();
        }
        System.out.print("rcon[] = \n");
        for (n = 0; n < 5; ++n) {
            for (n2 = 0; n2 < 6; ++n2) {
                System.out.print(Utils.toHEX1(rcon[n * 6 + n2]) + ", ");
            }
            System.out.println();
        }
        System.out.print("log[] = \n");
        for (n = 0; n < 32; ++n) {
            for (n2 = 0; n2 < 8; ++n2) {
                System.out.print(Utils.toHEX1(log[n * 8 + n2]) + ", ");
            }
            System.out.println();
        }
        System.out.print("alog[] = \n");
        for (n = 0; n < 32; ++n) {
            for (n2 = 0; n2 < 8; ++n2) {
                System.out.print(Utils.toHEX1(alog[n * 8 + n2]) + ", ");
            }
            System.out.println();
        }
    }

    public byte[] encrypt(byte[] arrby) {
        int n;
        int n2;
        int n3;
        byte[] arrby2 = new byte[16];
        byte[] arrby3 = new byte[16];
        this.traceInfo = "";
        if (this.traceLevel > 0) {
            this.traceInfo = "encryptAES(" + Utils.toHEX1(arrby) + ")";
        }
        if (arrby == null) {
            throw new IllegalArgumentException("Empty plaintext");
        }
        if (arrby.length != 16) {
            throw new IllegalArgumentException("Incorrect plaintext length");
        }
        byte[] arrby4 = this.Ke[0];
        for (n3 = 0; n3 < 16; ++n3) {
            arrby2[n3] = (byte)(arrby[n3] ^ arrby4[n3]);
        }
        if (this.traceLevel > 2) {
            this.traceInfo = this.traceInfo + "\n  R0 (Key = " + Utils.toHEX1(arrby4) + ")\n\tAK = " + Utils.toHEX1(arrby2);
        } else if (this.traceLevel > 1) {
            this.traceInfo = this.traceInfo + "\n  R0 (Key = " + Utils.toHEX1(arrby4) + ")\t = " + Utils.toHEX1(arrby2);
        }
        for (int i = 1; i < this.numRounds; ++i) {
            arrby4 = this.Ke[i];
            if (this.traceLevel > 1) {
                this.traceInfo = this.traceInfo + "\n  R" + i + " (Key = " + Utils.toHEX1(arrby4) + ")\t";
            }
            for (n3 = 0; n3 < 16; ++n3) {
                arrby3[n3] = S[arrby2[n3] & 255];
            }
            if (this.traceLevel > 2) {
                this.traceInfo = this.traceInfo + "\n\tSB = " + Utils.toHEX1(arrby3);
            }
            for (n3 = 0; n3 < 16; ++n3) {
                n2 = n3 % 4;
                n = (n3 + row_shift[n2] * 4) % 16;
                arrby2[n3] = arrby3[n];
            }
            if (this.traceLevel > 2) {
                this.traceInfo = this.traceInfo + "\n\tSR = " + Utils.toHEX1(arrby2);
            }
            for (int j = 0; j < 4; ++j) {
                n3 = j * 4;
                arrby3[n3] = (byte)(AES.mul(2, arrby2[n3]) ^ AES.mul(3, arrby2[n3 + 1]) ^ arrby2[n3 + 2] ^ arrby2[n3 + 3]);
                arrby3[n3 + 1] = (byte)(arrby2[n3] ^ AES.mul(2, arrby2[n3 + 1]) ^ AES.mul(3, arrby2[n3 + 2]) ^ arrby2[n3 + 3]);
                arrby3[n3 + 2] = (byte)(arrby2[n3] ^ arrby2[n3 + 1] ^ AES.mul(2, arrby2[n3 + 2]) ^ AES.mul(3, arrby2[n3 + 3]));
                arrby3[n3 + 3] = (byte)(AES.mul(3, arrby2[n3]) ^ arrby2[n3 + 1] ^ arrby2[n3 + 2] ^ AES.mul(2, arrby2[n3 + 3]));
            }
            if (this.traceLevel > 2) {
                this.traceInfo = this.traceInfo + "\n\tMC = " + Utils.toHEX1(arrby3);
            }
            for (n3 = 0; n3 < 16; ++n3) {
                arrby2[n3] = (byte)(arrby3[n3] ^ arrby4[n3]);
            }
            if (this.traceLevel > 2) {
                this.traceInfo = this.traceInfo + "\n\tAK";
            }
            if (this.traceLevel <= 1) continue;
            this.traceInfo = this.traceInfo + " = " + Utils.toHEX1(arrby2);
        }
        arrby4 = this.Ke[this.numRounds];
        if (this.traceLevel > 1) {
            this.traceInfo = this.traceInfo + "\n  R" + this.numRounds + " (Key = " + Utils.toHEX1(arrby4) + ")\t";
        }
        for (n3 = 0; n3 < 16; ++n3) {
            arrby2[n3] = S[arrby2[n3] & 255];
        }
        if (this.traceLevel > 2) {
            this.traceInfo = this.traceInfo + "\n\tSB = " + Utils.toHEX1(arrby2);
        }
        for (n3 = 0; n3 < 16; ++n3) {
            n2 = n3 % 4;
            n = (n3 + row_shift[n2] * 4) % 16;
            arrby3[n3] = arrby2[n];
        }
        if (this.traceLevel > 2) {
            this.traceInfo = this.traceInfo + "\n\tSR = " + Utils.toHEX1(arrby2);
        }
        for (n3 = 0; n3 < 16; ++n3) {
            arrby2[n3] = (byte)(arrby3[n3] ^ arrby4[n3]);
        }
        if (this.traceLevel > 2) {
            this.traceInfo = this.traceInfo + "\n\tAK";
        }
        if (this.traceLevel > 1) {
            this.traceInfo = this.traceInfo + " = " + Utils.toHEX1(arrby2) + "\n";
        }
        if (this.traceLevel > 0) {
            this.traceInfo = this.traceInfo + " = " + Utils.toHEX1(arrby2) + "\n";
        }
        return arrby2;
    }

    public byte[] decrypt(byte[] arrby) {
        int n;
        int n2;
        int n3;
        byte[] arrby2 = new byte[16];
        byte[] arrby3 = new byte[16];
        this.traceInfo = "";
        if (this.traceLevel > 0) {
            this.traceInfo = "decryptAES(" + Utils.toHEX1(arrby) + ")";
        }
        if (arrby == null) {
            throw new IllegalArgumentException("Empty ciphertext");
        }
        if (arrby.length != 16) {
            throw new IllegalArgumentException("Incorrect ciphertext length");
        }
        byte[] arrby4 = this.Kd[0];
        for (n3 = 0; n3 < 16; ++n3) {
            arrby2[n3] = (byte)(arrby[n3] ^ arrby4[n3]);
        }
        if (this.traceLevel > 2) {
            this.traceInfo = this.traceInfo + "\n  R0 (Key = " + Utils.toHEX1(arrby4) + ")\n\t AK = " + Utils.toHEX1(arrby2);
        } else if (this.traceLevel > 1) {
            this.traceInfo = this.traceInfo + "\n  R0 (Key = " + Utils.toHEX1(arrby4) + ")\t = " + Utils.toHEX1(arrby2);
        }
        for (int i = 1; i < this.numRounds; ++i) {
            arrby4 = this.Kd[i];
            if (this.traceLevel > 1) {
                this.traceInfo = this.traceInfo + "\n  R" + i + " (Key = " + Utils.toHEX1(arrby4) + ")\t";
            }
            for (n3 = 0; n3 < 16; ++n3) {
                n2 = n3 % 4;
                n = (n3 + 16 - row_shift[n2] * 4) % 16;
                arrby3[n3] = arrby2[n];
            }
            if (this.traceLevel > 2) {
                this.traceInfo = this.traceInfo + "\n\tISR = " + Utils.toHEX1(arrby3);
            }
            for (n3 = 0; n3 < 16; ++n3) {
                arrby2[n3] = Si[arrby3[n3] & 255];
            }
            if (this.traceLevel > 2) {
                this.traceInfo = this.traceInfo + "\n\tISB = " + Utils.toHEX1(arrby2);
            }
            for (n3 = 0; n3 < 16; ++n3) {
                arrby3[n3] = (byte)(arrby2[n3] ^ arrby4[n3]);
            }
            if (this.traceLevel > 2) {
                this.traceInfo = this.traceInfo + "\n\t AK = " + Utils.toHEX1(arrby3);
            }
            for (int j = 0; j < 4; ++j) {
                n3 = j * 4;
                arrby2[n3] = (byte)(AES.mul(14, arrby3[n3]) ^ AES.mul(11, arrby3[n3 + 1]) ^ AES.mul(13, arrby3[n3 + 2]) ^ AES.mul(9, arrby3[n3 + 3]));
                arrby2[n3 + 1] = (byte)(AES.mul(9, arrby3[n3]) ^ AES.mul(14, arrby3[n3 + 1]) ^ AES.mul(11, arrby3[n3 + 2]) ^ AES.mul(13, arrby3[n3 + 3]));
                arrby2[n3 + 2] = (byte)(AES.mul(13, arrby3[n3]) ^ AES.mul(9, arrby3[n3 + 1]) ^ AES.mul(14, arrby3[n3 + 2]) ^ AES.mul(11, arrby3[n3 + 3]));
                arrby2[n3 + 3] = (byte)(AES.mul(11, arrby3[n3]) ^ AES.mul(13, arrby3[n3 + 1]) ^ AES.mul(9, arrby3[n3 + 2]) ^ AES.mul(14, arrby3[n3 + 3]));
            }
            if (this.traceLevel > 2) {
                this.traceInfo = this.traceInfo + "\n\tIMC";
            }
            if (this.traceLevel <= 1) continue;
            this.traceInfo = this.traceInfo + " = " + Utils.toHEX1(arrby2);
        }
        arrby4 = this.Kd[this.numRounds];
        if (this.traceLevel > 1) {
            this.traceInfo = this.traceInfo + "\n  R" + this.numRounds + " (Key = " + Utils.toHEX1(arrby4) + ")\t";
        }
        for (n3 = 0; n3 < 16; ++n3) {
            n2 = n3 % 4;
            n = (n3 + 16 - row_shift[n2] * 4) % 16;
            arrby3[n3] = arrby2[n];
        }
        if (this.traceLevel > 2) {
            this.traceInfo = this.traceInfo + "\n\tISR = " + Utils.toHEX1(arrby2);
        }
        for (n3 = 0; n3 < 16; ++n3) {
            arrby3[n3] = Si[arrby3[n3] & 255];
        }
        if (this.traceLevel > 2) {
            this.traceInfo = this.traceInfo + "\n\tISB = " + Utils.toHEX1(arrby2);
        }
        for (n3 = 0; n3 < 16; ++n3) {
            arrby2[n3] = (byte)(arrby3[n3] ^ arrby4[n3]);
        }
        if (this.traceLevel > 2) {
            this.traceInfo = this.traceInfo + "\n\t AK";
        }
        if (this.traceLevel > 1) {
            this.traceInfo = this.traceInfo + " = " + Utils.toHEX1(arrby2) + "\n";
        }
        if (this.traceLevel > 0) {
            this.traceInfo = this.traceInfo + " = " + Utils.toHEX1(arrby2) + "\n";
        }
        return arrby2;
    }

    public void setKey(byte[] arrby) {
        int n;
        int n2;
        int n3 = arrby.length;
        int n4 = n3 / 4;
        this.traceInfo = "";
        if (this.traceLevel > 0) {
            this.traceInfo = "setKey(" + Utils.toHEX1(arrby) + ")\n";
        }
        if (arrby == null) {
            throw new IllegalArgumentException("Empty key");
        }
        if (arrby.length != 16 && arrby.length != 24 && arrby.length != 32) {
            throw new IllegalArgumentException("Incorrect key length");
        }
        this.numRounds = AES.getRounds(n3);
        int n5 = (this.numRounds + 1) * 4;
        byte[] arrby2 = new byte[n5];
        byte[] arrby3 = new byte[n5];
        byte[] arrby4 = new byte[n5];
        byte[] arrby5 = new byte[n5];
        this.Ke = new byte[this.numRounds + 1][16];
        this.Kd = new byte[this.numRounds + 1][16];
        int n6 = 0;
        for (n = 0; n < n4; ++n) {
            arrby2[n] = arrby[n6++];
            arrby3[n] = arrby[n6++];
            arrby4[n] = arrby[n6++];
            arrby5[n] = arrby[n6++];
        }
        for (n = n4; n < n5; ++n) {
            byte by = arrby2[n - 1];
            byte by2 = arrby3[n - 1];
            byte by3 = arrby4[n - 1];
            byte by4 = arrby5[n - 1];
            if (n % n4 == 0) {
                byte by5 = by;
                by = (byte)(S[by2 & 255] ^ rcon[n / n4]);
                by2 = S[by3 & 255];
                by3 = S[by4 & 255];
                by4 = S[by5 & 255];
            } else if (n4 > 6 && n % n4 == 4) {
                by = S[by & 255];
                by2 = S[by2 & 255];
                by3 = S[by3 & 255];
                by4 = S[by4 & 255];
            }
            arrby2[n] = (byte)(arrby2[n - n4] ^ by);
            arrby3[n] = (byte)(arrby3[n - n4] ^ by2);
            arrby4[n] = (byte)(arrby4[n - n4] ^ by3);
            arrby5[n] = (byte)(arrby5[n - n4] ^ by4);
        }
        n = 0;
        for (n2 = 0; n2 < this.numRounds + 1; ++n2) {
            for (n6 = 0; n6 < 4; ++n6) {
                this.Ke[n2][4 * n6] = arrby2[n];
                this.Ke[n2][4 * n6 + 1] = arrby3[n];
                this.Ke[n2][4 * n6 + 2] = arrby4[n];
                this.Ke[n2][4 * n6 + 3] = arrby5[n];
                this.Kd[this.numRounds - n2][4 * n6] = arrby2[n];
                this.Kd[this.numRounds - n2][4 * n6 + 1] = arrby3[n];
                this.Kd[this.numRounds - n2][4 * n6 + 2] = arrby4[n];
                this.Kd[this.numRounds - n2][4 * n6 + 3] = arrby5[n];
                ++n;
            }
        }
        if (this.traceLevel > 3) {
            this.traceInfo = this.traceInfo + "  Encrypt Round keys:\n";
            for (n2 = 0; n2 < this.numRounds + 1; ++n2) {
                this.traceInfo = this.traceInfo + "  R" + n2 + "\t = " + Utils.toHEX1(this.Ke[n2]) + "\n";
            }
            this.traceInfo = this.traceInfo + "  Decrypt Round keys:\n";
            for (n2 = 0; n2 < this.numRounds + 1; ++n2) {
                this.traceInfo = this.traceInfo + "  R" + n2 + "\t = " + Utils.toHEX1(this.Kd[n2]) + "\n";
            }
        }
    }

    public static void self_test(String string, String string2, String string3, int n) {
        byte[] arrby = Utils.hex2byte(string);
        byte[] arrby2 = Utils.hex2byte(string2);
        byte[] arrby3 = Utils.hex2byte(string3);
        AES aES = new AES();
        aES.traceLevel = n;
        aES.setKey(arrby);
        System.out.print(aES.traceInfo);
        byte[] arrby4 = aES.encrypt(arrby2);
        System.out.print(aES.traceInfo);
        if (Arrays.equals(arrby4, arrby3)) {
            System.out.print("Test OK\n");
        } else {
            System.out.print("Test Failed. Result was " + Utils.toHEX(arrby4) + "\n");
        }
        arrby4 = aES.decrypt(arrby3);
        System.out.print(aES.traceInfo);
        if (Arrays.equals(arrby4, arrby2)) {
            System.out.print("Test OK\n");
        } else {
            System.out.print("Test Failed. Result was " + Utils.toHEX(arrby4) + "\n");
        }
        System.out.println();
    }

    static {
        int n;
        S = new byte[]{99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, 24, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117, 9, -125, 44, 26, 27, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
        Si = new byte[]{82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, 16, 89, 39, -128, -20, 95, 96, 81, 127, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
        rcon = new byte[]{0, 1, 2, 4, 8, 16, 32, 64, -128, 27, 54, 108, -40, -85, 77, -102, 47, 94, -68, 99, -58, -105, 53, 106, -44, -77, 125, -6, -17, -59, -111};
        row_shift = new int[]{0, 1, 2, 3};
        alog = new int[256];
        log = new int[256];
        AES.alog[0] = 1;
        for (n = 1; n < 256; ++n) {
            int n2 = alog[n - 1] << 1 ^ alog[n - 1];
            if ((n2 & 256) != 0) {
                n2 ^= 283;
            }
            AES.alog[n] = n2;
        }
        n = 1;
        while (n < 255) {
            AES.log[AES.alog[n]] = n++;
        }
    }
}
