package com.fitnesscreatorplugin.std.tablegenerator.controller.dispatcher;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBException;

import com.fitnesscreatorplugin.std.shared.devutils.config.model.jaxb.unmarshall.Service;
import com.fitnesscreatorplugin.std.shared.devutils.config.xml.unmarshalling.MarshalCatalogueFile;
import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.fitnesscreatorplugin.std.shared.devutils.service.identity.ServiceIdentifier;
import com.fitnesscreatorplugin.std.tablegenerator.model.servicesfonctionnels.actions.CreateFitnesTableForFuctionnalService;
import com.fitnesscreatorplugin.std.tablegenerator.model.servicesfonctionnels.actions.DeleteFitnesTableForFuctionnalService;
import com.fitnesscreatorplugin.std.tablegenerator.model.servicesfonctionnels.actions.UpdateFitnesTableForFuctionnalService;
import com.fitnesscreatorplugin.std.tablegenerator.model.servicesunitaires.actions.CreateFitnesTableForUnitaryService;
import com.fitnesscreatorplugin.std.tablegenerator.model.servicesunitaires.actions.DeleteFitnesTableForUnitaryService;
import com.fitnesscreatorplugin.std.tablegenerator.model.servicesunitaires.actions.FindFitnesTableForUnitaryService;
import com.fitnesscreatorplugin.std.tablegenerator.model.servicesunitaires.actions.UpdateFitnesTableForUnitaryService;
import com.sun.codemodel.JClassAlreadyExistsException;


public class TablesGeneratorDispatcher 
{
   String toUpperCaseFirstChar(String s)
   {
      String a = (String) s.subSequence(0, 1);

      return a.toUpperCase() + s.substring(1);
   }

   public void generateContent(Class<?> TS,CustomClassLoader loader, String dir,Method[] list,boolean open) throws IOException, JClassAlreadyExistsException, JAXBException
   {

      int i;
      try{
      for (i = 0; i < list.length; i++)
      {

    	 
         // extraire les paramÃ©tres de chaque methode
         //ExtractInputsFromMethod ext1 = new ExtractInputsFromMethod();
       //  Class[] params = ext1.runExtractInputsFromMethod(list[i]);
         
         MarshalCatalogueFile parser = new MarshalCatalogueFile();
         Service catalogue = parser.execute();
         ServiceIdentifier disp = new ServiceIdentifier();
      
         String result=disp.compare(catalogue, list[i].getName());
         String type=result;
         

         // traitetement des services unitaires
        /* if (!(TS.getSimpleName().contains("Functional")))
         {
        	 // traitment des methodes de type find
        	  if (type.equals("find"))
             {
                FindFitnesTableForUnitaryService finder =FindFitnesTableForUnitaryService.getInstance();
                finder.generateContent(TS, list[i], loader,dir,open);

             }

             // traitment des methodes de type delete
              if (type.equals("delete"))
             {
                DeleteFitnesTableForUnitaryService deletor =DeleteFitnesTableForUnitaryService.getInstance();
                deletor.generateContent(TS, list[i], loader,dir,open);

             }

             // traitment des methodes de type create
              if (type.equals("create"))
             {
                CreateFitnesTableForUnitaryService creator = CreateFitnesTableForUnitaryService.getInstance();
                creator.generateContent(TS, list[i], loader,dir,open);

             }
             // traitment des methodes de type update
              if (type.equals("update"))
             {
                UpdateFitnesTableForUnitaryService updator = UpdateFitnesTableForUnitaryService.getInstance();
                updator.generateContent(TS, list[i], loader,dir,open);

             }

         }*/
         
         
         // traitetement des services fonctionnels
      //   else
         {
        	

             // traitment des methodes de type delete
              if (type.equals("delete"))
             {
            	  DeleteFitnesTableForFuctionnalService deletor =new DeleteFitnesTableForFuctionnalService();
                   deletor.generateContent(TS, list[i],loader, dir,open);

             }

             // traitment des methodes de type create
              if (type.equals("create"))
             {
            	  CreateFitnesTableForFuctionnalService creator =new CreateFitnesTableForFuctionnalService();
                  creator.generateContent(TS, list[i],loader, dir,open);

             }
             // traitment des methodes de type update
              if (type.equals("update"))
             {
            	  UpdateFitnesTableForFuctionnalService updator =new UpdateFitnesTableForFuctionnalService();
                  updator.generateContent(TS, list[i],loader, dir,open);

             }
              
         	 // traitment des methodes de type find
        	  if (type.equals("find"))
             {
                FindFitnesTableForUnitaryService finder =FindFitnesTableForUnitaryService.getInstance();
                finder.generateContent(TS, list[i], loader,dir,open);

             }

         }
         
    
     
   }
    	  
  }
      
      catch(Exception e){
     	 System.out.println("vérifier votre fichier catalogue.xml , une ou plusieurs méthodes ne sont pas déclarées ...");
     	 
      }

}
   }
