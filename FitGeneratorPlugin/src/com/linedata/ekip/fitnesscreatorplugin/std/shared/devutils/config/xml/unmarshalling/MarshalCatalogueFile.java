package com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.config.xml.unmarshalling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.config.model.jaxb.unmarshall.Service;



public class MarshalCatalogueFile
{

   public Service execute() throws JAXBException, IOException
   {
	 
	   
     Bundle myBundle = Platform.getBundle("LinedataFitNesseEclipsePlugin");
     
     URL url1 = FileLocator.find(myBundle,  new Path("/"), null);
    
     
     String url =  FileLocator.toFileURL(url1)+"catalogue.xml" ;
  
     System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
     System.out.println("| Le chemin de fichier catalogue.xml : "+url+" |");
     System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
    
     
     URL myUrl = new URL(url);
  
        URLConnection connection = myUrl.openConnection();
        try{
        	  InputStream input2 = connection.getInputStream();  
              JAXBContext jaxbContext = JAXBContext.newInstance(Service.class);
              Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
              Service service = (Service) jaxbUnmarshaller.unmarshal(input2);
             
            
              return service;
        }
        
        catch(Exception e){
        	//System.out.println("file catalogue.xml not found , copying the native one ...");
            InputStream input = myBundle.getEntry("/temp.xml").openStream();         
           // System.out.println("********** "+ FileLocator.toFileURL(FileLocator.find(myBundle,  new Path("/"), null))+"catalogue.xml");
            String path=FileLocator.toFileURL(FileLocator.find(myBundle,  new Path("/"), null))+"catalogue.xml";
            File outputFile =new File(path.substring(6));
            OutputStream  outStream = new FileOutputStream(outputFile);
            
            byte[] buffer = new byte[1024];
            
       	    int length;
       	    //copy the file content in bytes 
       	    while ((length = input.read(buffer)) > 0){

       	    	outStream.write(buffer, 0, length);

       	    }

       	    input.close();
       	    outStream.close();
       	    
       	 InputStream input2 = connection.getInputStream();  
         JAXBContext jaxbContext = JAXBContext.newInstance(Service.class);
         Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
         Service serviceq = (Service) jaxbUnmarshaller.unmarshal(input2);
        
       
         return serviceq;
       	
           
        }
     
     //System.out.println("Platform.getInstanceLocation() : "+Platform.getInstanceLocation().getURL().getPath());
    // System.out.println("Platform.getInstanceLocation() : "+Platform.getStateLocation(myBundle));
      
     
//            
         
        

   }
}
