/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.WUWien;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oeg.core.main.Core;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Maria
 */
public class tarsqiTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try{
 
            
            String fil = args[0];
                    
            File fich = new File(fil);
            
            
            cleanDocs(fich);
            
            for(File f : fich.listFiles()){
                
                String extension = "text";
                if(f.getName().substring(f.getName().length()-3, f.getName().length()).equals("xml"))
                    extension = "xml";
                
                Process p;
                System.out.println("python " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\tarsqi.py  --pipeline PREPROCESSOR,GUTIME --source " + extension + " " + f.getAbsolutePath() + " " + f.getAbsolutePath() + ".output.xml --loglevel 4");
                p = Runtime.getRuntime().exec("python " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\tarsqi.py  --pipeline PREPROCESSOR,GUTIME --source " + extension + " " + f.getAbsolutePath() + " " + f.getAbsolutePath() + ".output.xml --loglevel 4");
//                p = Runtime.getRuntime().exec("python " + Core.getRootFolder() + "\\lib\\ttk-2.1.0\\tarsqi.py  --pipeline PREPROCESSOR,GUTIME --source " + extension + " " + f.getAbsolutePath() + " " + f.getAbsolutePath() + ".output.xml --loglevel 4");
            
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
            
            //We change the output format
            File fich2 = new File(f.getAbsolutePath() + ".output.xml");
            outputAsInlineTags(fich2);
            
        }   
        }
        catch (Exception ex) {
            Logger.getLogger(tarsqiTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static boolean cleanDocs(File fich){
        for(File f : fich.listFiles()){
            try {            
                String original;
                original = FileUtils.readFileToString(f, "UTF-8");
                    
//                System.out.println(original);
                String cadenaNormalize = Normalizer.normalize(original, Normalizer.Form.NFD);  
                String cadenaSinAcentos = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
                
                try(  PrintWriter out = new PrintWriter(f.getAbsolutePath())  ){
                    out.println(cadenaSinAcentos);
                }
            
            } catch (IOException ex) {
                Logger.getLogger(tarsqiTest.class.getName()).log(Level.SEVERE, null, ex);
            }}
        return true;
    }
    
    public static boolean outputAsInlineTags(File f) throws IOException {
        String original;
        original = FileUtils.readFileToString(f, "UTF-8");
        String copy = original;
        String timexRegex = "((<TIMEX3 )begin=\\\"(\\d+)\\\" end=\\\"(\\d+)\\\" origin=\\\"GUTIME\\\" ([^(\\/>)]+) \\/>(\\n))";
        String cierreTimex3 = "</TIMEX3>";
        String textRegex = "(<text>([^<]*?)<\\/text>)";
        String vauleRegex = "value=\\\"(\\d{8})\\\"";
        Pattern pValue = Pattern.compile(vauleRegex);
        String onlyText = "";
        String copyOnlyText = "";
        String tagIni = "";
        Integer ini = 0;
        Integer fin = 0;
//        Integer offset = 0;
//        Integer offsetTIMEX = cierreTimex3.length();
        String normalizedValue = "";
 
        // We retrieve the text without annotations
        Pattern pText = Pattern.compile(textRegex);
        Matcher m =  pText.matcher(copy);
        
        while(m.find())
        {
            onlyText = m.group(2);
            copyOnlyText = onlyText;
        }        
        
        // We retrieve the TIMEX3 annotations   
        
        Pattern pTIMEX3 = Pattern.compile(timexRegex);
        Matcher m2 =  pTIMEX3.matcher(copy);
        
        // For each timex found
        while(m2.find())
        {
            
            String stringValue = m2.group(5);
            normalizedValue = stringValue;
            
            // Change of format for value, with -
            Matcher m3 =  pValue.matcher(stringValue);
            if(m3.find()){
               String aux  = m3.group(1);
               aux = aux.substring(0, 4) + "-" + aux.substring(4, 6) + "-" + aux.substring(6, 8);
               normalizedValue = normalizedValue.replace(m3.group(1), aux);
            }
            
            tagIni = m2.group(2) + normalizedValue + ">";
            
            
            ini = Integer.parseInt(m2.group(3));
            fin = Integer.parseInt(m2.group(4));
            
            // Whats in that substring
            CharSequence target = copyOnlyText.subSequence(ini, fin);
            String workingCopy = copyOnlyText.substring(ini);
            String workingCopy2 = workingCopy;
            workingCopy = workingCopy.replace(target, tagIni + target + cierreTimex3);
            onlyText = onlyText.replace(workingCopy2, workingCopy);
//            offset = offset + offsetTIMEX + tagIni.length();
        }               
        
        // We write the final files with inline annotations
         try(  PrintWriter out = new PrintWriter(f.getAbsolutePath() + ".normalized.xml")){
                    out.println("<ttk>\n" + "<text>" + onlyText + "\n</text>\n</ttk>");
         }
        
        return true;
}
    
}