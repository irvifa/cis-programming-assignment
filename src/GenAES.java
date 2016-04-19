import java.io.PrintStream;
import java.util.Random;

public class GenAES {
    private AES myAES;
    private static final String usage = "Usage:\nGenAES [n]\n\t- generate 1 [n] random AES triples\n";

    public static void main(String[] arrstring) {
        byte[] arrby = new byte[16];
        byte[] arrby2 = new byte[16];
        int n = 1;
        if (arrstring.length > 0) {
            n = Integer.parseInt(arrstring[0]);
        }
        Random random = new Random(System.currentTimeMillis());
        System.out.println("# Random AES (key,plain,cipher) triples");
        for (int i = 0; i < n; ++i) {
            random.nextBytes(arrby);
            random.nextBytes(arrby2);
            AES aES = new AES();
            aES.setKey(arrby);
            byte[] arrby3 = aES.encrypt(arrby2);
            System.out.println(Util.toHEX1(arrby) + " " + Util.toHEX1(arrby2) + " " + Util.toHEX1(arrby3));
        }
    }
}
