/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.time;

import de.unihd.dbs.heideltime.standalone.DocumentType;
import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.OutputType;
import de.unihd.dbs.heideltime.standalone.POSTagger;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oeg.core.main.Core;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author mnavas
 */
public class heideltime {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, Exception{
        
        String textFile = Core.getRootFolder() + "\\res\\short.txt";
        
        try{
            HeidelTimeStandalone heidelTime = new HeidelTimeStandalone(Language.ENGLISH, DocumentType.SCIENTIFIC, OutputType.TIMEML,
                Core.getRootFolder() + "\\lib\\heideltime-standalone\\config.props", POSTagger.NO, true);
           
            String content = new Scanner(new File(textFile)).useDelimiter("\\Z").next();
            String result = heidelTime.process(content);
            
//            System.out.print(result);
            PrintWriter out = new PrintWriter(textFile + ".output.xmi");
            out.println(result);
            
        }catch(Exception ex){
            System.out.print(ex.toString());
        }
        
    }
}