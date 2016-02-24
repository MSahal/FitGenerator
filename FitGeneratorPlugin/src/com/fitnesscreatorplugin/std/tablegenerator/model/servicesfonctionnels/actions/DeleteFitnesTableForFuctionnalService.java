package com.fitnesscreatorplugin.std.tablegenerator.model.servicesfonctionnels.actions;

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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.fitnesscreatorplugin.std.shared.devutils.reflection.GetFieldsFromInterface;
import com.fitnesscreatorplugin.std.shared.devutils.reflection.GetInputsFromMethod;
import com.sun.codemodel.JClassAlreadyExistsException;


public class DeleteFitnesTableForFuctionnalService extends FitnesTableForFunctionnalService{

	
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

	  
	public void generateContent(Class<?> TS, Method list2, CustomClassLoader loader, String dir,boolean open) throws IOException, JClassAlreadyExistsException, CoreException
	   {
		
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
		   GetFieldsFromInterface extractFieldsFromInterface=new GetFieldsFromInterface();
            File fichier = new File(txtFile);
  	      PrintWriter out = new PrintWriter(new FileWriter(fichier));

	      System.out.println("**********generation des tableaux pour la mathode delete*************");
	      out.println();
	 

	      
	         out.println();

	                  // traitment de l'entité à créer 
	                     out.write("!|" +className+ "|\n");
	                     // extraire les attributs de l'entitÃ©
	                    // Field[] fields = params[j].getDeclaredFields();
	                     extractFieldsFromInterface.getFieldsAndReferenceFields(list2.getReturnType());
	                     ArrayList<String> fieldsName=extractFieldsFromInterface.getFieldsName();
	                 	
	                 	
	                     int count = 0;
	                     for (int s=0;s<fieldsName.size();s++)
	                     {
	                        out.write("|" + fieldsName.get(s)+ "\t\t\t\t\t\t");
	                        count++;
	                     }
	                     
	                     
	                     
	                     
	                  // traitement des parametres de type entities
	                     GetInputsFromMethod ext1 = new GetInputsFromMethod();
	        	         Class<?>[] params = ext1.runExtractInputsFromMethod(list2);
	        	         
	        	         int coutf=0;
	        	         for (int i=0;i<params.length;i++){
	        	        	   int count1 = 0;
	        	       	  if ((!(params[i].isAssignableFrom(String.class))) && (!(params[i].isPrimitive())) && (!(params[i].isAssignableFrom(Boolean.class))) && (!(params[i].isAssignableFrom(Date.class))) && !(params[i].getSimpleName().contains("ActionContext")) && (!(params[i].isAssignableFrom(list2.getReturnType())))){
	            			  
	            			  if (!Collection.class.isAssignableFrom(params[i])){
	            				  GetFieldsFromInterface extractFieldsFromInterface1=new GetFieldsFromInterface();
	            				    extractFieldsFromInterface1.getFieldsAndReferenceFields(params[i]);
	       	                     ArrayList<String> fieldsName1=extractFieldsFromInterface1.getFieldsName();
	       	                 	
	       	                 	
	       	                  
	       	                     for (int s=0;s<fieldsName1.size();s++)
	       	                     {
	       	                    	
	       	                        out.write("|" + fieldsName1.get(s) + "\t\t\t\t\t\t");
	       	                        count1++;
	       	                     }
	       	                     
	                    		 
	            			  }

	                		  }
	        	       	coutf=coutf+count1;
	        	         }
	                     
	                     
	                
	              		
	             		// traitement des listes
	        	         // verification des types des entités dans les listes
	        	            ArrayList<Class> typeList=new ArrayList<Class>(); 
	        	            Type[] genericParameterTypes1 = list2.getGenericParameterTypes();
	        	            for(Type genericParameterType : genericParameterTypes1){
	        	            	if(genericParameterType instanceof ParameterizedType){
	        	            		 ParameterizedType aType = (ParameterizedType) genericParameterType;
	        	     		        Type[] parameterArgTypes = aType.getActualTypeArguments();
	        	     		        for(Type parameterArgType : parameterArgTypes){
	        	     		        	Class parameterArgClassTemp = (Class) parameterArgType;
	        	     		        	typeList.add(parameterArgClassTemp);
	        	     		        	
	        	     		        }
	        	            	}
	        	            	
	        	            }
	        	            
	        	            boolean test=false;
	        	           for (int i=0;i<typeList.size();i++){
	        	        	   Class temp=typeList.get(i);
	        	        	   int j=0;
	        	        	   while((j<typeList.size()) && (j != i)){
	        	        		   
	        	        		   if (temp.equals(typeList.get(j))) {test=true;}
	        	        		   j++;
	        	        	   }
	        	           }
	        	         
	        	         
	        	         
	        	         
	        	         
	        	         
	        	         
	        	           if (test==false){
	        	         
	        	         
	             		Type[] genericParameterTypes = list2.getGenericParameterTypes();

	             		for(Type genericParameterType : genericParameterTypes){
	             			int count1 = 0;
	             		    if(genericParameterType instanceof ParameterizedType){
	             		        ParameterizedType aType = (ParameterizedType) genericParameterType;
	             		        Type[] parameterArgTypes = aType.getActualTypeArguments();
	             		        for(Type parameterArgType : parameterArgTypes){
	             		        	
	             		        	Class<?>  parameterArgClass = (Class<?>) parameterArgType;  
	             		 		  GetFieldsFromInterface extractFieldsFromInterface2=new GetFieldsFromInterface();
	            				    extractFieldsFromInterface2.getFieldsAndReferenceFields(parameterArgClass);
	       	                     ArrayList<String> fieldsName2=extractFieldsFromInterface2.getFieldsName();

	       	                     for (int s=0;s<fieldsName2.size();s++)
	       	                     {
	       	                    
	       	                        out.write("|" + fieldsName2.get(s) + "\t\t\t\t\t\t");
	       	                        count1++;
	       	                     }
	                     
	                     
	             		        }
	             		    
	             		    
	     
	             		}
	             		   coutf=coutf+count1; 
	             		}
	                     
	        	           }
	        	           
	        	           
	        	           else{
	        	        	   
	        	        	   int r=0;
	  	        	         
	   	             		Type[] genericParameterTypes = list2.getGenericParameterTypes();

	   	             		for(Type genericParameterType : genericParameterTypes){
	   	             			int count1 = 0;
	   	             		    if(genericParameterType instanceof ParameterizedType){
	   	             		    r++;
	   	             		        ParameterizedType aType = (ParameterizedType) genericParameterType;
	   	             		        Type[] parameterArgTypes = aType.getActualTypeArguments();
	   	             		        for(Type parameterArgType : parameterArgTypes){
	   	             		        	
	   	             		        	Class<?>  parameterArgClass = (Class<?>) parameterArgType;  
	   	             		 		  GetFieldsFromInterface extractFieldsFromInterface2=new GetFieldsFromInterface();
	   	            				    extractFieldsFromInterface2.getFieldsAndReferenceFields(parameterArgClass);
	   	       	                     ArrayList<String> fieldsName2=extractFieldsFromInterface2.getFieldsName();

	   	       	                     for (int s=0;s<fieldsName2.size();s++)
	   	       	                     {
	   	       	                    
	   	       	                        out.write("|" + fieldsName2.get(s)+r + "\t\t\t\t\t\t");
	   	       	                        count1++;
	   	       	                     }
	   	                     
	   	                     
	   	             		        }
	   	             		    
	   	             		    
	   	     
	   	             		}
	   	             		   coutf=coutf+count1; 
	   	             		}
	        	        	   
	        	           }
	                     
	                     
	                     
	                     
	                     
	                     
	                     
	                     //les methodes isCreated and getErrorCode

	                     out.write("|isDeleted" +"?\t\t\t\t\t\t");
	                     out.write("|getErrorCode?\t\t\t\t\t\t");
	                     count++;

	                     out.write("|\n");
	                     for (int h = 0; h <= count+coutf; h++)
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
