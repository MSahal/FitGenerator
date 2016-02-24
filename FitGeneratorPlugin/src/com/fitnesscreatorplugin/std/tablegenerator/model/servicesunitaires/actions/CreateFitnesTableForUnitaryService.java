package com.fitnesscreatorplugin.std.tablegenerator.model.servicesunitaires.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.fitnesscreatorplugin.std.shared.devutils.reflection.GetFieldsFromInterface;
import com.sun.codemodel.JClassAlreadyExistsException;


public class CreateFitnesTableForUnitaryService extends FitnesTableForUnitaryService{
	
	

	
	private CreateFitnesTableForUnitaryService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static CreateFitnesTableForUnitaryService getInstance()
	{
		
		return new CreateFitnesTableForUnitaryService();
	}

	
	
	
	 /**
	 * @uml.property  name="className"
	 */
	String  className;
	/**
	 * @return
	 * @uml.property  name="className"
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 * @uml.property  name="className"
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	String toUpperCaseFirstChar(String s)
	   {
	      String a = (String) s.subSequence(0, 1);

	      return a.toUpperCase() + s.substring(1);
	   }
	  String toLowerCaseFirstChar(String s)
	   {
	      String a = (String) s.subSequence(0, 1);
	      return a.toLowerCase() + s.substring(1);
	   }

	   public void generateContent(Class<?> TS, Method list2, CustomClassLoader loader, String dir,boolean open) throws IOException, JClassAlreadyExistsException
	   {
			String TSPackage=TS.getPackage().toString().substring(8);
			int indexOfServices=TSPackage.indexOf(".services.");
			String domain=TSPackage.substring(indexOfServices+10, TSPackage.length()-8);
			

		    String fitpack="com.linedata.ekip.std.acceptance.test."+domain;
			
		   
		   
		
		     
		      className = fitpack+"."+toUpperCaseFirstChar(list2.getName())+list2.getReturnType().getSimpleName()+ "AcceptanceTest";
		   
		   String txtFile = dir + "\\" +TS.getSimpleName()+"-"+list2.getName() +"-content" + ".txt";
		   GetFieldsFromInterface extractFieldsFromInterface=new GetFieldsFromInterface();
            File fichier = new File(txtFile);
  	      PrintWriter out = new PrintWriter(new FileWriter(fichier));

	      System.out.println("**********generation des tableaux pour la mathode create*************");
	      out.println();
	     
	  
	         out.println();
	        

	     
	          


	                  // traitment des methodes de type create
	                 

	                     out.write("!|" +className+ "|\n");
	                     // extraire les attributs de l'entité
	                    // Field[] fields = params[j].getDeclaredFields();
	                     extractFieldsFromInterface.getFields(list2.getReturnType());
	                     ArrayList<String> fieldsName=extractFieldsFromInterface.getFieldsName();
	                 	
	                 	
	                     int count = 0;
	                     for (int s=0;s<fieldsName.size();s++)
	                     {
	                        out.write("|" + fieldsName.get(s) + "\t\t\t\t\t\t");
	                        count++;
	                     }

	                     out.write("|isCreated" +"?\t\t\t\t\t\t");
	                     out.write("|getErrorCode?\t\t\t\t\t\t");
	                     count++;

	                     out.write("|\n");
	                     for (int h = 0; h <= count; h++)
	                     {
	                        out.write("|");
	                     }
	                     out.write("|");
	                     out.println();

	                  

	          
	      
	         
	         out.close();
	         if (open==true){
	         IFileStore fileStore = EFS.getLocalFileSystem().getStore(fichier.toURI());
	         IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	      
	         try {
	             IDE.openEditorOnFileStore( page, fileStore );
	         } catch ( PartInitException e ) {
	             //Put your exception handler here if you wish to
	         }
	         }
	      
	   }

}
