/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.cis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;

/**
 *
 * @author RAUHIL
 */
class XTSAES {
    private static final int blockSize = 16;
    private static final int keyLength = 64;
    private static final byte[] nonce = Util.hex2byte("12345678901234567890123456789012");
    private static final int numberThreat = 100;
    private final String plain;
    private final String cipher;
    private final String key;
    private byte[] nonceDP = null;
    private byte[][] multiplyDP = null;

    public XTSAES(String key, String plain, String cipher) {
        this.plain = plain;
        this.key = key;
        this.cipher = cipher;
    }

    public void enkripsi(String key, String plain, String cipher) throws Exception {
        String read;
        try (BufferedReader br = new BufferedReader(new FileReader(key))) {
            read = br.readLine();
        }

        String key1 = read.substring(0, keyLength / 2);
        String key2 = read.substring(keyLength / 2, read.length());
        byte[] key11 = Util.hex2byte(key1);
        byte[] key22 = Util.hex2byte(key2);
        byte[] i = nonce;

        RandomAccessFile raf2;
        try (RandomAccessFile raf1 = new RandomAccessFile(plain, "r")) {
            raf2 = new RandomAccessFile(cipher, "rw");
            long fileSize = raf1.length();
            int m = (int) (fileSize / blockSize);
            int b = (int) (fileSize % blockSize);
            byte[][] bufferIn = new byte[m + 1][16];
            bufferIn[m] = new byte[b];
            for (byte[] bufferIn1 : bufferIn) {
                raf1.read(bufferIn1);
            }
            byte[][] bufferOut = new byte[m + 1][16];
            bufferOut[m] = new byte[b];
            AES aes = new AES();
            aes.setKey(key22);
            if (nonceDP == null) {
                nonceDP = aes.encrypt(i);
            }
            buildTable(nonceDP, m + 1);
            Thread[] worker = new Thread[numberThreat];
            for (int a = 0; a <= m - 2; a++) {
                worker[a % numberThreat] = new Thread(new WorkerThread(WorkerThread.ENCRYPT, bufferOut[a], bufferIn[a], key11, key22, a, i));
                worker[a % numberThreat].start();
                if (a % numberThreat == numberThreat - 1) {
                    for (int aa = 0; aa < numberThreat; aa++) {
                        if (worker[aa] != null) {
                            worker[aa].join(0);
                        }
                    }
                }
            }
            for (int a = 0; a < numberThreat; a++) {
                if (worker[a] != null) {
                    worker[a].join(0);
                }
            }
            if (b == 0) {
                perBlockEncrypt(bufferOut[m - 1], bufferIn[m - 1], key11, key22, m - 1, i);
                bufferOut[m] = new byte[0];
            } else {
                byte[] cc = new byte[blockSize];
                perBlockEncrypt(cc, bufferIn[m - 1], key11, key22, m - 1, i);
                System.arraycopy(cc, 0, bufferOut[m], 0, b);
                byte[] cp = new byte[16 - b];
                int ctr = 16 - b;
                int xx = cc.length - 1;
                int yy = cp.length - 1;
                while (ctr-- != 0) {
                    cp[yy--] = cc[xx--];
                }
                byte[] pp = new byte[16];
                System.arraycopy(bufferIn[m], 0, pp, 0, b);
                for (int a = b; a < pp.length; a++) {
                    pp[a] = cp[a - b];
                }
                perBlockEncrypt(bufferOut[m - 1], pp, key11, key22, m, i);
            }
            for (byte[] bufferOut1 : bufferOut) {
                raf2.write(bufferOut1);
            }
        }
        raf2.close();
    }

