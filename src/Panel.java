/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.cis;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author RAUHIL
 */
public class Panel extends JPanel implements ActionListener {
    private JLabel judul, plaintext, keyLabel, ciphertext,copyright;
    private JTextField fileField, keyField, saveField;
    private JButton dekripsi, enkripsi, openKey, openFile, saveFile;
    private JFileChooser pilihFile, pilihKey, pilihSave;
    private File fileSource, fileKey, fileSave;
    public String source, key, save;
    
    
     public Panel () {
        setLayout(null);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
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
        copyright = new JLabel("Copyright.rauhil-irvi.cis2016@fasilkom-ui");
        copyright.setHorizontalAlignment(SwingConstants.CENTER);
        copyright.setSize(680,20);
        copyright.setFont(new Font("Times New Roman",0,12));
        copyright.setLocation(10,350);
        copyright.setHorizontalTextPosition(SwingConstants.CENTER);
        copyright.setVerticalTextPosition(SwingConstants.CENTER);
        
        judul = new JLabel("DEKRIPSI DAN ENKRIPSI XTS-AES");
        judul.setHorizontalAlignment(SwingConstants.CENTER);
        judul.setSize(680,40);
        judul.setFont(new Font("Times New Roman",0,25));
        judul.setHorizontalTextPosition(SwingConstants.CENTER);
        judul.setVerticalTextPosition(SwingConstants.CENTER);
        
        plaintext = new JLabel("Source        :");
        plaintext.setHorizontalAlignment(SwingConstants.LEFT);
        plaintext.setSize(220,20);
        plaintext.setFont(new Font("Times New Roman",0,20));
        plaintext.setHorizontalTextPosition(SwingConstants.CENTER);
        plaintext.setVerticalTextPosition(SwingConstants.CENTER);
        plaintext.setLocation(130,100);
        
        keyLabel = new JLabel("Key            :");
        keyLabel.setHorizontalAlignment(SwingConstants.LEFT);
        keyLabel.setSize(220,20);
        keyLabel.setFont(new Font("Times New Roman",0,20));
        keyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        keyLabel.setVerticalTextPosition(SwingConstants.CENTER);
        keyLabel.setLocation(130,150);
        
        ciphertext = new JLabel("Target        :");
        ciphertext.setHorizontalAlignment(SwingConstants.LEFT);
        ciphertext.setSize(220,20);
        ciphertext.setFont(new Font("Times New Roman",0,20));
        ciphertext.setHorizontalTextPosition(SwingConstants.CENTER);
        ciphertext.setVerticalTextPosition(SwingConstants.CENTER);
        ciphertext.setLocation(130,200);

        fileField =new JTextField(20);
        fileField.setSize(fileField.getPreferredSize());
        fileField.setLocation(235,100);
       fileField.setSize(235,20);
        
        keyField =new JTextField(20);
        keyField.setSize(keyField.getPreferredSize());
        keyField.setLocation(235,150);
        keyField.setSize(235,20);
        
        saveField =new JTextField(20);
        saveField.setSize(saveField.getPreferredSize());
        saveField.setLocation(235,200);
        saveField.setSize(235,20);

        dekripsi = new JButton("Dekripsi");
        dekripsi.setSize(85,20);
        dekripsi.setLocation(383,280);
        dekripsi.addActionListener(this);
        
        enkripsi = new JButton("Enkripsi");
        enkripsi.setSize(80,20);
        enkripsi.setLocation(470,280);
        enkripsi.addActionListener(this);
        
        openFile = new JButton("Source");
        openFile.setSize(80,20);
        openFile.setLocation(470,100);
        openFile.addActionListener(this);
        
        openKey = new JButton("Key");
        openKey.setSize(80,20);
        openKey.setLocation(470,150);
        openKey.addActionListener(this);
        
        saveFile = new JButton("Target");
        saveFile.setSize(80,20);
        saveFile.setLocation(470,200);
        saveFile.addActionListener(this);
    }
     
    
    @Override
    public void actionPerformed(ActionEvent ae) {
      if ( ae.getSource() == openFile) {
        pilihFile =  new JFileChooser(); 
        int result = pilihFile.showOpenDialog(null); 
        if( result == JFileChooser.APPROVE_OPTION) {
           fileSource = pilihFile.getSelectedFile(); 
           source = fileSource.getPath();
           fileField.setText(source);
        } else if ( result ==  JFileChooser.CANCEL_OPTION) {
            return;
        }
    }else if (ae.getSource() == openKey) {
        pilihKey =  new JFileChooser(); 
        int result = pilihKey.showOpenDialog(null); 
        if( result == JFileChooser.APPROVE_OPTION) {
           fileKey = pilihKey.getSelectedFile(); 
            key= fileKey.getPath();
            keyField.setText(key);
        } else if ( result ==  JFileChooser.CANCEL_OPTION) {
            return;
        }
    }else if(ae.getSource() == saveFile) {
        pilihSave =  new JFileChooser(); 
        int result = pilihSave.showSaveDialog(null); 
        if( result == JFileChooser.APPROVE_OPTION) {
            fileSave = pilihSave.getSelectedFile(); 
            save = fileSave.getPath();
            saveField.setText(save);
        } else if ( result ==  JFileChooser.CANCEL_OPTION) {
            return;
        }
    }else if(ae.getSource() == dekripsi) {
        XTSAES xtsaes = new XTSAES(source, key, save);
          try {
              xtsaes.dekripsi(source, key, save);
          } catch (IOException ex) {
              Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
          }
   }else if(ae.getSource() == enkripsi) {
        XTSAES xtsaes = new XTSAES(source, key, save);
          try {
              xtsaes.enkripsi(source, key, save);
          } catch (IOException ex) {
              Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    }
}
