package com.fitnesscreatorplugin.std.classegenerator.model.servicesunitaires.actions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.CoreException;

import com.fitnesscreatorplugin.std.shared.devutils.importsresolver.EntityFactoryResolver;
import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.fitnesscreatorplugin.std.shared.devutils.reflection.GetFieldsFromInterface;
import com.fitnesscreatorplugin.std.shared.devutils.reflection.GetInputsFromMethod;
import com.fitnesscreatorplugin.std.shared.devutils.reflection.GetOriginalFieldsFromInterface;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;




public class FindFitnesClassForUnitaryService extends FitnesClassUnitairyService
{
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

   
   Class<?> findApproximatelyByClassName(ArrayList<Class<?>> classes, String className)
   {

      Class<?> temp = null;
      for (int i = 0; i < classes.size(); i++)
      {
         if (classes.get(i).getName().contains(className))
         {
            temp = classes.get(i);
         }
      }

      return temp;

   }

 
   Class<?> findByClassName(ArrayList<Class<?>> classes, String className)
   {

      Class<?> temp = null;
      for (int i = 0; i < classes.size(); i++)
      {
         if (classes.get(i).getName().equals(className))
         {
            temp = classes.get(i);
         }
      }

      return temp;

   }

   @SuppressWarnings({ "rawtypes", "unchecked" })
   public void generateFixture(Class TS, Method list2, String path, CustomClassLoader loader,boolean open) throws IOException, JClassAlreadyExistsException, ClassNotFoundException,
            SecurityException, NoSuchMethodException, JAXBException, CoreException
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
		
	   
//		String TSPackage=TS.getPackage().toString().substring(8);
//		int indexOfServices=TSPackage.indexOf(".services.");
//		String domain=TSPackage.substring(indexOfServices+10, TSPackage.length()-8);
		

	  //  String fitpack="com.linedata.ekip.std.acceptance.test."+domain;
		
		 EntityFactoryResolver factoryResolver=new EntityFactoryResolver();
		    
	      JCodeModel cm = new JCodeModel();
	      
	      JPackage blockServicePackage = cm._package(fitpack);
	   Class entityRefType=null;
	
      
      File file = new File(path);
      
      
      // extraire les parametres de la methode
      GetInputsFromMethod ext1 = new GetInputsFromMethod();
      Class[] params = ext1.runExtractInputsFromMethod(list2);
    


      String className = null;
      

     
    	  // traitement des methodes de type Entity find (ActionContext,EntityReference,Entity...);
    	  
