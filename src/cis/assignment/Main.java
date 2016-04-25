/**
 * @author Rauhil Fahmi (1206208145)
 * @author Irvi Firqotul Aini (1306463591)
 * 
 * 
 * @version 2016/04/24
 * 
 */

package cis.assignment;

import cis.assignment.interfaces.FrameGui;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        FrameGui firstPage = new FrameGui("XTS-AES"); 
        firstPage.setVisible(true);
        firstPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}

