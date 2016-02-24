package com.fitnesscreatorplugin.std.tablegenerator.controller.actions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;

import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.fitnesscreatorplugin.std.tablegenerator.controller.dispatcher.TablesGeneratorDispatcher;
import com.sun.codemodel.JClassAlreadyExistsException;



public class CreateTables 
{
	
   public void write(String dir, ICompilationUnit cu,ArrayList<Method> list,boolean open) throws ClassNotFoundException, IOException, JClassAlreadyExistsException, CoreException, JAXBException
   {
      try
      {
    	    
         
    	  CustomClassLoader classLoader = new CustomClassLoader();
         String pack = cu.getPackageDeclarations()[0].toString().substring(8, cu.getPackageDeclarations()[0].toString().indexOf("[") - 1);
         String test = cu.getCorrespondingResource().getName();

         // Need
         String[] name = test.split("\\.");
        

         
   	    Method[] list2 = new Method[(list).size()] ; 
         for (int i=0;i<list2.length;i++){
        	 list2[i]=(list).get(i);
         }

         // classLoader.loadClass(pack + "." + name[0], cu);
         TablesGeneratorDispatcher gen = new TablesGeneratorDispatcher();
         Class<?> clas = null;
         
            clas = classLoader.loadClass(pack + "." + name[0]);

         gen.generateContent(clas,classLoader,dir,list2,open);

      }
      catch (JavaModelException e)
      {
      }

   }

}
