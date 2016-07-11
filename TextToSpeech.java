/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texttospeech;

/**
 *
 * @author pradeep
 */


import java.util.*;

/* adding jars : 
  en_us.jar , mbrola.jar , jsapi.jar , freetts-jsapi10.jar 
  
  from freetts-1.2.2 package.
*/

import javax.speech.*;
import javax.speech.synthesis.*;

import com.sun.speech.freetts.jsapi.FreeTTSEngineCentral.*;


public class TextToSpeech {
    
    static String text ;
    
    private static void speakText()
    {
        
        try
        {
            //create SynthesizerModeDesc that will match FreeTTS Synthesizer
            SynthesizerModeDesc smd = new SynthesizerModeDesc(
                    null,
                    "general", /*use "time" or "general" */
                    Locale.US , 
                    Boolean.FALSE , null );
            /*
            if( smd == null )   //unnessary test . expression is never null
            {
                System.out.println("Error creating mode descriptor");
            }
            */
            
            Synthesizer syn = Central.createSynthesizer(smd); 
            
            /*    //you can use above line or this block to create synthesizer
            FreeTTSEngineCentral central = new FreeTTSEngineCentral() ;
            Synthesizer syn = null ;
            
            EngineCreate creator = (EngineCreate) central.createEngineList(smd).get(0);
            syn = (Synthesizer) creator.createEngine() ;
            */
            if( syn == null )
            {
                System.err.println("Cannot create synthesizer");
                System.exit(1);
            }
            
            //get ready to speak
            syn.allocate();
            syn.resume();
            
            smd = (SynthesizerModeDesc) syn.getEngineModeDesc() ;
            
            
            
            /*      //this block can be used to provide choice of selecting voice
            
            //choose voice.
            Voice[] voices = smd.getVoices() ;
            
            Voice voice = null ;
            
            System.out.print("Select voice : ");
            for(int i=0 ; i< voices.length ; i++ )
            {
                System.out.print(i+" -> "+voices[i]+" ; ");
            }
            //Take input
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt() ;
            
            if( choice < 0 && choice >= voices.length )
            {
                System.out.println("Invalid choice . Default 0");
                choice = 0;
            }
            
            
            //selected voice
            voice = voices[choice];
            
            syn.getSynthesizerProperties().setVoice(voice);
            
            */
            
            //say text
            syn.speakPlainText(text,null);
            //wait until speaking is done and clean up
            syn.waitEngineState(Synthesizer.QUEUE_EMPTY);
            
            syn.deallocate();
        }
        catch(Exception e)
        {
            System.out.println(e);
            //System.out.println(System.getProperty("user.home"));
        }
    }
    
    
    public static void main(String[] args) {
        
        TextToSpeech t2s = new TextToSpeech() ;
        
        //System.out.println(System.getProperty("user.home"));
        //System.out.println(System.getProperty("java.home"));
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Text String : ");
        
        text = sc.next();
        
        System.out.println("Speaking .......");
        
        t2s.speakText() ;
        
        System.out.println("** Done ");
    }
    
}