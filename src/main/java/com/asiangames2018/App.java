package com.asiangames2018;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!g" );
        try{
      	  UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
      	  userAgent.settings.checkSSLCerts = false;
      	  userAgent.visit("https://en.asiangames2018.id/athletes/page/1/");         //visit a url  
 //     	  System.out.println(userAgent.doc.innerHTML());               //print the document as HTML
      	  
      	  Elements elements = userAgent.doc.findEvery("<select>"); 
      	  Elements sportsElements = elements.getElement(0).findEvery("<option>");
      	  
      	  
      	  System.out.println(elements.size());
      	  
      	  for (Element element : sportsElements) {
      		  System.out.println("Every OPtion: " + element.getAt("value") + " ==== "  +  element.getChildText());
      	  }
      	}
      	catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
      	  System.err.println(e);
      	}        
        
    }
}
