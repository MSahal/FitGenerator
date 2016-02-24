package com.fitnesscreatorplugin.std.tablegenerator.model.servicesunitaires.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;



import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.fitnesscreatorplugin.std.shared.devutils.reflection.GetFieldsFromInterface;
import com.fitnesscreatorplugin.std.shared.devutils.reflection.GetInputsFromMethod;
import com.sun.codemodel.JClassAlreadyExistsException;


public class FindFitnesTableForUnitaryService extends FitnesTableForUnitaryService{
	
	
	
	private FindFitnesTableForUnitaryService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static FindFitnesTableForUnitaryService getInstance()
	{
		return new FindFitnesTableForUnitaryService();
	}


	/**
	 * @uml.property  name="className"
	 */
	String className;

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

	   
	public void generateContent(Class<?> TS, Method list2,  CustomClassLoader loader, String dir,boolean open) throws IOException, JClassAlreadyExistsException
	   {
		 

	   System.out.println("**********generation des tableaux pour la mathode find*************");

	  
	   
	    //
	      
	      // extraire les paramétres de chaque methode
	         GetInputsFromMethod ext1 = new GetInputsFromMethod();
	         Class<?>[] params = ext1.runExtractInputsFromMethod(list2);  
	      

		  ArrayList<Class<?>> otherEntitiies=new ArrayList<Class<?>>();
		
		     
		      
		      for (int i=0;i<params.length;i++){
		
		    		  if ((!(params[i].isAssignableFrom(String.class))) && (!(params[i].isPrimitive())) && (!(params[i].isAssignableFrom(Boolean.class))) && (!(params[i].isAssignableFrom(Date.class))) && !(params[i].getSimpleName().contains("ActionContext")) ){
		    			  
		    				  otherEntitiies.add(params[i]);

		        		  }
		    	  }


	         if (!(Collection.class.isAssignableFrom(list2.getReturnType())))
         	  {
	        	 
//	        		String TSPackage=TS.getPackage().toString().substring(8);
//	        		int indexOfServices=TSPackage.indexOf(".services.");
//	        		String domain=TSPackage.substring(indexOfServices+10, TSPackage.length()-8);
//	        		
//
//	        	    String fitpack="com.linedata.ekip.std.acceptance.test."+domain;
	        		String actualpackage=TS.getPackage().toString().substring(8);
	        		String fitpack="";
	        		if (actualpackage.contains(".services.")){
	        			
	        			int indexOfServices=actualpackage.indexOf(".services.");
	        			String domain=actualpackage.substring(indexOfServices+10, actualpackage.length()-8);
	        			

	        		     fitpack="com.linedata.ekip.std.acceptance.test."+domain;
	        		}
	        		
	        		else {
	        			fitpack="com.linedata.ekip.std.acceptance.test."+actualpackage.substring(18);
	        		}
	        		
	       
			      className = fitpack+"."+toUpperCaseFirstChar(list2.getName())+list2.getReturnType().getSimpleName()+ "AcceptanceTest";
			      String txtFile = dir + "\\" +TS.getSimpleName()+"-"+list2.getName() +"-content" + ".txt";
	        	  
	        	  GetFieldsFromInterface extractFieldsFromInterface1=new GetFieldsFromInterface();
	              File fichier = new File(txtFile);
	    	      PrintWriter out = new PrintWriter(new FileWriter(fichier));
	    	      out.println();
	      
	                  // traitment des methodes de type find
	                 

	                     // extraire les attributs de l'entité reference
	                    // Field[] fields = params[j].getDeclaredFields();
	    	      int count = 0;
	    	      out.write("!|" +className + "|\n");
	    	      for (int l=0;l<otherEntitiies.size();l++){
	    	    	  GetFieldsFromInterface extractFieldsFromInterface=new GetFieldsFromInterface();
	                     extractFieldsFromInterface.getFields(otherEntitiies.get(l));
	                     ArrayList<String> fieldsName=extractFieldsFromInterface.getFieldsName();

	                     for (int k=0;k<fieldsName.size();k++)
	                     {
	                        out.write("|" + fieldsName.get(k) + "\t\t\t\t\t\t");
	                        count++;
	                     }
	    	      }
	                     //Field[] fields1 = list2.getReturnType().getDeclaredFields();
	                     extractFieldsFromInterface1.getFields(list2.getReturnType());
	                     ArrayList<String> fieldsName1=extractFieldsFromInterface1.getFieldsName();

	                     for (int k=0;k<fieldsName1.size();k++)
	                     {
	                        out.write("|get" + toUpperCaseFirstChar(fieldsName1.get(k).substring(0, fieldsName1.get(k).lastIndexOf("Of"))) + "?\t\t\t\t\t\t");
	                        count++;
	                     }

	                     out.write("|\n");
	                     for (int h = 0; h <= count; h++)
	                     {
	                        out.write("|");
	                     }
	                     
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
	          	  
	          	  else{
	          		  

	          		  
	          		  ArrayList<Class<?>> otherEntities=new ArrayList<Class<?>>();  
		    		      for (int i=0;i<params.length;i++){
		    		    		  if ((!(params[i].isAssignableFrom(String.class))) && (!(params[i].isPrimitive())) && (!(params[i].isAssignableFrom(Boolean.class))) && (!(params[i].isAssignableFrom(Date.class))) && !(params[i].getSimpleName().contains("ActionContext")) ){
		    		    				  otherEntities.add(params[i]);	
		    		        		  }
		    		    	  }

	          			 // extraire le type des elements dans la liste
	      			   Class<?> listElementType=null;
	  	            	Type genericFieldType = list2.getGenericReturnType();
	  	            	System.out.println("le type generique: "+ list2.getGenericReturnType());
	  	            	if(genericFieldType instanceof ParameterizedType){
	  	            	    ParameterizedType aType = (ParameterizedType) genericFieldType;
	  	            	    
	  	            	    Type[] fieldArgTypes = aType.getActualTypeArguments();
	  	            	    for(Type fieldArgType : fieldArgTypes){
	  	            	    	listElementType = (Class<?>) fieldArgType;      
	  	            	    }
	  	            	  if (list2.getName().contains(listElementType.getSimpleName())) {
	  	           	    	className=list2.getName();
	  	           	    }
	  	           	    else className=list2.getName()+listElementType.getSimpleName();

//	  	            	String TSPackage=TS.getPackage().toString().substring(8);
//	  	      		int indexOfServices=TSPackage.indexOf(".services.");
//	  	      		String domain=TSPackage.substring(indexOfServices+10, TSPackage.length()-8);
//	  	      		
//
//	  	      	    String fitpack="com.linedata.ekip.std.acceptance.test."+domain;
	  	            	String actualpackage=TS.getPackage().toString().substring(8);
		        		String fitpack="";
		        		if (actualpackage.contains(".services.")){
		        			
		        			int indexOfServices=actualpackage.indexOf(".services.");
		        			String domain=actualpackage.substring(indexOfServices+10, actualpackage.length()-8);
		        			

		        		     fitpack="com.linedata.ekip.std.acceptance.test."+domain;
		        		}
		        		
		        		else {
		        			fitpack="com.linedata.ekip.std.acceptance.test."+actualpackage.substring(18);
		        		}
		        		
	  	            	  

		          		 String txtFile = dir + "\\" +TS.getSimpleName()+"-"+list2.getName() +"-content" + ".txt";
			              File fichier = new File(txtFile);
			    	      PrintWriter out = new PrintWriter(new FileWriter(fichier));
			    	      out.println("!*> setup");
			    	      out.println("|import								        |"); 
			    	      out.println("|com.linedata.ekip.std.acceptance.test.query|");
			    	      out.println("*! ");
		                     out.write("!|Query:" +fitpack+"."+toUpperCaseFirstChar(className)+ "AcceptanceTest" + "|\t\t");
		                     int count = 0;
		                     GetFieldsFromInterface extractFieldsFromInterface3=new GetFieldsFromInterface();
		                     extractFieldsFromInterface3.getFields(listElementType);
		                     ArrayList<String> fieldsName3=extractFieldsFromInterface3.getFieldsName();
		                     for (int k=0;k<fieldsName3.size();k++)
		                     {
		                        out.write("|\t\t\t");
		                        
		                     }

		                     for (int p=0;p<otherEntities.size();p++){
		                    	 
		                    	 GetFieldsFromInterface extractFieldsFromInterface4=new GetFieldsFromInterface();
			                     extractFieldsFromInterface4.getFields(otherEntities.get(p));
			                     ArrayList<String> fieldsName4=extractFieldsFromInterface4.getFieldsName();
			                     
			                     for (int k=1;k<fieldsName4.size();k++)
			                     {
			                        out.write("|\t\t\t");
			                        
			                     }
		                     }
		                     
		                     out.write("\n");
		                    // Field[] fields1 = listElementType.getDeclaredFields();
		                     GetFieldsFromInterface extractFieldsFromInterface4=new GetFieldsFromInterface();
		                     extractFieldsFromInterface4.getFields(listElementType);
		                     ArrayList<String> fieldsName4=extractFieldsFromInterface4.getFieldsName();

		                     for (int k=0;k<fieldsName4.size();k++)
		                     {
		                        out.write("|" + toUpperCaseFirstChar(fieldsName4.get(k).substring(0, fieldsName4.get(k).lastIndexOf("Of"))) + "\t\t\t\t\t\t");
		                        count++;
		                     }
		                     
		                     
		                     for (int p=0;p<otherEntities.size();p++){
		                    	 
		                    	 GetFieldsFromInterface extractFieldsFromInterface5=new GetFieldsFromInterface();
			                     extractFieldsFromInterface5.getFields(otherEntities.get(p));
			                     ArrayList<String> fieldsName5=extractFieldsFromInterface5.getFieldsName();
			                     
			                     for (int k=0;k<fieldsName5.size();k++)
			                     {
			                         out.write("|" + fieldsName5.get(k) + "\t\t\t\t\t\t");
				                        count++;
			                        
			                     }
		                     }
		                     

		                     out.write("|\n");
		                     for (int h = 0; h <= count; h++)
		                     {
		                        out.write("|");
		                     }
		                     
		                     out.println();

		                     out.close(); 
		                     if (open==true){
		            	         IFileStore fileStore = EFS.getLocalFileSystem().getStore(fichier.toURI());
		            	         IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		            	      
		            	         try {
		            	             IDE.openEditorOnFileStore( page, fileStore );
		            	         } catch ( PartInitException e ) {
		            	            
		            	         }
		            	         }
		               }
  
	          	  }
	     
	         }
	     // }
	   //}

	
	   }


