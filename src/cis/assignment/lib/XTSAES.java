/**
 * @author Rauhil Fahmi (1206208145)
 * @author Irvi Firqotul Aini (1306463591)
 * 
 * 
 * @version 2016/04/24
 */

package cis.assignment.lib;

import cis.assignment.commons.Utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;

/**
 * Class XTSAES digunakan untuk melakukan enkripsi maupun dekripsi data 
 * dengan key tertentu
 */
public class XTSAES {
    //Variabel-variable yang akan digunakan dalam kelas
    private static final byte[] idx = Utils.hex2byte("12345678901234567890123456789012");
    private static final int numberThreat = 100;
    private final String plain;
    private final String cipher;
    private final String key;
    private byte[] idxx = null;
    private byte[][] multiply = null;

    //Contructor
    public XTSAES(String key, String plain, String cipher) {
        this.plain = plain;
        this.key = key;
        this.cipher = cipher;
    }
    
    /**
     * method enkripsi 
     *
     * @param key merupakan key yang digunakan untuk proses enkripsi
     * @param plain merupakan plaintext dari proses enkripsi
     * @param cipher merupakan hasil dari proses enkripsi
     * @throws Exception
     */
    public void enkripsi(String key, String plain, String cipher) throws Exception {
        String read;
        try (BufferedReader br = new BufferedReader(new FileReader(key))) {
            read = br.readLine();
        }

        String key1 = read.substring(0, 64 / 2);
        String key2 = read.substring(64 / 2, read.length());
        byte[] key11 = Utils.hex2byte(key1);
        byte[] key22 = Utils.hex2byte(key2);
        byte[] i = idx;

        RandomAccessFile raf2;
        try (RandomAccessFile raf1 = new RandomAccessFile(plain, "r")) {
            raf2 = new RandomAccessFile(cipher, "rw");
            long fileSize = raf1.length();
            int m = (int) (fileSize / 16);
            int b = (int) (fileSize % 16);
            byte[][] bufferIn = new byte[m + 1][16];
            bufferIn[m] = new byte[b];
            for (byte[] bufferIn1 : bufferIn) {
                raf1.read(bufferIn1);
            }
            byte[][] bufferOut = new byte[m + 1][16];
            bufferOut[m] = new byte[b];
            AES aes = new AES();
            aes.setKey(key22);
            if (idxx == null) {
                idxx= aes.encrypt(i);
            }
            buildTable(idxx, m + 1);
            Thread[] worker = new Thread[numberThreat];
            for (int a = 0; a <= m - 2; a++) {
                worker[a % numberThreat] = new Thread(new runThread(runThread.ENCRYPT, bufferOut[a], bufferIn[a], key11, key22, a, i));
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
                blockEncrypt(bufferOut[m - 1], bufferIn[m - 1], key11, key22, m - 1, i);
                bufferOut[m] = new byte[0];
            } else {
                byte[] cc = new byte[16];
                blockEncrypt(cc, bufferIn[m - 1], key11, key22, m - 1, i);
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
                blockEncrypt(bufferOut[m - 1], pp, key11, key22, m, i);
            }
            for (byte[] bufferOut1 : bufferOut) {
                raf2.write(bufferOut1);
            }
        }
        raf2.close();
    }
    
