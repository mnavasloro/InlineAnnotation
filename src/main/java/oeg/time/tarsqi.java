/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.time;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.core.main.Core;

/**
 *
 * @author Maria
 */
public class tarsqi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Process p;
        try {
//            p = Runtime.getRuntime().exec("python " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\tarsqi.py --pipeline PREPROCESSOR,GUTIME,EVITA --source text " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\data\\in\\simple-xml\\test.xml test.output.xml");
            p = Runtime.getRuntime().exec("python " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\tarsqi.py --pipeline PREPROCESSOR,GUTIME,EVITA --source text " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\data\\in\\simple-xml\\tiny.xml tiny.output.xml");
            p = Runtime.getRuntime().exec("python " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\tarsqi.py --pipeline PREPROCESSOR,GUTIME,EVITA --source text " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\data\\in\\simple-xml\\tiny.txt tiny.output2.xml");
//            p = Runtime.getRuntime().exec("python " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\tarsqi.py --source text " + Core.getRootFolder() + "\\res\\short.txt output.xml");
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception ex) {
            Logger.getLogger(tarsqi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
