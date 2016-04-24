/**
 * @author Rauhil Fahmi (1206208145)
 * @author Irvi Firqotul Aini (1306463591)
 * 
 * 
 * @version 2016/04/24
 * 
 */

package tugas.cis;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrameGui extends JFrame {
    private final Panel panel = new Panel();
    FrameGui(String title){
        this.setSize(700,500);
        setBounds(0,0,700,400);
        setLocationRelativeTo(null);
        setTitle(title);
        getContentPane().add(panel);
        setResizable(false);
        setVisible( true );
    }
}

class Panel extends JPanel implements ActionListener {
    private JLabel judul, plaintext, keyLabel, ciphertext,copyright;
    private JTextField fileField, keyField, saveField;
    private JButton dekripsi, enkripsi, openKey, openFile, saveFile;
    private JFileChooser pilihFile, pilihKey, pilihSave;
    private File fileSource, fileKey, fileSave;
    public String file, key, save;
    
    
    public Panel () {
        setLayout(null);
        setBackground(Color.PINK);
        initComponents();
        setLocation(10,100);
        setSize(692,300);
        addTampilan();
       
    }
    private void addTampilan() {
        add(judul);
        add(plaintext);
        add(keyLabel);
        add(ciphertext);
        add(fileField);
        add(keyField);
        add(saveField);
        add(dekripsi);
        add(enkripsi);
        add(openKey);
        add(openFile);
        add(saveFile);
        add(copyright);
    }
     private void initComponents() {
        judul = new JLabel("DEKRIPSI DAN ENKRIPSI XTS-AES");
        judul.setHorizontalAlignment(SwingConstants.CENTER);
        judul.setSize(680,40);
        judul.setFont(new Font("Times New Roman",0,25));
        judul.setHorizontalTextPosition(SwingConstants.CENTER);
        judul.setVerticalTextPosition(SwingConstants.CENTER);
        
        plaintext = new JLabel("Plaintext / Ciphertext");
        plaintext.setHorizontalAlignment(SwingConstants.LEFT);
        plaintext.setSize(220,20);
        plaintext.setFont(new Font("Tahoma",0,15));
        plaintext.setHorizontalTextPosition(SwingConstants.CENTER);
        plaintext.setVerticalTextPosition(SwingConstants.CENTER);
        plaintext.setLocation(110,80);
        
        keyLabel = new JLabel("Key");
        keyLabel.setHorizontalAlignment(SwingConstants.LEFT);
        keyLabel.setSize(220,20);
        keyLabel.setFont(new Font("Tahoma",0,15));
        keyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        keyLabel.setVerticalTextPosition(SwingConstants.CENTER);
        keyLabel.setLocation(110,130);
        
        ciphertext = new JLabel("Save to");
        ciphertext.setHorizontalAlignment(SwingConstants.LEFT);
        ciphertext.setSize(220,20);
        ciphertext.setFont(new Font("Tahoma",0,15));
        ciphertext.setHorizontalTextPosition(SwingConstants.CENTER);
        ciphertext.setVerticalTextPosition(SwingConstants.CENTER);
        ciphertext.setLocation(110,180);

        fileField =new JTextField(20);
        fileField.setLocation(110,100);
        fileField.setSize(364,20);
        fileField.disable();
        
        keyField =new JTextField(20);
        keyField.setLocation(110,150);
        keyField.setSize(364,20);
        keyField.disable();
        
        saveField =new JTextField(20);
        saveField.setLocation(110,200);
        saveField.setSize(364,20);
        saveField.disable();
        
        openFile = new JButton("Choose File");
        openFile.setSize(100,20);
        openFile.setLocation(475,100);
        openFile.addActionListener(this);
        
        openKey = new JButton("Choose File");
        openKey.setSize(100,20);
        openKey.setLocation(475,150);
        openKey.addActionListener(this);
        
        saveFile = new JButton("Choose File");
        saveFile.setSize(100,20);
        saveFile.setLocation(475,200);
        saveFile.addActionListener(this);
         
        dekripsi = new JButton("Dekripsi");
        dekripsi.setSize(99,30);
        dekripsi.setLocation(375,270);
        dekripsi.addActionListener(this);
        
        enkripsi = new JButton("Enkripsi");
        enkripsi.setSize(99,30);
        enkripsi.setLocation(475,270);
        enkripsi.addActionListener(this);
      
        copyright = new JLabel("copyright.rauhil-irvi.cis2016@fasilkom-ui");
        copyright.setHorizontalAlignment(SwingConstants.CENTER);
        copyright.setSize(680,20);
        copyright.setFont(new Font("Times New Roman",0,12));
        copyright.setLocation(10,350);
        copyright.setHorizontalTextPosition(SwingConstants.CENTER);
        copyright.setVerticalTextPosition(SwingConstants.CENTER);
        
    }
     
    
    @Override
    public void actionPerformed(ActionEvent ae) {
      if ( ae.getSource() == openFile) {
        pilihFile =  new JFileChooser(); 
        int result = pilihFile.showOpenDialog(null); 
        if( result == JFileChooser.APPROVE_OPTION) {
           fileSource = pilihFile.getSelectedFile(); 
           file = fileSource.getPath();
           fileField.setText(file);
        } else if ( result ==  JFileChooser.CANCEL_OPTION) {
        }
    }else if (ae.getSource() == openKey) {
        pilihKey =  new JFileChooser(); 
        int result = pilihKey.showOpenDialog(null); 
        if( result == JFileChooser.APPROVE_OPTION) {
           fileKey = pilihKey.getSelectedFile(); 
            key= fileKey.getPath();
            keyField.setText(key);
        } else if ( result ==  JFileChooser.CANCEL_OPTION) {
        }
    }else if(ae.getSource() == saveFile) {
        pilihSave =  new JFileChooser(); 
        int result = pilihSave.showSaveDialog(null); 
        if( result == JFileChooser.APPROVE_OPTION) {
            fileSave = pilihSave.getSelectedFile(); 
            save = fileSave.getPath();
            saveField.setText(save);
        } else if ( result ==  JFileChooser.CANCEL_OPTION) {
        }
    }else if(ae.getSource() == dekripsi) {
        XTSAES xtsaes = new XTSAES( key,file, save);
          try {
              xtsaes.dekripsi(key,file, save);
              JFrame parent = new JFrame();
              JOptionPane.showMessageDialog(parent, "Dekripsi Sukses !");
          } catch (IOException ex) {
              Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
          } catch (Exception ex) {
              Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
          }
   }else if(ae.getSource() == enkripsi) {
        XTSAES xtsaes = new XTSAES(key,file, save);
          try {
              xtsaes.enkripsi(key,file, save);
              JFrame parent = new JFrame();
              JOptionPane.showMessageDialog(parent, "Enkripsi Sukses !");
          } catch (IOException ex) {
              Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
          } catch (Exception ex) {
              Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    }
}

/**
 * Sumber yang dijadikan sebagai Refrensi:
 * -- https://github.com/hesahesa/xts-aes-java
 * -- http://stackoverflow.com
 * -- scele.cs.ui.ac.id/mod/resource/view.php?id=79983
 */