package com.fitnesscreatorplugin.std.classegenerator.controller.dispatcher;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBException;



import org.eclipse.core.runtime.CoreException;

import com.fitnesscreatorplugin.std.classegenerator.model.servicesfonctionnels.actions.CreateFitnesClassForFuctionnalService;
import com.fitnesscreatorplugin.std.classegenerator.model.servicesfonctionnels.actions.DeleteFitnesClassForFuctionnalService;
import com.fitnesscreatorplugin.std.classegenerator.model.servicesfonctionnels.actions.UpdateFitnesClassForFuctionnalService;
import com.fitnesscreatorplugin.std.classegenerator.model.servicesunitaires.actions.CreateFitnesClassForUnitaryService;
import com.fitnesscreatorplugin.std.classegenerator.model.servicesunitaires.actions.DeleteFitnesClassForUnitaryService;
import com.fitnesscreatorplugin.std.classegenerator.model.servicesunitaires.actions.FindFitnesClassForUnitaryService;
import com.fitnesscreatorplugin.std.classegenerator.model.servicesunitaires.actions.UpdateFitnesClassForUnitaryService;
import com.fitnesscreatorplugin.std.shared.devutils.config.model.jaxb.unmarshall.Service;
import com.fitnesscreatorplugin.std.shared.devutils.config.xml.unmarshalling.MarshalCatalogueFile;
import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.fitnesscreatorplugin.std.shared.devutils.service.identity.ServiceIdentifier;
import com.sun.codemodel.JClassAlreadyExistsException;



public class ClassesGeneratorDispatcher 
{

   @SuppressWarnings("rawtypes")
   public void generateFixture(Class TS, String path, CustomClassLoader loader,Method[] list,boolean open) throws IOException, JClassAlreadyExistsException, ClassNotFoundException,
            SecurityException, NoSuchMethodException, JAXBException, CoreException
   {

      
      
      int i;
      try{
      for (i = 0; i < list.length; i++)
      {
    	  

         // extraire les paramÃ©tres de chaque methode
        // ExtractInputsFromMethod ext1 = new ExtractInputsFromMethod();
         //Class[] params = ext1.runExtractInputsFromMethod(list[i]);
         
         MarshalCatalogueFile parser = new MarshalCatalogueFile();
         Service catalogue = parser.execute();
         ServiceIdentifier disp = new ServiceIdentifier();
         String result=disp.compare(catalogue, list[i].getName());
         String type=result;
         
         
         System.out.println("le type de la methode est : "+type);
       
         // tratitement des services unitaires
         
        /* if (!(TS.getSimpleName().contains("Functional")))
         {

            // traitment des methodes de type find
            if (type.equals("find"))
            {
               FindFitnesClassForUnitaryService finder = new FindFitnesClassForUnitaryService();
               finder.generateFixture(TS, list[i], path, loader,open);

            }

            // traitment des methodes de type delete
            if (type.equals("delete"))
            {
               DeleteFitnesClassForUnitaryService deletor = new DeleteFitnesClassForUnitaryService();
               deletor.generateFixture(TS, list[i], path, loader,open);

            }

            // traitment des methodes de type create
            if (type.equals("create"))
            {
               CreateFitnesClassForUnitaryService creator = new CreateFitnesClassForUnitaryService();
               creator.generateFixture(TS, list[i], path, loader,open);

            }
            // traitment des methodes de type update
            if (type.equals("update"))
            {
               UpdateFitnesClassForUnitaryService updator = new UpdateFitnesClassForUnitaryService();
               updator.generateFixture(TS, list[i], path, loader,open);

            }

         }*/
         
         // tratitement des services fonctionnels
        // else
         {

            // traitment des methodes de type delete
            if (type.equals("delete"))
            {
            	DeleteFitnesClassForFuctionnalService deletor = new DeleteFitnesClassForFuctionnalService();
                 deletor.generateFixture(TS, list[i], path, loader,open);

            }

            // traitment des methodes de type create
            if (type.equals("create"))
            {
            	CreateFitnesClassForFuctionnalService creator = new CreateFitnesClassForFuctionnalService();
               creator.generateFixture(TS, list[i], path, loader,open);

            }
            // traitment des methodes de type update
            if (type.equals("update"))
            {
            	UpdateFitnesClassForFuctionnalService updator = new UpdateFitnesClassForFuctionnalService();
               updator.generateFixture(TS, list[i], path, loader,open);

            }
            
            // traitment des methodes de type find
            if (type.equals("find"))
            {
               FindFitnesClassForUnitaryService finder = new FindFitnesClassForUnitaryService();
               finder.generateFixture(TS, list[i], path, loader,open);

            }

         }
    	
         
      }
      }
      catch(Exception e){
     	 System.out.println("vérifier votre fichier catalogue.xml , une ou plusieurs méthodes ne sont pas déclarées ...");
     	 e.printStackTrace();
      }
   }

}