    	  if (!(Collection.class.isAssignableFrom(list2.getReturnType())))
    	  {		 
    		  
    		  ArrayList<Class> otherEntitiies=new ArrayList<Class>();
    	      
		      for (int i=0;i<params.length;i++){
		 
		    		  if ((!(params[i].isAssignableFrom(String.class))) && (!(params[i].isPrimitive())) && (!(params[i].isAssignableFrom(Boolean.class))) && (!(params[i].isAssignableFrom(Date.class))) && !(params[i].getSimpleName().contains("ActionContext")) && (!(Collection.class.isAssignableFrom(params[i])))){
	
		    				  otherEntitiies.add(params[i]);

		        		  }
		    	  
		    	  
		      }
    		  
    		  
		   // traitement des methodes de type Entity find (ActionContext,EntityReference)
    		  
    		if (otherEntitiies.size()==1){  
    		  
    		  
    		  
    	      for (int x=0;x<params.length;x++){
    	    	  if (!(params[x].getSimpleName().contains("ActionContext"))){
    	    		  entityRefType=params[x];
    	    	  }
    	      }
    	      
    	      ArrayList<Class> primitiveParams=new ArrayList<Class>();
    	      ArrayList<String> primitiveParamsNames=new ArrayList<String>();
    	      for (int i=0;i<params.length;i++){
    	    	  if ((!(params[i].getSimpleName().contains("ActionContext"))) && (!(params[i].getSimpleName().contains("Reference")))){
    	    		  primitiveParams.add(params[i]);
    	    	  }
    	    	  
    	      }
    	      
    	      if (!(primitiveParams.isEmpty())){
    	      for (int i=0;i<primitiveParams.size();i++){
    	    	  primitiveParamsNames.add("param"+i);
    	      }
    	      }
    		  className=toUpperCaseFirstChar(list2.getName())+toUpperCaseFirstChar(list2.getReturnType().getSimpleName());
    		  JClass FitAcceptTest = cm.ref(loader.loadClass( "com.linedata.ekip.std.acceptance.test.common.FitnesseAcceptanceTest"));
    		  //JClass stringConverter = cm.ref(loader.loadClass( "com.linedata.ekip.std.converter.StringConverter"));
              JDefinedClass dc = blockServicePackage._class(className + "AcceptanceTest")._extends(FitAcceptTest);
             
               
              
              
              dc.field(JMod.PRIVATE, TS, toLowerCaseFirstChar(TS.getSimpleName()), null);
              dc.field(JMod.PRIVATE | JMod.STATIC | JMod.FINAL, "".getClass(), "BEANID", JExpr.direct("\"" + className + "AcceptanceTest" + "\""));
              String entityName;
              String src="initContext();\n\t\t";
    	
        	 
            // extraire les attributs de l'entite reference
           // Field[] fields = params[j].getDeclaredFields();
        	 GetFieldsFromInterface extractFieldsFromInterface=new GetFieldsFromInterface();
            extractFieldsFromInterface.getFields(entityRefType);
            ArrayList<String> fieldsName=extractFieldsFromInterface.getFieldsName();
        	ArrayList<Class> fieldsType=extractFieldsFromInterface.getFieldsType();
            String attBean = toLowerCaseFirstChar(entityRefType.getSimpleName());
           
            dc.field(0, entityRefType, attBean, null);
             entityName=toUpperCaseFirstChar(list2.getReturnType().getSimpleName());
             
             
             JClass entityFactory=null;
 			for (int jj=0;jj<params.length;jj++){
             if (!(primitiveParams.contains(params[jj])) &&  !(params[jj].getSimpleName().contains("ActionContext"))){
            System.out.println("le factory de "+params[jj].getSimpleName()+ " est : "+factoryResolver.getFactory(params[jj]));
             entityFactory = cm.ref(loader.loadClass(factoryResolver.getFactory(params[jj])));
             }
             }
 			
 			JFieldVar entityFact=null;
            // classes references
           // JClass entityReferenceFactory = cm.ref(findApproximatelyByClassName(workspaceClass, "." + params[j].getSimpleName() + "Factory"));
            //JClass entityClass = cm.ref(findApproximatelyByClassName(workspaceClass, "entity." + entityName));
           
            //JFieldVar selectedClass =
            		
            //JFieldVar BEANID = 
            		
            //JFieldVar entity = dc.field(0, entityClass, toLowerCaseFirstChar(entityName), null);
            //JFieldVar entityRef =
            dc.field(JMod.PRIVATE, list2.getReturnType(), toLowerCaseFirstChar(list2.getReturnType().getSimpleName()),JExpr._null());
            if (!entityFactory.equals(null)) entityFact = dc.field(0, entityFactory, toLowerCaseFirstChar(attBean) + "Factory", null);
            src =src+ attBean + "Factory =(" + entityRefType.getSimpleName() + "Factory) getBeanFactory().getBean (\""
                     +entityRefType.getSimpleName() + "Factory\"); \n\t\t"+toLowerCaseFirstChar(toLowerCaseFirstChar(TS.getSimpleName())) + " = (" + TS.getSimpleName() + ") getBeanFactory().getBean(\""
                     +TS.getSimpleName() +"\"); \n\t\t";
            
           // File file = new File(path);

            String listOfPrimitiveParams="";
            if (!(primitiveParamsNames.isEmpty())){
            for (int k=0;k<primitiveParamsNames.size();k++){
            	dc.field(0, primitiveParams.get(k), primitiveParamsNames.get(k), null);
            	listOfPrimitiveParams=listOfPrimitiveParams+","+primitiveParamsNames.get(k);
            }
            }
            // extraire les attributs de l'entite
           // Field[] entityFields = list2.getReturnType().getDeclaredFields();
            GetOriginalFieldsFromInterface extractFieldsFromInterface2=new GetOriginalFieldsFromInterface();
            extractFieldsFromInterface2.getFields(list2.getReturnType());
            ArrayList<String> fieldsName2=extractFieldsFromInterface2.getFieldsName();
        	ArrayList<Class> fieldsType2=extractFieldsFromInterface2.getFieldsType();
        	
        	
        	JMethod run = dc.method(JMod.PUBLIC,Void.TYPE , "runGet" + toUpperCaseFirstChar(list2.getReturnType().getSimpleName()));
        	 String source =  attBean + " = " +toLowerCaseFirstChar( entityRefType.getSimpleName()) + "Factory.buildBean();\n";
        	 run.body().directStatement(source);
        	
        	   for (int k=0;k<fieldsName.size();k++)
               {
        		   String temp="";  
        		   //
        		   if (!(fieldsType.get(k).isAssignableFrom(String.class))){
                   temp="to"+toUpperCaseFirstChar(fieldsType.get(k).getSimpleName());
            	   
            	   
            	   if (fieldsType.get(k).isAssignableFrom(Double.class)) temp="toDouble";
            	   if (fieldsType.get(k).isAssignableFrom(Long.class)) temp="toLong";
            	   if (fieldsType.get(k).isAssignableFrom(Integer.class)) temp="toInteger";
            	   if (fieldsType.get(k).isAssignableFrom(Date.class)) temp="parseDate";
            	   if (fieldsType.get(k).isAssignableFrom(Boolean.class)) temp="Boolean.valueOf";
            	   if (fieldsType.get(k).isAssignableFrom(boolean.class)) temp="Boolean.valueOf";
        		   
        		   }
        		   
        		   
        		   
                  String src1 = attBean + ".set" + toUpperCaseFirstChar(fieldsName.get(k).substring(0, fieldsName.get(k).lastIndexOf("Of"))) + "(" +temp+"("+fieldsName.get(k) + "));\n";
                  run.body().directStatement(src1);
               }
        	   
        	   run.body().directStatement(
                        toLowerCaseFirstChar(entityName) + " = " + toLowerCaseFirstChar(TS.getSimpleName()) + "." + list2.getName() + "(getActionContext(),"
                                + toLowerCaseFirstChar(attBean)+listOfPrimitiveParams + ");\n");
        	   
        	 
        	 for (int i=0;i<fieldsName2.size();i++)
            {
      
               JMethod getField = dc.method(JMod.PUBLIC,String.class , "get" + toUpperCaseFirstChar(fieldsName2.get(i)));
               if (i==0){
            	   getField.body().directStatement(run.name()+"() ;\n");
      		 }
               getField.body().directStatement("if (" + toLowerCaseFirstChar(entityName) + " !=null  && "+toLowerCaseFirstChar(list2.getReturnType().getSimpleName())+ ".get"+ toUpperCaseFirstChar(fieldsName2.get(i))+"() !=null ) {\n");
               
               
               if (!(fieldsType2.get(i).equals(String.class))){
            	   if (!(fieldsType2.get(i).equals(Date.class))){
            		   getField.body().directStatement("return StringConverter." +"convertObjectToString"+"(" +toLowerCaseFirstChar(list2.getReturnType().getSimpleName()) +".get" + toUpperCaseFirstChar(fieldsName2.get(i)) + "()) ;\n}");  
            	   }

            	   else{
               getField.body().directStatement("return getSdf()." +"format"+"(" +toLowerCaseFirstChar(list2.getReturnType().getSimpleName()) +".get" + toUpperCaseFirstChar(fieldsName2.get(i)) + "()) ;\n}");
               
               }
               }
               
               else{
            	   
                   getField.body().directStatement("return " +toLowerCaseFirstChar(list2.getReturnType().getSimpleName()) +".get" + toUpperCaseFirstChar(fieldsName2.get(i)) + "() ;\n}");
                   
                   }
               
               getField.body().directStatement("return null; \n ");
            }
        	 
        	 
        	 
             for (int i=0;i<fieldsName.size();i++)
             {
                // generation des attributs
                //JFieldVar temp = 
               dc.field(JMod.PRIVATE, String.class, fieldsName.get(i), null);

                // generation des setters
                JMethod setters = dc.method(JMod.PUBLIC, Void.TYPE, "set" + toUpperCaseFirstChar( fieldsName.get(i)));
                JMethod getters = dc.method(JMod.PUBLIC, String.class, "get" + toUpperCaseFirstChar( fieldsName.get(i)));

                setters.param(String.class,  fieldsName.get(i));
                setters.body().directStatement("this." +  fieldsName.get(i) + " = " +  fieldsName.get(i) + " ;");
                getters.body().directStatement(" return "+fieldsName.get(i)+" ;");
             }


    		  dc.constructor(JMod.PUBLIC).body().directStatement(src);	  
    		}  
    		
    		

      	  // traitement des methodes de type Entity find (ActionContext,EntityReference,Entity...);
    		
    		else{

  			   Class listElementType=list2.getReturnType();

             	    
             	    if (list2.getName().contains(listElementType.getSimpleName())) {
             	    	className=list2.getName();
             	    }
             	    else className=list2.getName()+listElementType.getSimpleName();
             	  JClass FitAcceptTest = cm.ref(loader.loadClass( "com.linedata.ekip.std.acceptance.test.common.FitnesseAcceptanceTest"));
  	            JDefinedClass dc = blockServicePackage._class(toUpperCaseFirstChar(className) + "AcceptanceTest")._extends(FitAcceptTest);
  	            
  	            
  	            
  	          GetOriginalFieldsFromInterface extractFieldsFromInterface2=new GetOriginalFieldsFromInterface();
              extractFieldsFromInterface2.getFields(list2.getReturnType());
              ArrayList<String> fieldsName2=extractFieldsFromInterface2.getFieldsName();
          	ArrayList<Class> fieldsType2=extractFieldsFromInterface2.getFieldsType();
  	            
  	            
  	            
  	    		  ArrayList<Class> otherEntities=new ArrayList<Class>();
  	    		    String[] paramsList=new String[params.length];
  	    		      for (int c=0;c<paramsList.length;c++){
  	    		      	paramsList[c]="0";
  	    		      }
  	    		      
  	    		      
  	    		      // recuperation de la liste des parametres de la methode dans une chaine pour l'utiliser aprés lors de l'appel de service 
  	    		      
  	    		      for (int i=0;i<params.length;i++){
  	    		    	  
  	    		    	  if (params[i].getSimpleName().contains("ActionContext")) paramsList[i]="getActionContext()";
  	    		    	  if (((params[i].isAssignableFrom(String.class))) || ((params[i].isPrimitive())) || ((params[i].isAssignableFrom(Boolean.class))) || ((params[i].isAssignableFrom(Date.class)))) paramsList[i]=",primitiveParam"+i;
  	    		    	 
  	    		    	
  	    		    	  
  	    		    	 
  	    		    	  if ((!(params[i].getSimpleName().contains("ActionContext"))) && (!(params[i].isAssignableFrom(list2.getReturnType())))){
  	    		    		  if (((params[i].isAssignableFrom(String.class))) || ((params[i].isPrimitive())) || ((params[i].isAssignableFrom(Boolean.class))) || ((params[i].isAssignableFrom(Date.class)))){
  	    		    		  
  	    		    		 dc.field(0, params[i], "primitiveParam"+i, null);
  	    		    		 
  	    		    	  }
  	    		    		  if ((!(params[i].isAssignableFrom(String.class))) && (!(params[i].isPrimitive())) && (!(params[i].isAssignableFrom(Boolean.class))) && (!(params[i].isAssignableFrom(Date.class))) && !(params[i].getSimpleName().contains("ActionContext")) ){
  	    		    			  
  	    		    			
  	    		    				  otherEntities.add(params[i]);
  	    		    				  paramsList[i]=","+toLowerCaseFirstChar(params[i].getSimpleName());
  	 

  	    		        		  }
  	    		    	  }
  	    		    	  
  	    		      }

  	            //JFieldVar listElementTypeName = 
  	            		dc.field(0, listElementType, toLowerCaseFirstChar(listElementType.getSimpleName()), null);
  	            //JFieldVar selectedClass =
  	            		dc.field(JMod.PRIVATE, TS, toLowerCaseFirstChar(TS.getSimpleName()), null);
  	            //JFieldVar BEANID = 
  	            		dc.field(JMod.PRIVATE | JMod.STATIC | JMod.FINAL, "".getClass(), "BEANID", JExpr.direct("\"" + className + "AcceptanceTest" + "\""));
  	            String src = "initContext();\n\t\t"+toLowerCaseFirstChar(toLowerCaseFirstChar(TS.getSimpleName())) + " = (" + TS.getSimpleName() + ") getBeanFactory().getBean(\""
  	                     +TS.getSimpleName() +"\"); \n\t\t";
  	            String attBean = null;
  	            String src1="";
  	            String source1="";
  	            
  	          
  	            JMethod getRun = dc.method(JMod.PUBLIC,Void.TYPE , "runGet" + toUpperCaseFirstChar(list2.getReturnType().getSimpleName()));
   	            
  	       	 for (int i=0;i<fieldsName2.size();i++)
             {
       
                JMethod getField = dc.method(JMod.PUBLIC,String.class , "get" + toUpperCaseFirstChar(fieldsName2.get(i)));
                if (i==0){
             	   getField.body().directStatement(getRun.name()+"() ;\n");
       		 }
                getField.body().directStatement("if (" + toLowerCaseFirstChar(toUpperCaseFirstChar(list2.getReturnType().getSimpleName())) + " !=null  && "+toLowerCaseFirstChar(list2.getReturnType().getSimpleName())+ ".get"+ toUpperCaseFirstChar(fieldsName2.get(i))+"() !=null ) {\n");
                
                
                if (!(fieldsType2.get(i).equals(String.class))){
             	   if (!(fieldsType2.get(i).equals(Date.class))){
             		   getField.body().directStatement("return StringConverter." +"convertObjectToString"+"(" +toLowerCaseFirstChar(list2.getReturnType().getSimpleName()) +".get" + toUpperCaseFirstChar(fieldsName2.get(i)) + "()) ;\n}");  
             	   }

             	   else{
                getField.body().directStatement("return getSdf()." +"format"+"(" +toLowerCaseFirstChar(list2.getReturnType().getSimpleName()) +".get" + toUpperCaseFirstChar(fieldsName2.get(i)) + "()) ;\n}");
                
                }
                }
                
                else{
             	   
                    getField.body().directStatement("return " +toLowerCaseFirstChar(list2.getReturnType().getSimpleName()) +".get" + toUpperCaseFirstChar(fieldsName2.get(i)) + "() ;\n}");
                    
                    }
                
                getField.body().directStatement("return null; \n ");
             }
  		
   	          Class entityRefType1 = null;
   	           for(int p=0;p<otherEntities.size();p++ ){
      			
   	        	    entityRefType1 = otherEntities.get(p);
      	            GetFieldsFromInterface extractFieldsFromInterface3=new GetFieldsFromInterface();
      	            extractFieldsFromInterface3.getFields( entityRefType1);
      	            ArrayList<String> fieldsName3=extractFieldsFromInterface3.getFieldsName();
      	        	ArrayList<Class> fieldsType3=extractFieldsFromInterface3.getFieldsType();
      	            attBean = toLowerCaseFirstChar(entityRefType1.getSimpleName());
      	            dc.field(0, entityRefType1, toLowerCaseFirstChar(attBean), null);
      	            JClass entityFactory=null;
      	 			
      	            
      	          entityFactory = cm.ref(loader.loadClass(factoryResolver.getFactory(otherEntities.get(p))));
      	           
      	          /*JFieldVar entityRefFact =*/ dc.field(0, entityFactory, toLowerCaseFirstChar(attBean) + "Factory", null);
      	            src =src+ attBean + "Factory =(" + entityRefType1.getSimpleName() + "Factory) getBeanFactory().getBean (\""
      	                     + entityRefType1.getSimpleName() + "Factory\");\n\t\t";
      	        	 for (int i=0;i<fieldsName3.size();i++)
      	            {
      	               // generation des attributs
      	               //JFieldVar temp = 
      	            	dc.field(JMod.PRIVATE, String.class, fieldsName3.get(i), null);

      	               // generation des setters
      	               JMethod setters = dc.method(JMod.PUBLIC, Void.TYPE, "set" + toUpperCaseFirstChar(fieldsName3.get(i)));
      	               JMethod getters = dc.method(JMod.PUBLIC, String.class, "get" + toUpperCaseFirstChar(fieldsName3.get(i)));
      	               setters.param(String.class, fieldsName3.get(i));
      	               setters.body().directStatement("this." + fieldsName3.get(i) + " = " + fieldsName3.get(i) + " ;");
      	               getters.body().directStatement("return "+fieldsName3.get(i)+";");
      	            }
      	            
      	            // la methode getRun
      	          
      	           source1 =source1+ attBean + " = " + toLowerCaseFirstChar(entityRefType1.getSimpleName()) + "Factory.buildBean();\n";
      	           
      	           for (int i=0;i<fieldsName3.size();i++)
  	               {
      	        	   
      	        	   String temp1="";  
              		   //
              		   if (!(fieldsType3.get(i).isAssignableFrom(String.class))){
                         temp1="to"+toUpperCaseFirstChar(fieldsType3.get(i).getSimpleName());
                  	   
                  	   
                  	   if (fieldsType3.get(i).isAssignableFrom(Double.class)) temp1="toDouble";
                  	   if (fieldsType3.get(i).isAssignableFrom(Long.class)) temp1="toLong";
                  	   if (fieldsType3.get(i).isAssignableFrom(Integer.class)) temp1="toInteger";
                  	   if (fieldsType3.get(i).isAssignableFrom(Date.class)) temp1="parseDate";
                  	   if (fieldsType3.get(i).isAssignableFrom(Boolean.class)) temp1="Boolean.valueOf";
                  	 if (fieldsType3.get(i).isAssignableFrom(boolean.class)) temp1="Boolean.valueOf";
              		   
              		   }

                         src1 = src1+attBean + ".set" + toUpperCaseFirstChar(fieldsName3.get(i).substring(0, fieldsName3.get(i).lastIndexOf("Of"))) + "(" +temp1+"("+fieldsName3.get(i) + "));\n";
  	               }
     
   	           }
   	        String listOfParams="";
	            for (int c=0;c<paramsList.length;c++){
	            	listOfParams=listOfParams+paramsList[c];	
	            }

  	            dc.constructor(JMod.PUBLIC).body().directStatement(src);
  	    		 
  		           getRun.body().directStatement(source1);
  		           getRun.body().directStatement(src1);
  		        
  		         getRun.body().directStatement(
  	                    toLowerCaseFirstChar( toLowerCaseFirstChar(list2.getReturnType().getSimpleName())) + " = " + toLowerCaseFirstChar(TS.getSimpleName()) + "." + list2.getName() + "("
  	                            + listOfParams + ");\n");	
    			
    		}
	  
      }
    	  
    	  
    	  
    	  
    	  