    /**
     * method dekripsi 
     *
     * @param key merupakan key yang digunakan untuk proses enkripsi
     * @param plain merupakan plaintext dari proses enkripsi
     * @param cipher merupakan hasil dari proses enkripsi
     * @throws Exception
     */
    public void dekripsi(String key, String plain, String cipher) throws Exception {
        String read;
        try (BufferedReader br = new BufferedReader(new FileReader(key))) {
            read = br.readLine();
        }

        String key1 = read.substring(0, 64 / 2);
        String key2 = read.substring(64 / 2, read.length());
        byte[] key11 = Utils.hex2byte(key1);
        byte[] key22 = Utils.hex2byte(key2);
        byte[] i = idx;

        RandomAccessFile raf2;
        try (RandomAccessFile raf1 = new RandomAccessFile(plain, "r")) {
            raf2 = new RandomAccessFile(cipher, "rw");
            long fileSize = raf1.length();
            int m = (int) (fileSize / 16);
            int b = (int) (fileSize % 16);
            byte[][] bufferIn = new byte[m + 1][16];
            bufferIn[m] = new byte[b];
            for (byte[] bufferIn1 : bufferIn) {
                raf1.read(bufferIn1);
            }
            byte[][] bufferOut = new byte[m + 1][16];
            bufferOut[m] = new byte[b];
            AES aes = new AES();
            aes.setKey(key22);
            if (idxx == null) {
                idxx= aes.encrypt(i);
            }
            buildTable(idxx, m + 1);
            Thread[] worker = new Thread[numberThreat];
            for (int a = 0; a <= m - 2; a++) {
                worker[a % numberThreat] = new Thread(new runThread(runThread.DECRYPT,bufferOut[a], bufferIn[a], key11, key22, a, i));
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
                blockDecrypt(bufferOut[m - 1], bufferIn[m - 1], key11, key22, m - 1, i);
                bufferOut[m] = new byte[0];
            } else {
                byte[] cc = new byte[16];
                blockDecrypt(cc, bufferIn[m - 1], key11, key22, m, i);
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
                blockDecrypt(bufferOut[m - 1], pp, key11, key22, m - 1, i);
            }
            for (byte[] bufferOut1 : bufferOut) {
                raf2.write(bufferOut1);
            }
        }
        raf2.close();
    }

    
     /**
     * method untuk melakukan enkripsi per block data
     * menggunakan XTS-AES.
     * 
     * @param p ciphertext berukuran 1 blok / 16 byte / 128 bit
     * @param plain 
     * @param key1 
     * @param key2 
     * @param j nomor blok, dimulai dari 0
     * @param i 
     * @return 16 byte data hasil enkripsi
     */
    public void blockEncrypt(byte[] p, byte[] plain, byte[] key1, byte[] key2, int j, byte[] i) {
        AES aes = new AES();
        // T <- tabular array of multiplication value
        byte[] t = multiply[j];
        
        // PP <- P xor T
        byte[] pp = new byte[16];
        for (int a = 0; a < pp.length; a++) {
            pp[a] = (byte) (plain[a] ^ t[a]);
        }
        
        // CC <- AES-enc(Key1, PP)
        aes.setKey(key1);
        byte[] cc = aes.encrypt(pp);
         // C <- CC xor T
        for (int a = 0; a < p.length; a++) {
            p[a] = (byte) (cc[a] ^ t[a]);
        }
    }
    
     /**
     * method untuk melakukan dekripsi per block data
     * menggunakan XTS-AES.
     * 
     * @param p ciphertext berukuran 1 blok / 16 byte / 128 bit
     * @param plain 
     * @param key1 
     * @param key2 
     * @param j nomor blok, dimulai dari 0
     * @param i 
     * @return 16 byte data hasil dekripsi
     */
    public void blockDecrypt(byte[] p, byte[] cipher, byte[] key1, byte[] key2, int j, byte[] i) {
        AES aes = new AES();
        // T <- tabular array of multiplication value
        byte[] t = multiply[j];
        
        // CC <- C xor T
        byte[] cc = new byte[16];
        for (int a = 0; a < cc.length; a++) {
            cc[a] = (byte) (cipher[a] ^ t[a]);
        }
        
       // PP <- AES-dec(Key1, CC)
        aes.setKey(key1);
        byte[] pp = aes.decrypt(cc);
        
        // P <- PP xor T
        for (int a = 0; a < p.length; a++) {
            p[a] = (byte) (pp[a] ^ t[a]);
        }
    }
    
    private void buildTable(byte[] a, int numBlock) {
        multiply= new byte[numBlock][16];
        multiply[0] = a;
        for (int i = 1; i < numBlock; i++) {
            multiply[i][0] = (byte) ((2 * (multiply[i - 1][0] % 128)) ^ (135 * (multiply[i - 1][15] / 128)));
            for (int k = 1; k < 16; k++) {
                multiply[i][k] = (byte) ((2 * (multiply[i - 1][k] % 128)) ^ (multiply[i - 1][k - 1] / 128));
            }
        }
    }
   
   //Class untuk melakukan eknripsi atau dekripsi sesuai dengan mode
   class runThread implements Runnable {
        public static final int ENCRYPT = 0;
        public static final int DECRYPT = 1;
        private final int mode;
        private final byte[] dest;
        private final byte[] source;
        private final byte[] key1;
        private final byte[] key2;
        private final int j;
        private final byte[] i;

        public runThread(int mode, byte[] dest, byte[] source, byte[] key1,byte[] key2, int j, byte[] i) {
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
                    blockEncrypt(dest, source, key1, key2, j, i);
                    break;
                case DECRYPT:
                    blockDecrypt(dest, source, key1, key2, j, i);
                    break;
            }
        }
    }
}