    public void dekripsi(String key, String plain, String cipher) throws Exception {
        String read;
        try (BufferedReader br = new BufferedReader(new FileReader(key))) {
            read = br.readLine();
        }

        String key1 = read.substring(0, keyLength / 2);
        String key2 = read.substring(keyLength / 2, read.length());
        byte[] key11 = Util.hex2byte(key1);
        byte[] key22 = Util.hex2byte(key2);
        byte[] i = nonce;

        RandomAccessFile raf2;
        try (RandomAccessFile raf1 = new RandomAccessFile(plain, "r")) {
            raf2 = new RandomAccessFile(cipher, "rw");
            long fileSize = raf1.length();
            int m = (int) (fileSize / blockSize);
            int b = (int) (fileSize % blockSize);
            byte[][] bufferIn = new byte[m + 1][16];
            bufferIn[m] = new byte[b];
            for (byte[] bufferIn1 : bufferIn) {
                raf1.read(bufferIn1);
            }
            byte[][] bufferOut = new byte[m + 1][16];
            bufferOut[m] = new byte[b];
            AES aes = new AES();
            aes.setKey(key22);
            if (nonceDP == null) {
                nonceDP = aes.encrypt(i);
            }
            buildTable(nonceDP, m + 1);
            Thread[] worker = new Thread[numberThreat];
            for (int a = 0; a <= m - 2; a++) {
                worker[a % numberThreat] = new Thread(new WorkerThread(WorkerThread.DECRYPT,bufferOut[a], bufferIn[a], key11, key22, a, i));
                worker[a % numberThreat].start();
                if (a % numberThreat == numberThreat - 1) {
                    for (int aa = 0; aa < numberThreat; aa++) {
                        if (worker[aa] != null) {
                            worker[aa].join(0);
                        }
                    }
                }
            }
            for (int a = 0; a < numberThreat; a++) {
                if (worker[a] != null) {
                    worker[a].join(0);
                }
            }
            if (b == 0) {
                perBlockDecrypt(bufferOut[m - 1], bufferIn[m - 1], key11, key22, m - 1, i);
                bufferOut[m] = new byte[0];
            } else {
                byte[] cc = new byte[blockSize];
                perBlockDecrypt(cc, bufferIn[m - 1], key11, key22, m, i);
                System.arraycopy(cc, 0, bufferOut[m], 0, b);
                byte[] cp = new byte[16 - b];
                int ctr = 16 - b;
                int xx = cc.length - 1;
                int yy = cp.length - 1;
                while (ctr-- != 0) {
                    cp[yy--] = cc[xx--];
                }
                byte[] pp = new byte[16];
                System.arraycopy(bufferIn[m], 0, pp, 0, b);
                for (int a = b; a < pp.length; a++) {
                    pp[a] = cp[a - b];
                }
                perBlockDecrypt(bufferOut[m - 1], pp, key11, key22, m - 1, i);
            }
            for (byte[] bufferOut1 : bufferOut) {
                raf2.write(bufferOut1);
            }
        }
        raf2.close();
    }

    public void perBlockEncrypt(byte[] ret, byte[] plain, byte[] key1, byte[] key2, int j, byte[] i) {
        AES aes = new AES();
        byte[] t = multiplyDP[j];
        byte[] pp = new byte[blockSize];
        for (int a = 0; a < pp.length; a++) {
            pp[a] = (byte) (plain[a] ^ t[a]);
        }
        aes = new AES();
        aes.setKey(key1);
        byte[] cc = aes.encrypt(pp);
        for (int a = 0; a < ret.length; a++) {
            ret[a] = (byte) (cc[a] ^ t[a]);
        }
    }

    public void perBlockDecrypt(byte[] ret, byte[] cipher, byte[] key1, byte[] key2, int j, byte[] i) {
        AES aes = new AES();
        byte[] t = multiplyDP[j];
        byte[] cc = new byte[blockSize];
        for (int a = 0; a < cc.length; a++) {
            cc[a] = (byte) (cipher[a] ^ t[a]);
        }
        aes = new AES();
        aes.setKey(key1);
        byte[] pp = aes.decrypt(cc);
        for (int a = 0; a < ret.length; a++) {
            ret[a] = (byte) (pp[a] ^ t[a]);
        }
    }

    private void buildTable(byte[] a, int numBlock) {
        multiplyDP = new byte[numBlock][blockSize];
        multiplyDP[0] = a;
        for (int i = 1; i < numBlock; i++) {
            multiplyDP[i][0] = (byte) ((2 * (multiplyDP[i - 1][0] % 128)) ^ (135 * (multiplyDP[i - 1][15] / 128)));
            for (int k = 1; k < 16; k++) {
                multiplyDP[i][k] = (byte) ((2 * (multiplyDP[i - 1][k] % 128)) ^ (multiplyDP[i - 1][k - 1] / 128));
            }
        }
    }

    class WorkerThread implements Runnable {
        public static final int ENCRYPT = 0;
        public static final int DECRYPT = 1;
        private final int mode;
        private final byte[] dest;
        private final byte[] source;
        private final byte[] key1;
        private final byte[] key2;
        private final int j;
        private final byte[] i;

        public WorkerThread(int mode, byte[] dest, byte[] source, byte[] key1,byte[] key2, int j, byte[] i) {
            this.mode = mode;
            this.dest = dest;
            this.source = source;
            this.key1 = key1;
            this.key2 = key2;
            this.j = j;
            this.i = i;
        }

        @Override
        public void run() {
            switch (this.mode) {
                case ENCRYPT:
                    perBlockEncrypt(dest, source, key1, key2, j, i);
                    break;
                case DECRYPT:
                    perBlockDecrypt(dest, source, key1, key2, j, i);
                    break;
            }
        }
    }
}
