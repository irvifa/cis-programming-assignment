/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.cis;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 *
 * @author RAUHIL
 */
public class FrameGui extends JFrame {
    private Panel panel = new Panel();
    
   
    FrameGui(String title){
        this.setSize(700,500);
        setBackground(Color.blue);
        setBounds(0,0,700,400);
        setLocationRelativeTo(null);
        setTitle(title);
        getContentPane().add(panel);
        setResizable(false);
        setVisible( true );
    }
  
    
}
