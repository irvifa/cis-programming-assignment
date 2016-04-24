/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.cis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

/**
 *
 * @author RAUHIL
 */
public class XTSAES {
     private String input;
     private String key;
     private String output;
     
     public XTSAES(String input, String key, String output) {
        this.input = input;
        this.key  = key;
        this.output  = output;
    }
     
     public void enkripsi(String input, String key, String output) throws FileNotFoundException, IOException{
         String bacaKey;
         String bacaInput;
         BufferedReader br = new BufferedReader(new FileReader(key));
             bacaKey = br.readLine();
         br = new BufferedReader(new FileReader(input));
            bacaInput = br.readLine();
            
        PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter(output, true)));
            out.println("Key :" + bacaKey);
            out.println("Input :" + bacaInput);
            out.close();
         System.out.println("Enkripsi Superrr");
      
       
    }
     
     public void dekripsi(String input, String key, String output) throws FileNotFoundException, IOException{
         String bacaKey;
         String bacaInput;
         BufferedReader br = new BufferedReader(new FileReader(key));
             bacaKey = br.readLine();
         br = new BufferedReader(new FileReader(input));
            bacaInput = br.readLine();
         PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter(output, true)));
            out.println("Key :" + bacaKey);
            out.println("Input :" + bacaInput);
            out.close();
              System.out.println("Dekripsi Superrr");
}
}