    	// traitement des methodes de type List<Entity> find (ActionContext,EntityReference,Entity',Entity", ...);
    	  
    	  else{
    		 

    		  // extraire le type des elements dans la liste
			   Class listElementType=null;
           	Type genericFieldType = list2.getGenericReturnType();
           	System.out.println("le type generique: "+ list2.getGenericReturnType());
           	if(genericFieldType instanceof ParameterizedType){
           	    ParameterizedType aType = (ParameterizedType) genericFieldType;
           	    
           	    Type[] fieldArgTypes = aType.getActualTypeArguments();
           	    for(Type fieldArgType : fieldArgTypes){
           	    	listElementType = (Class) fieldArgType;      
           	    }
           	    
           	    if (list2.getName().contains(listElementType.getSimpleName())) {
           	    	className=list2.getName();
           	    }
           	    else className=list2.getName()+listElementType.getSimpleName();
           	  JClass FitAcceptTest = cm.ref(loader.loadClass( "com.linedata.ekip.std.acceptance.test.common.FitnesseAcceptanceTest"));
	            JDefinedClass dc = blockServicePackage._class(toUpperCaseFirstChar(className) + "AcceptanceTest")._extends(FitAcceptTest);
	            
	            
	            
	            
	    		  ArrayList<Class> otherEntities=new ArrayList<Class>();
	    		    String[] paramsList=new String[params.length];
	    		      for (int c=0;c<paramsList.length;c++){
	    		      	paramsList[c]="0";
	    		      }
	    		      
	    		      
	    		      // recuperation de la liste des parametres de la methode dans une chaine pour l'utiliser aprés lors de l'appel de service 
	    		      
	    		      for (int i=0;i<params.length;i++){
	    		    	  
	    		    	  if (params[i].getSimpleName().contains("ActionContext")) paramsList[i]="getActionContext()";
	    		    	  if (((params[i].isAssignableFrom(String.class))) || ((params[i].isPrimitive())) || ((params[i].isAssignableFrom(Boolean.class))) || ((params[i].isAssignableFrom(Date.class)))) paramsList[i]=",primitiveParam"+i;
	    		    	 
	    		    	
	    		    	  
	    		    	 
	    		    	  if ((!(params[i].getSimpleName().contains("ActionContext"))) && (!(params[i].isAssignableFrom(list2.getReturnType())))){
	    		    		  if (((params[i].isAssignableFrom(String.class))) || ((params[i].isPrimitive())) || ((params[i].isAssignableFrom(Boolean.class))) || ((params[i].isAssignableFrom(Date.class)))){
	    		    		  
	    		    		 dc.field(0, params[i], "primitiveParam"+i, null);
	    		    		 
	    		    	  }
	    		    		  if ((!(params[i].isAssignableFrom(String.class))) && (!(params[i].isPrimitive())) && (!(params[i].isAssignableFrom(Boolean.class))) && (!(params[i].isAssignableFrom(Date.class))) && !(params[i].getSimpleName().contains("ActionContext")) ){
	    		    			  
	    		    			
	    		    				  otherEntities.add(params[i]);
	    		    				  paramsList[i]=","+toLowerCaseFirstChar(params[i].getSimpleName());
	 

	    		        		  }
	    		    	  }
	    		    	  
	    		      }

	            //JFieldVar listElementTypeName = 
	            		//dc.field(0, listElementType, toLowerCaseFirstChar(listElementType.getSimpleName()+"Entity"), null);
	            //JFieldVar selectedClass =
	            		dc.field(JMod.PRIVATE, TS, toLowerCaseFirstChar(TS.getSimpleName()), null);
	            //JFieldVar BEANID = 
	            		dc.field(JMod.PRIVATE | JMod.STATIC | JMod.FINAL, "".getClass(), "BEANID", JExpr.direct("\"" + className + "AcceptanceTest" + "\""));
	            String src = "initContext();\n\t\t"+toLowerCaseFirstChar(toLowerCaseFirstChar(TS.getSimpleName())) + " = (" + TS.getSimpleName() + ") getBeanFactory().getBean(\""
	                     +TS.getSimpleName() +"\"); \n\t\t";
	            String attBean = null;
	            String src1="";
	            String source1="";
	            
	          
	            JMethod getRun = dc.method(JMod.PUBLIC,  List.class, "query");
 	            String core="List<"+listElementType.getSimpleName()+"> result;\n";
				getRun.body().decl(0, cm.ref(ArrayList.class).narrow(Object.class), toLowerCaseFirstChar(listElementType.getSimpleName())+"List", JExpr._new(cm.ref(ArrayList.class).narrow(Object.class)));
		
 	          Class entityRefType1 = null;
 	           for(int p=0;p<otherEntities.size();p++ ){
    			
 	        	    entityRefType1 = otherEntities.get(p);
    	            GetFieldsFromInterface extractFieldsFromInterface3=new GetFieldsFromInterface();
    	            extractFieldsFromInterface3.getFields( entityRefType1);
    	            ArrayList<String> fieldsName3=extractFieldsFromInterface3.getFieldsName();
    	        	ArrayList<Class> fieldsType3=extractFieldsFromInterface3.getFieldsType();
    	            attBean = toLowerCaseFirstChar(entityRefType1.getSimpleName());
    	            dc.field(0, entityRefType1, toLowerCaseFirstChar(attBean), null);
    	            JClass entityFactory=null;
    	 			
    	            
    	          entityFactory = cm.ref(loader.loadClass(factoryResolver.getFactory(otherEntities.get(p))));
    	        
    	           dc.field(0, entityFactory, toLowerCaseFirstChar(attBean) + "Factory", null);
    	           
    	            src =src+ attBean + "Factory =(" + entityRefType1.getSimpleName() + "Factory) getBeanFactory().getBean (\""
    	                     + entityRefType1.getSimpleName() + "Factory\");\n\t\t";
    	        	 for (int i=0;i<fieldsName3.size();i++)
    	            {
    	               // generation des attributs
    	               //JFieldVar temp = 
    	            	dc.field(JMod.PRIVATE, String.class, fieldsName3.get(i), null);

    	               // generation des setters
    	               JMethod setters = dc.method(JMod.PUBLIC, Void.TYPE, "set" + toUpperCaseFirstChar(fieldsName3.get(i)));
    	               JMethod getters = dc.method(JMod.PUBLIC, String.class, "get" + toUpperCaseFirstChar(fieldsName3.get(i)));
    	               setters.param(String.class, fieldsName3.get(i));
    	               setters.body().directStatement("this." + fieldsName3.get(i) + " = " + fieldsName3.get(i) + " ;");
    	               getters.body().directStatement("return "+fieldsName3.get(i)+";");
    	            }
    	            
    	            // la methode getRun
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	          
    	           source1 =source1+ attBean + " = " + toLowerCaseFirstChar(entityRefType1.getSimpleName()) + "Factory.buildBean();\n";
    	           
    	           for (int i=0;i<fieldsName3.size();i++)
	               {
    	        	   
    	        	   String temp1="";  
            		   //
            		   if (!(fieldsType3.get(i).isAssignableFrom(String.class))){
                       temp1="to"+toUpperCaseFirstChar(fieldsType3.get(i).getSimpleName());
                	   
                	   
                	   if (fieldsType3.get(i).isAssignableFrom(Double.class)) temp1="toDouble";
                	   if (fieldsType3.get(i).isAssignableFrom(Long.class)) temp1="toLong";
                	   if (fieldsType3.get(i).isAssignableFrom(Integer.class)) temp1="toInteger";
                	   if (fieldsType3.get(i).isAssignableFrom(Date.class)) temp1="parseDate";
                	   if (fieldsType3.get(i).isAssignableFrom(Boolean.class)) temp1="Boolean.valueOf";
                	   if (fieldsType3.get(i).isAssignableFrom(boolean.class)) temp1="Boolean.valueOf";
            		   
            		   }

                       src1 = src1+attBean + ".set" +  toUpperCaseFirstChar(fieldsName3.get(i).substring(0, fieldsName3.get(i).lastIndexOf("Of"))) + "(" +temp1+"("+fieldsName3.get(i) + "));\n";
 
	                 
	               }
   
 	           }
    	           
    	           
    	           
	            
	            dc.constructor(JMod.PUBLIC).body().directStatement(src);
	    		  getRun.body().directStatement(core);
		           getRun.body().directStatement(source1);
		           getRun.body().directStatement(src1);
		           String listOfParams="";
		            for (int c=0;c<paramsList.length;c++){
		            	listOfParams=listOfParams+paramsList[c];	
		            }

		           getRun.body().directStatement(
	                      "result = " + toLowerCaseFirstChar(TS.getSimpleName()) + "." + list2.getName() + "("
	                                +listOfParams + ");\n");  
		           
    	           getRun.body().directStatement(
                           "if (!(result.isEmpty())){ \n for ("+listElementType.getSimpleName()+" "+ toLowerCaseFirstChar(listElementType.getSimpleName())+":result){\n if ("+ toLowerCaseFirstChar(listElementType.getSimpleName())+" !=null) { ");

   	            // extraire les attributs de l'entité
   	           // Field[] entityFields = listElementType.getDeclaredFields();
   	            
   	         GetOriginalFieldsFromInterface extractFieldsFromInterface4=new GetOriginalFieldsFromInterface();
	            extractFieldsFromInterface4.getFields( listElementType);
	            ArrayList<String> fieldsName4=extractFieldsFromInterface4.getFieldsName();
	        	
   	            
   	            String list="";
   	            String cc;
   	            int i=0;
   	         for (int k=0;i<fieldsName4.size();k++)
   	            {
   	        	 
   	
   	            	getRun.body().directStatement(" List<Object> list"+toUpperCaseFirstChar(fieldsName4.get(k))+" = list("+"\""+toUpperCaseFirstChar(fieldsName4.get(k))+"\""+","+toLowerCaseFirstChar(listElementType.getSimpleName())+".get"+toUpperCaseFirstChar(fieldsName4.get(k))+"());\n");
   	            	i++;
   	            	if (i==fieldsName4.size()) cc="";
   	            	else cc=",";
   	            	list=list+"list"+toUpperCaseFirstChar(fieldsName4.get(k))+cc;
   	            }
   	         getRun.body().directStatement(toLowerCaseFirstChar(listElementType.getSimpleName())+"List.add(list("+list+"));");
   	         getRun.body().directStatement(
                     "\n}\n}\n}\n  return "+ toLowerCaseFirstChar(listElementType.getSimpleName())+"List  ; \n ");

   	           
    	  }

      }
    	  
    	    file.mkdirs();
            cm.build(file);   
            
 
      
      
   }
      }
   

   
