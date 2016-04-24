/**
 * @author Rauhil Fahmi (1206208145)
 * @author Irvi Firqotul Aini (1306463591)
 * 
 * 
 * @version 2016/04/24
 * 
 */

package tugas.cis;

import javax.swing.JFrame;

public class TUGASCIS {
    public static void main(String[] args) {
        FrameGui firstPage = new FrameGui("XTS-AES"); 
        firstPage.setVisible(true);
        firstPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}


/**
 * Sumber yang dijadikan sebagai Refrensi:
 * -- https://github.com/hesahesa/xts-aes-java
 * -- http://stackoverflow.com
 * -- scele.cs.ui.ac.id/mod/resource/view.php?id=79983
 */