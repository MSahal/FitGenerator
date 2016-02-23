package com.linedata.ekip.fitnesscreatorplugin.std.classegenerator.model.servicesfonctionnels.actions;



import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.CoreException;

import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.importsresolver.EntityFactoryResolver;
import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.reflection.GetFieldsFromInterface;
import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.reflection.GetInputsFromMethod;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JStatement;



public class DeleteFitnesClassForFuctionnalService  extends FitnesClassForFunctionnalService
{
   public String toUpperCaseFirstChar(String s)
   {
      String a = (String) s.subSequence(0, 1);

      return a.toUpperCase() + s.substring(1);
   }

   public String toLowerCaseFirstChar(String s)
   {
      String a = (String) s.subSequence(0, 1);
      return a.toLowerCase() + s.substring(1);
   }

   @SuppressWarnings("rawtypes")
public
   Class<?>  findApproximatelyByClassName(ArrayList<Class<?> > classes, String className)
   {

      Class temp = null;
      for (int i = 0; i < classes.size(); i++)
      {
         if (classes.get(i).getName().contains(className))
         {
            temp = classes.get(i);
         }
      }

      return temp;

   }

   @SuppressWarnings("rawtypes")
public
   Class<?> findByClassName(ArrayList<Class<?> > classes, String className)
   {

      Class temp = null;
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
		

	      JCodeModel cm = new JCodeModel();
	      JPackage blockServicePackage = cm._package(fitpack);
	      GetFieldsFromInterface extractFieldsFromInterface=new GetFieldsFromInterface();
	      EntityFactoryResolver factoryResolver=new EntityFactoryResolver();
	      //EntityResolver entityResolver=new EntityResolver();
	      // extraire les parametres de chaque methode
	      GetInputsFromMethod ext1 = new GetInputsFromMethod();
	      Class[] params = ext1.runExtractInputsFromMethod(list2);
	      String[] paramsList=new String[params.length];
	      for (int c=0;c<paramsList.length;c++){
	      	paramsList[c]="0";
	      }
	      ArrayList<Class> otherEntities=new ArrayList<Class>();
	      ArrayList<Class> primitiveParams=new ArrayList<Class>();
	      ArrayList<String> primitiveParamsNames=new ArrayList<String>();
	      String className = toUpperCaseFirstChar(list2.getName())+list2.getReturnType().getSimpleName();
	      
	      //import de la classe FitnesseAcceptanceTest
	      JClass FitAcceptTest = cm.ref(loader.loadClass( "com.linedata.ekip.std.acceptance.test.common.FitnesseAcceptanceTest"));
	      JDefinedClass dc = blockServicePackage._class(className + "AcceptanceTest")._extends(FitAcceptTest);
	      
	      
	     // recuperation de la liste des parametres de la methode dans une chaine pour l'utiliser aprés lors de l'appel de service 
	     
	      for (int i=0;i<params.length;i++){
	    	  
	    	  if (params[i].getSimpleName().contains("ActionContext")) paramsList[i]="getActionContext()";
	    	  if (((params[i].isAssignableFrom(String.class))) || ((params[i].isPrimitive())) || ((params[i].isAssignableFrom(Boolean.class))) || ((params[i].isAssignableFrom(Date.class)))) paramsList[i]=",primitiveParam"+i;
	    	  if (params[i].isAssignableFrom(list2.getReturnType())) paramsList[i]=","+toLowerCaseFirstChar(list2.getReturnType().getSimpleName());
	    	  if (Collection.class.isAssignableFrom(params[i])) paramsList[i]="list";
	    	  
	    	 
	    	  if ((!(params[i].getSimpleName().contains("ActionContext"))) && (!(params[i].isAssignableFrom(list2.getReturnType())))){
	    		  if (((params[i].isAssignableFrom(String.class))) || ((params[i].isPrimitive())) || ((params[i].isAssignableFrom(Boolean.class))) || ((params[i].isAssignableFrom(Date.class)))){
	    		  
	    		  dc.field(0, params[i], "primitiveParam"+i, null);
	    		 
	    	  }
	    		  if ((!(params[i].isAssignableFrom(String.class))) && (!(params[i].isPrimitive())) && (!(params[i].isAssignableFrom(Boolean.class))) && (!(params[i].isAssignableFrom(Date.class))) && !(params[i].getSimpleName().contains("ActionContext")) && (!(params[i].isAssignableFrom(list2.getReturnType())))){
	    			  
	    			  if (!Collection.class.isAssignableFrom(params[i])){
	    				  otherEntities.add(params[i]);
	    				  paramsList[i]=","+toLowerCaseFirstChar(params[i].getSimpleName());
	            		 
	    			  }

	        		  }
	    	  }
	    	  
	      }

	      ////
	      
	            // traitement de l'entité à créer ; extraction de ses parametres avec leurs types
	            extractFieldsFromInterface.getFieldsAndReferenceFields(list2.getReturnType());
	            ArrayList<String> fieldsName=extractFieldsFromInterface.getFieldsName();
	        	ArrayList<Class> fieldsType=extractFieldsFromInterface.getFieldsType();
	            // construction du nom de la classe
	            String attBean =toLowerCaseFirstChar(list2.getReturnType().getSimpleName());
	            

	            // construction de la classe

	            //JClass entityFactory = cm.ref(findApproximatelyByClassName(workspaceClass, "." + params[j].getSimpleName() + "Factory"));
	           // declaratiob des attributs selectedClass,entityFact,BEANID, entity , is created , errorcode
	            String is = "deleted";
	            String errorcode = "errorCode";
	            
	            /*JFieldVar selectedClass =*/ dc.field(JMod.PRIVATE, TS, toLowerCaseFirstChar(TS.getSimpleName()), null);
	            System.out.println("getting the factory of"+toUpperCaseFirstChar(attBean));
	            System.out.println("the factory is "+factoryResolver.getFactory(list2.getReturnType()));
	            JClass entityFactoryMain = cm.ref(loader.loadClass(factoryResolver.getFactory(list2.getReturnType())));
	            //JClass entityMain = cm.ref(loader.loadClass(entityResolver.getEntity(toUpperCaseFirstChar(attBean))));
	            JClass entityMain = cm.ref(loader.loadClass(list2.getReturnType().getName()));
	            //JFieldVar entityFact = 
	            dc.field(JMod.PRIVATE, entityFactoryMain, attBean + "Factory", null);
	            JFieldVar isDone = dc.field(JMod.PRIVATE, is.getClass(), is, null);
	            JFieldVar errorCod = dc.field(JMod.PRIVATE, errorcode.getClass(), errorcode, null);
	            
	            /*JFieldVar entity =*/ dc.field(0, entityMain, attBean, null);
	            /*JFieldVar BEANID =*/ dc.field(JMod.PRIVATE | JMod.STATIC | JMod.FINAL, "".getClass(), "BEANID", JExpr.direct("\"" + className + "AcceptanceTest" + "\""));          
	            for (int i=0;i<primitiveParams.size();i++){
	            	dc.field(0, primitiveParams.get(i), primitiveParamsNames.get(i), null);
	            }
	  

	            // generation de la methode init
	            JMethod init = dc.method(JMod.PRIVATE, Void.TYPE, "init");

	            // generation de la methode run
	            JMethod run = dc.method(JMod.PRIVATE, Void.TYPE, "run" + toUpperCaseFirstChar(list2.getName()));

	            File file = new File(path);

	          
	            //traitement des parametres de type entiy: (!=primitive type) && (!=List)
	            for (int k=0;k<otherEntities.size();k++){
	            	if (!(Collection.class.isAssignableFrom(otherEntities.get(k)))){
	            		System.out.println("le nom de l'entité : "+otherEntities.get(k).getName());
	            		
	              //JClass otherEntity = cm.ref(loader.loadClass(entityResolver.getEntity(otherEntities.get(k).getSimpleName())));
	            		JClass otherEntity = cm.ref(loader.loadClass(otherEntities.get(k).getName()));
	            	dc.field(0, otherEntity, toLowerCaseFirstChar(otherEntities.get(k).getSimpleName()), null);
	            	System.out.println("the factory is : "+factoryResolver.getFactory(otherEntities.get(k)));
	            	JClass entityFactory = cm.ref(loader.loadClass(factoryResolver.getFactory(otherEntities.get(k))));
	            	
	            	dc.field(0, entityFactory, toLowerCaseFirstChar(otherEntities.get(k).getSimpleName())+"Factory", null);
	            	
	            	 init.body().directStatement("/*Building the "+toLowerCaseFirstChar(otherEntities.get(k).getSimpleName())+" entity */\n\t\t"+toLowerCaseFirstChar(otherEntities.get(k).getSimpleName()) + " = " + toLowerCaseFirstChar(otherEntities.get(k).getSimpleName()) + "Factory.buildBean();\n");
	            	 	GetFieldsFromInterface extractFieldsFromInterface1=new GetFieldsFromInterface();
	               	 extractFieldsFromInterface1.getFieldsAndReferenceFields(otherEntities.get(k));
	                    ArrayList<String> fieldsName1=extractFieldsFromInterface1.getFieldsName();
	                	ArrayList<Class> fieldsType1=extractFieldsFromInterface1.getFieldsType();
	                	for (int j=0;j<fieldsName1.size();j++){

	                	
	                          dc.field(JMod.PRIVATE, String.class, fieldsName1.get(j), null);
	                        // generation des attributs

	                        // generation des setters
	                        JMethod setters = dc.method(JMod.PUBLIC, Void.TYPE, "set" + toUpperCaseFirstChar(fieldsName1.get(j)));
	                        JMethod getters = dc.method(JMod.PUBLIC, String.class, "get" + toUpperCaseFirstChar(fieldsName1.get(j)));

	                        setters.param(String.class, fieldsName1.get(j));
	                        setters.body().directStatement("this." + fieldsName1.get(j) + " = " + fieldsName1.get(j)+ " ;");
	                        getters.body().directStatement("return "+fieldsName1.get(j)+";");
	                        
	                        if (!(fieldsType1.get(j).isAssignableFrom(String.class))){
	                     	   String temp="to"+toUpperCaseFirstChar(fieldsType1.get(j).getSimpleName());
	                     	   
	                     	   
	                     	   if (fieldsType1.get(j).isAssignableFrom(Double.class)) temp="toDouble";
	                     	   if (fieldsType1.get(j).isAssignableFrom(Long.class)) temp="toLong";
	                     	   if (fieldsType1.get(j).isAssignableFrom(Integer.class)) temp="toInteger";
	                     	   if (fieldsType1.get(j).isAssignableFrom(Date.class)) temp="parseDate";
	                     	  if (fieldsType1.get(j).isAssignableFrom(Boolean.class)) temp="Boolean.valueOf";
	                     	 if (fieldsType1.get(j).isAssignableFrom(boolean.class)) temp="Boolean.valueOf";
	                     	   
	                        init.body().directStatement(
	                                 "if (checkItem(" + fieldsName1.get(j) + "))\n { \n \t " + toLowerCaseFirstChar(otherEntities.get(k).getSimpleName()) + ".set" +  toUpperCaseFirstChar(fieldsName1.get(j)).subSequence(0, fieldsName1.get(j).lastIndexOf("Of")) + "(" +temp+ "("+fieldsName1.get(j) + "));\n }");
	                        }
	                        
	                        else{
	                     	   
	                            init.body().directStatement(
	                                     "if (checkItem(" + fieldsName1.get(j) + "))\n { \n \t " + toLowerCaseFirstChar(otherEntities.get(k).getSimpleName()) + ".set" + toUpperCaseFirstChar(fieldsName1.get(j)).subSequence(0, fieldsName1.get(j).lastIndexOf("Of")) + "(" +fieldsName1.get(j) + ");\n }");
	                            }
	                        // init.body()._if();

	                	}
	            }
	            }
	            
	            
	            
	            
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
	           
	           
	           
	           String factories="";
	           
	         // traitement des parametres de type List<entity>
	           if (test==false){
	            
	       
	 		Class parameterArgClass=null; 
			// recuperation de type d'entité dans la liste
			Type[] genericParameterTypes = list2.getGenericParameterTypes();
			
			for(Type genericParameterType : genericParameterTypes){
				
			    if(genericParameterType instanceof ParameterizedType){
			    	
			        ParameterizedType aType = (ParameterizedType) genericParameterType;
			        Type[] parameterArgTypes = aType.getActualTypeArguments();
			        for(Type parameterArgType : parameterArgTypes){
			        	
			            parameterArgClass = (Class) parameterArgType;
			            int c=0;
			            while (c<paramsList.length && !paramsList[c].equals("list")){ 
			             c++;
			            }
			            paramsList[c]=","+toLowerCaseFirstChar(parameterArgClass.getSimpleName()+"s");
			            System.out.println("le nom de l'entité : "+parameterArgClass.getName());
			            
			           // JClass entityOfList = cm.ref(loader.loadClass(entityResolver.getEntity(parameterArgClass.getSimpleName())));
			            JClass entityOfList = cm.ref(loader.loadClass(parameterArgClass.getName()));
			            
			            System.out.println("getting the class factory"+parameterArgClass.getSimpleName());
			            System.out.println("the factory is"+factoryResolver.getFactory(parameterArgClass));
			            JClass entityFactoryForList = cm.ref(loader.loadClass(factoryResolver.getFactory(parameterArgClass)));        
			            dc.field(JMod.PRIVATE, entityFactoryForList,toLowerCaseFirstChar(parameterArgClass.getSimpleName())+"Factory", null);
			// ajouter les entity factory dans le constructeur
			factories=factories+toLowerCaseFirstChar(parameterArgClass.getSimpleName()) + "Factory =(" + parameterArgClass.getSimpleName()+ "Factory) getBeanFactory().getBean (\""
	                + parameterArgClass.getSimpleName() + "Factory\");\n\t\t";
			
			GetFieldsFromInterface extractFieldsFromInterface2=new GetFieldsFromInterface();
	      	 extractFieldsFromInterface2.getFieldsAndReferenceFields(parameterArgClass);
	           ArrayList<String> fieldsName2=extractFieldsFromInterface2.getFieldsName();
	       	ArrayList<Class> fieldsType2=extractFieldsFromInterface2.getFieldsType();
	       	init.body().directStatement("//getting the list of "+parameterArgClass.getSimpleName());
	       	JFieldVar x =dc.field(0, List.class, toLowerCaseFirstChar(parameterArgClass.getSimpleName())+"s", null);
	       	init.body().add((JStatement) x.assign(JExpr._new(cm.ref(ArrayList.class).narrow(entityOfList))));
	       
	       	
	        init.body().directStatement(" for (Entry<String, String> obj : "+toLowerCaseFirstChar(fieldsName2.get(0))+".entrySet()){\n");
	        init.body().directStatement(parameterArgClass.getSimpleName()+" "+toLowerCaseFirstChar(parameterArgClass.getSimpleName()+" = "+toLowerCaseFirstChar(parameterArgClass.getSimpleName()+"Factory.buildBean();\n")));
	        
	        String tempObji="";
	        if (fieldsType2.get(0).isAssignableFrom(Double.class)) tempObji="toDouble";
	    	   if (fieldsType2.get(0).isAssignableFrom(Long.class)) tempObji="toLong";
	    	   if (fieldsType2.get(0).isAssignableFrom(Integer.class)) tempObji="toInteger";
	    	   if (fieldsType2.get(0).isAssignableFrom(Date.class)) tempObji="parseDate";
	    	   if (fieldsType2.get(0).isAssignableFrom(Boolean.class)) tempObji="Boolean.valueOf";
	    	   if (fieldsType2.get(0).isAssignableFrom(boolean.class)) tempObji="Boolean.valueOf";
	    	   
	    	   
	        init.body().directStatement(" if (!obj.getValue().equals(\"null\"))\n{\n\t"+toLowerCaseFirstChar(parameterArgClass.getSimpleName())+".set"+toUpperCaseFirstChar(fieldsName2.get(0)).subSequence(0, fieldsName2.get(0).lastIndexOf("Of"))+"("+tempObji+"(obj.getValue()));"+"\n}");
	        for (int i=0;i<fieldsName2.size();i++)
	        {
	        	dc.field(JMod.PRIVATE, Map.class, "<String , String> "+toLowerCaseFirstChar(fieldsName2.get(i)));
	            JMethod setters = dc.method(JMod.PUBLIC, Void.TYPE, "set" + toUpperCaseFirstChar(fieldsName2.get(i)));
	            JMethod getters = dc.method(JMod.PUBLIC, Map.class, "<String, String> get" + toUpperCaseFirstChar(fieldsName2.get(i)));
	            
	            setters.param(Map.class,"<String, String> "+ fieldsName2.get(i));
	            setters.body().directStatement("this." + fieldsName2.get(i) + " = " + fieldsName2.get(i)+ " ;");
	            getters.body().directStatement("return "+fieldsName2.get(i)+";");
	        	
	        }
	        
	        for (int i=1;i<fieldsName2.size();i++)
	        {

	        	  if (!(fieldsType2.get(i).isAssignableFrom(String.class))){
	           	   String temp="to"+toUpperCaseFirstChar(fieldsType2.get(i).getSimpleName());
	           	   
	           	   
	           	   if (fieldsType2.get(i).isAssignableFrom(Double.class)) temp="toDouble";
	           	   if (fieldsType2.get(i).isAssignableFrom(Long.class)) temp="toLong";
	           	   if (fieldsType2.get(i).isAssignableFrom(Integer.class)) temp="toInteger";
	           	   if (fieldsType2.get(i).isAssignableFrom(Date.class)) temp="parseDate";
	           	if (fieldsType2.get(i).isAssignableFrom(Boolean.class)) temp="Boolean.valueOf";
	           	if (fieldsType2.get(i).isAssignableFrom(boolean.class)) temp="Boolean.valueOf";
	           	   
	           	init.body().directStatement(" if (!"+toLowerCaseFirstChar(fieldsName2.get(i))+".get(obj.getKey()).equals(\"null\"))\n{\n\t"+toLowerCaseFirstChar(parameterArgClass.getSimpleName())+".set"+toUpperCaseFirstChar(fieldsName2.get(i)).subSequence(0, fieldsName2.get(i).lastIndexOf("Of"))+"("+temp+ "("+toLowerCaseFirstChar(fieldsName2.get(i))+".get(obj.getKey())));"+"\n}");
	             
	              }
	              
	              else{
	           	   
	            	  init.body().directStatement(" if (!"+toLowerCaseFirstChar(fieldsName2.get(i))+".get(obj.getKey()).equals(\"null\"))\n{\n\t"+toLowerCaseFirstChar(parameterArgClass.getSimpleName())+".set"+toUpperCaseFirstChar(fieldsName2.get(i)).subSequence(0, fieldsName2.get(i).lastIndexOf("Of"))+"("+ "("+toLowerCaseFirstChar(fieldsName2.get(i))+".get(obj.getKey())));"+"\n}");
	                  }
		
	        }
	       
	      
	        init.body().directStatement(toLowerCaseFirstChar(parameterArgClass.getSimpleName()+"s")+".add("+toLowerCaseFirstChar(parameterArgClass.getSimpleName())+");\n}");
	       
	      
			        }
			    }
			    
			}
	   }
			
			
			if (test==true){
				int r=0;
				
		 		Class parameterArgClass=null; 
				// recuperation de type d'entité dans la liste
				Type[] genericParameterTypes = list2.getGenericParameterTypes();
				
				for(Type genericParameterType : genericParameterTypes){
					
				    if(genericParameterType instanceof ParameterizedType){
				    	r++;
				        ParameterizedType aType = (ParameterizedType) genericParameterType;
				        Type[] parameterArgTypes = aType.getActualTypeArguments();
				        for(Type parameterArgType : parameterArgTypes){
				        	
				            parameterArgClass = (Class) parameterArgType;
				            int c=0;
				            while (c<paramsList.length && !paramsList[c].equals("list")){ 
				             c++;
				            }
				            paramsList[c]=","+toLowerCaseFirstChar(parameterArgClass.getSimpleName()+"s"+r);
				            System.out.println("le nom de l'entité : "+parameterArgClass.getName());
				            
				           // JClass entityOfList = cm.ref(loader.loadClass(entityResolver.getEntity(parameterArgClass.getSimpleName())));
				            JClass entityOfList = cm.ref(loader.loadClass(parameterArgClass.getName()));
				            
				            System.out.println("getting the class factory"+parameterArgClass.getSimpleName());
				            System.out.println("the factory is"+factoryResolver.getFactory(parameterArgClass));
				            JClass entityFactoryForList = cm.ref(loader.loadClass(factoryResolver.getFactory(parameterArgClass)));        
				            dc.field(JMod.PRIVATE, entityFactoryForList,toLowerCaseFirstChar(parameterArgClass.getSimpleName())+"Factory"+r, null);
				// ajouter les entity factory dans le constructeur
				factories=factories+toLowerCaseFirstChar(parameterArgClass.getSimpleName()) + "Factory"+r+" =(" + parameterArgClass.getSimpleName()+ "Factory) getBeanFactory().getBean (\""
		                + parameterArgClass.getSimpleName() + "Factory\");\n\t\t";
				
				GetFieldsFromInterface extractFieldsFromInterface2=new GetFieldsFromInterface();
		      	 extractFieldsFromInterface2.getFieldsAndReferenceFields(parameterArgClass);
		           ArrayList<String> fieldsName2=extractFieldsFromInterface2.getFieldsName();
		       	ArrayList<Class> fieldsType2=extractFieldsFromInterface2.getFieldsType();
		       	init.body().directStatement("//getting the list of "+parameterArgClass.getSimpleName());
		       	JFieldVar x =dc.field(0, List.class, toLowerCaseFirstChar(parameterArgClass.getSimpleName())+"s"+r, null);
		       	init.body().add((JStatement) x.assign(JExpr._new(cm.ref(ArrayList.class).narrow(entityOfList))));
		       
		       	
		        init.body().directStatement(" for (Entry<String, String> obj : "+toLowerCaseFirstChar(fieldsName2.get(0))+r+".entrySet()){\n");
		        init.body().directStatement(parameterArgClass.getSimpleName()+" "+toLowerCaseFirstChar(parameterArgClass.getSimpleName()+r+" = "+toLowerCaseFirstChar(parameterArgClass.getSimpleName()+"Factory"+r+".buildBean();\n")));
		        
		        String tempObji="";
		        if (fieldsType2.get(0).isAssignableFrom(Double.class)) tempObji="toDouble";
		    	   if (fieldsType2.get(0).isAssignableFrom(Long.class)) tempObji="toLong";
		    	   if (fieldsType2.get(0).isAssignableFrom(Integer.class)) tempObji="toInteger";
		    	   if (fieldsType2.get(0).isAssignableFrom(Date.class)) tempObji="parseDate";
		    	   if (fieldsType2.get(0).isAssignableFrom(Boolean.class)) tempObji="Boolean.valueOf";
		    	   if (fieldsType2.get(0).isAssignableFrom(boolean.class)) tempObji="Boolean.valueOf";
		    	   
		        init.body().directStatement(" if (!obj.getValue().equals(\"null\"))\n{\n\t"+toLowerCaseFirstChar(parameterArgClass.getSimpleName())+r+".set"+toUpperCaseFirstChar(fieldsName2.get(0)).subSequence(0, fieldsName2.get(0).lastIndexOf("Of"))+"("+tempObji+"(obj.getValue()));"+"\n}");
		        for (int i=0;i<fieldsName2.size();i++)
		        {
		        	dc.field(JMod.PRIVATE, Map.class, "<String , String> "+toLowerCaseFirstChar(fieldsName2.get(i))+r);
		            JMethod setters = dc.method(JMod.PUBLIC, Void.TYPE, "set" + toUpperCaseFirstChar(fieldsName2.get(i))+r);
		            JMethod getters = dc.method(JMod.PUBLIC, Map.class, "<String, String> get" + toUpperCaseFirstChar(fieldsName2.get(i))+r);
		            
		            setters.param(Map.class,"<String, String> "+ fieldsName2.get(i)+r);
		            setters.body().directStatement("this." + fieldsName2.get(i)+r + " = " + fieldsName2.get(i)+r+ " ;");
		            getters.body().directStatement("return "+fieldsName2.get(i)+r+";");
		        	
		        }
		        
		        for (int i=1;i<fieldsName2.size();i++)
		        {

		        	  if (!(fieldsType2.get(i).isAssignableFrom(String.class))){
		           	   String temp="to"+toUpperCaseFirstChar(fieldsType2.get(i).getSimpleName());
		           	   
		           	   
		           	   if (fieldsType2.get(i).isAssignableFrom(Double.class)) temp="toDouble";
		           	   if (fieldsType2.get(i).isAssignableFrom(Long.class)) temp="toLong";
		           	   if (fieldsType2.get(i).isAssignableFrom(Integer.class)) temp="toInteger";
		           	   if (fieldsType2.get(i).isAssignableFrom(Date.class)) temp="parseDate";
		           	if (fieldsType2.get(i).isAssignableFrom(Boolean.class)) temp="Boolean.valueOf";
		           	if (fieldsType2.get(i).isAssignableFrom(boolean.class)) temp="Boolean.valueOf";
		           	   
		           	init.body().directStatement(" if (!"+toLowerCaseFirstChar(fieldsName2.get(i)+r)+".get(obj.getKey()).equals(\"null\"))\n{\n\t"+toLowerCaseFirstChar(parameterArgClass.getSimpleName()+r)+".set"+toUpperCaseFirstChar(fieldsName2.get(i)).subSequence(0, fieldsName2.get(i).lastIndexOf("Of"))+"("+temp+ "("+toLowerCaseFirstChar(fieldsName2.get(i))+r+".get(obj.getKey())));"+"\n}");
		             
		              }
		              
		              else{
		           	   
		            	  init.body().directStatement(" if (!"+toLowerCaseFirstChar(fieldsName2.get(i)+r)+".get(obj.getKey()).equals(\"null\"))\n{\n\t"+toLowerCaseFirstChar(parameterArgClass.getSimpleName()+r)+".set"+toUpperCaseFirstChar(fieldsName2.get(i)).subSequence(0, fieldsName2.get(i).lastIndexOf("Of"))+"("+ "("+toLowerCaseFirstChar(fieldsName2.get(i))+r+".get(obj.getKey())));"+"\n}");
		                  }
			
		        }
		       
		      
		        init.body().directStatement(toLowerCaseFirstChar(parameterArgClass.getSimpleName()+"s"+r)+".add("+toLowerCaseFirstChar(parameterArgClass.getSimpleName())+r+");\n}");
		       
		      
				        }
				    }
				    
				}
				
			}
			
			
			
			
			//methode init()
			   
			 init.body().directStatement("/*Building the "+attBean+" entity */\n\t\t"+attBean + " = " + toLowerCaseFirstChar(list2.getReturnType().getSimpleName()) + "Factory.buildBean();\n");  
	      

	            for (int s=0;s<otherEntities.size();s++){
	            	if (!Collection.class.isAssignableFrom(otherEntities.get(s)))
	                {	
	            		factories=factories+toLowerCaseFirstChar(otherEntities.get(s).getSimpleName()) + "Factory =(" + otherEntities.get(s).getSimpleName()+ "Factory) getBeanFactory().getBean (\""
	                        + otherEntities.get(s).getSimpleName() + "Factory\");\n\t\t";
	            	}
	            	
	            }
	            
	            
	         // constructeur de la classe
	            String source = "initContext();\n\t\t" + toLowerCaseFirstChar(toLowerCaseFirstChar(TS.getSimpleName())) + " = (" + TS.getSimpleName() + ") getBeanFactory().getBean(\""
	                     +TS.getSimpleName() +"\"); \n\t\t" + attBean + "Factory =(" + list2.getReturnType().getSimpleName() + "Factory) getBeanFactory().getBean (\""
	                     + list2.getReturnType().getSimpleName() + "Factory\");\n\t\t"+factories;
	            dc.constructor(JMod.PUBLIC).body().directStatement(source);
	            

	            // implementation de la methode run
	            String listOfParams="";
	            for (int c=0;c<paramsList.length;c++){
	            	listOfParams=listOfParams+paramsList[c];	
	            }
	            run.body().directStatement(
	                     errorcode + " = \"null\" ;\n\t" + is + " =\" yes\";\n\n\t" +  "init" + " ();\n\n\t\t " 
	                              + "\n \t\t\t try {\n\t\t\t" +list2.getReturnType().getSimpleName()+" "+ attBean+"Result" + " = " + toLowerCaseFirstChar(TS.getSimpleName()) + "." + list2.getName()
	                              + "(" +listOfParams+ ");\n\t\t }\n\t\t catch (Exception e) {\n\t\t" + errorcode
	                              + " = getMessage(e);\n\t\t" + is + "=\"no\";\n\t\t \n\t\t \n\t\t}");
	            for (int i=0;i<fieldsName.size();i++)
	            {
	               // implementation de la methode init
	            	 // generation des attributs
	               dc.field(JMod.PRIVATE, String.class, fieldsName.get(i), null);

	               // generation des getters & setters
	               JMethod setters = dc.method(JMod.PUBLIC, Void.TYPE, "set" + toUpperCaseFirstChar(fieldsName.get(i)));
	               JMethod getters = dc.method(JMod.PUBLIC, String.class, "get" + toUpperCaseFirstChar(fieldsName.get(i)));

	               setters.param(String.class, fieldsName.get(i));
	               setters.body().directStatement("this." + fieldsName.get(i) + " = " + fieldsName.get(i)+ " ;");
	               getters.body().directStatement("return "+fieldsName.get(i)+";");
	               
	               // parsing des types String aux types originaux
	               if (!(fieldsType.get(i).isAssignableFrom(String.class))){
	            	   String temp="to"+toUpperCaseFirstChar(fieldsType.get(i).getSimpleName());
	            	   
	            	   
	            	   if (fieldsType.get(i).isAssignableFrom(Double.class)) temp="toDouble";
	            	   if (fieldsType.get(i).isAssignableFrom(Long.class)) temp="toLong";
	            	   if (fieldsType.get(i).isAssignableFrom(Integer.class)) temp="toInteger";
	            	   if (fieldsType.get(i).isAssignableFrom(Date.class)) temp="parseDate";
	            	   if (fieldsType.get(i).isAssignableFrom(Boolean.class)) temp="Boolean.valueOf";
	            	   if (fieldsType.get(i).isAssignableFrom(boolean.class)) temp="Boolean.valueOf";
	            	   
	               init.body().directStatement(
	                        "if (checkItem(" + fieldsName.get(i) + "))\n { \n \t " + attBean + ".set" + toUpperCaseFirstChar(fieldsName.get(i)).subSequence(0, fieldsName.get(i).lastIndexOf("Of")) + "(" +temp+ "("+fieldsName.get(i) + "));\n }");
	               }
	               
	               else{
	            	   
	                   init.body().directStatement(
	                            "if (checkItem(" + fieldsName.get(i) + "))\n { \n \t " + attBean + ".set" + toUpperCaseFirstChar(fieldsName.get(i)).subSequence(0, fieldsName.get(i).lastIndexOf("Of")) + "(" +fieldsName.get(i) + ");\n }");
	                   }

	            }
	            
	            // implementation des methodes isCreated et getErrorCode
	            JMethod getIs = dc.method(JMod.PUBLIC, is.getClass(), "is" + toUpperCaseFirstChar(is));
	            getIs.body().directStatement(run.name()+"() ;\n");
	            getIs.body()._return(isDone);
	            JMethod geterror = dc.method(JMod.PUBLIC, is.getClass(), "getErrorCode");
	            geterror.body()._return(errorCod);
	            file.mkdirs();
	            cm.build(file);


	   }
}

