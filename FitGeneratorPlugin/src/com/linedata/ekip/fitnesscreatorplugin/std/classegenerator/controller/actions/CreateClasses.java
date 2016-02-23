package com.linedata.ekip.fitnesscreatorplugin.std.classegenerator.controller.actions;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;

import com.linedata.ekip.fitnesscreatorplugin.std.classegenerator.controller.dispatcher.ClassesGeneratorDispatcher;
import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.sun.codemodel.JClassAlreadyExistsException;



public class CreateClasses 
{


   public void write(String dir, ICompilationUnit cu,ArrayList<Method> list,boolean open) throws ClassNotFoundException, IOException, JClassAlreadyExistsException, SecurityException, NoSuchMethodException,
            JAXBException, CoreException
   {

	  
      
         CustomClassLoader classLoader = new CustomClassLoader();
         String pack = cu.getPackageDeclarations()[0].toString().substring(8, cu.getPackageDeclarations()[0].toString().indexOf("[") - 1);
         String test = cu.getCorrespondingResource().getName();

         // Need
         String[] name = test.split("\\.");
         String txtFile = dir;
         
         Class<?> clas = null;
        
        

         
         System.out.println("loading the selected java class ..."+pack + "." + name[0]);
        clas = classLoader.loadClass(pack + "." + name[0]);

         Method[] list2 = new Method[list.size()] ; 
         for (int i=0;i<list2.length;i++){
        	 list2[i]=list.get(i);
         }
         
         ClassesGeneratorDispatcher gen = new ClassesGeneratorDispatcher();
         
         gen.generateFixture(clas, txtFile, classLoader,list2,open);
         
         
         
      }
     

   }


