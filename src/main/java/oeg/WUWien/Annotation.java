/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.WUWien;

import de.unihd.dbs.heideltime.standalone.DocumentType;
import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.OutputType;
import de.unihd.dbs.heideltime.standalone.POSTagger;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import oeg.core.main.Core;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author mnavas
 */
public class Annotation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, Exception{
        
             
        try{
            HeidelTimeStandalone heidelTime = new HeidelTimeStandalone(Language.ENGLISH, DocumentType.NARRATIVES, OutputType.TIMEML,
                Core.getRootFolder() + "\\lib\\heideltime-standalone\\config.props", POSTagger.NO, true);

            // Lets take all the files in the directory
//            String fil = "C:\\Users\\María\\Dropbox\\ECHR\\B - Maria\\ANSItxtB";
            String fil = "C:\\Users\\mnavas\\Dropbox\\ECHR\\B-Maria\\EURLEX\\ORIGINALS\\DONE";
            File fich = new File(fil);
            for(File f : fich.listFiles()){
                String content = new Scanner(f).useDelimiter("\\Z").next();
                String result = heidelTime.process(content);
            
                try(  PrintWriter out = new PrintWriter(f.getAbsolutePath() + ".output.xml")  ){
                    out.println(result);
                }
            }
        }      
        catch(ParseException | FileNotFoundException | DocumentCreationTimeMissingException ex){
            System.out.print(ex.toString());
        }
        
    }
}


    