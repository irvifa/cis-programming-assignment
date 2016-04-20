/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.cis;

import javax.swing.JFrame;

/**
 *
 * @author RAUHIL
 */
public class TUGASCIS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FrameGui firstPage = new FrameGui("XTS-AES"); 
        firstPage.setVisible(true);
        firstPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
