package com.asiangames2018;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import com.asiangames2018.dao.AsianGamesDAO;
import com.asiangames2018.entity.Country;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import com.jaunt.util.IOUtil;

/**
 * Hello world!
 * https://en.asiangames2018.id/d3images/ml/flags/xl/JPN.png
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String flagURL = "https://en.asiangames2018.id/d3images/ml/flags/xl/";
        System.out.println( "Hello World!g" );
        AsianGamesDAO dao = new AsianGamesDAO();
        try{
      	  UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
      	  userAgent.settings.checkSSLCerts = false;
      	  userAgent.visit("https://en.asiangames2018.id/athletes/page/1/");         //visit a url  
 //     	  System.out.println(userAgent.doc.innerHTML());               //print the document as HTML
      	  
      	  Elements elements = userAgent.doc.findEvery("<select>"); 
      	  Elements countryElements = elements.getElement(1).findEvery("<option>");
      	  
      	  
      	  System.out.println(elements.size());
      	  
      	  for (Element element : countryElements) {
      		  String countryTags = element.getAt("value");
      		  
      		  String[] split = countryTags.split("/");
      		  String countryId = split[3];
      		  if (countryId.length() != 3) continue;   // valid country id only 3 digits
      		  String countryName = element.getChildText();

      		  String urlCountryFlag = flagURL + countryId + ".png";
      		  Blob blob = null; 
      		  FileInputStream fis = null;
      		  try {
          		  URL url = new URL (urlCountryFlag);
          		  
      			  byte[] flagBytes =IOUtils.toByteArray(url);
 				  blob = new javax.sql.rowset.serial.SerialBlob(flagBytes);

 				  File flagFile = new File("image//country//" + countryName + ".png");
 				  if (!flagFile.getParentFile().isDirectory())  {  // check if directory exist
 					 flagFile.getParentFile().mkdir();   // make non-existing directory folder
 				  }
  				InputStream is = blob.getBinaryStream();
  				OutputStream os = new FileOutputStream(flagFile);
  				byte[] buff = blob.getBytes(1,(int)blob.length());
  				os.write(buff);
  				os.close();


				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
      			  // TODO Auto-generated catch block
      			  e.printStackTrace();
      		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		  
      		  
      		
      		  
      		  
      		  Country country = new Country(countryId, countryName, blob);
      		  dao.insertCountry(country);
      	  }
      	}
      	catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
      	  System.err.println(e);
      	}        
        
    }
}
