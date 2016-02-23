package com.linedata.ekip.fitnesscreatorplugin.std.classegenerator.model.servicesunitaires.actions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

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



public class UpdateFitnesClassForUnitaryService extends FitnesClassUnitairyService
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

   @SuppressWarnings("rawtypes")
   Class<?> findApproximatelyByClassName(ArrayList<Class<?>> classes, String className)
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
   Class<?> findByClassName(ArrayList<Class<?>> classes, String className)
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

		String TSPackage=TS.getPackage().toString().substring(8);
		int indexOfServices=TSPackage.indexOf(".services.");
		String domain=TSPackage.substring(indexOfServices+10, TSPackage.length()-8);
		

	    String fitpack="com.linedata.ekip.std.acceptance.test."+domain;
		 EntityFactoryResolver factoryResolver=new EntityFactoryResolver();
		
		    
	      JCodeModel cm = new JCodeModel();
	      
	      JPackage blockServicePackage = cm._package(fitpack);
      GetFieldsFromInterface extractFieldsFromInterface=new GetFieldsFromInterface();
      
    
   

     

      // extraire les paramétres de chaque methode
      GetInputsFromMethod ext1 = new GetInputsFromMethod();
      Class[] params = ext1.runExtractInputsFromMethod(list2);
      ArrayList<Class> primitiveParams=new ArrayList<Class>();
      ArrayList<String> primitiveParamsNames=new ArrayList<String>();
      for (int i=0;i<params.length;i++){
    	  if ((!(params[i].getSimpleName().contains("ActionContext"))) && (!(params[i].getSimpleName().contains(list2.getReturnType().getSimpleName())))){
    		  primitiveParams.add(params[i]);
    	  }
      }
      
      if (!(primitiveParams.isEmpty())){
      for (int i=0;i<primitiveParams.size();i++){
    	  primitiveParamsNames.add("param"+i);
      }
      }
      String className = null;

            // extraire les attributs de chaque paramètre
          //  Field[] fields = params[j].getDeclaredFields();
            extractFieldsFromInterface.getFields(list2.getReturnType());
            ArrayList<String> fieldsName=extractFieldsFromInterface.getFieldsName();
        	ArrayList<Class> fieldsType=extractFieldsFromInterface.getFieldsType();
            // construction du nom de la classe
            String attBean = list2.getReturnType().getSimpleName().substring(0, 1).toLowerCase() + list2.getReturnType().getSimpleName().substring(1, list2.getReturnType().getSimpleName().length());
            className = list2.getName().substring(0, 1).toUpperCase() + list2.getName().substring(1) + list2.getReturnType().getSimpleName();

            // construction de la classe
            JClass entityFactory=null;
			for (int j=0;j<params.length;j++){
            if (!(primitiveParams.contains(params[j])) &&  !(params[j].getSimpleName().contains("ActionContext"))){
           System.out.println(factoryResolver.getFactory(params[j]));
            entityFactory = cm.ref(loader.loadClass(factoryResolver.getFactory(params[j])));
            }
            }
			
			@SuppressWarnings("unused")
			JFieldVar entityFact=null;
            
            
            JClass FitAcceptTest = cm.ref(loader.loadClass( "com.linedata.ekip.std.acceptance.test.common.FitnesseAcceptanceTest"));
           // JClass entityFactory = cm.ref(findApproximatelyByClassName(workspaceClass, "." + params[j].getSimpleName() + "Factory"));
            JDefinedClass dc = blockServicePackage._class(className + "AcceptanceTest")._extends(FitAcceptTest);
            
         
            // dc.constructor(JMod.PUBLIC).body().invoke(initContext.toString().substring(initContext.toString().lastIndexOf(".")
            // + 1, initContext.toString().length()));

            String source = "initContext();\n\t\t" + toLowerCaseFirstChar(toLowerCaseFirstChar(TS.getSimpleName())) + " = (" + TS.getSimpleName() + ") getBeanFactory().getBean(\""
                    +TS.getSimpleName() +"\"); \n\t\t" + attBean + "Factory =(" + list2.getReturnType().getSimpleName() + "Factory) getBeanFactory().getBean (\""
                    + list2.getReturnType().getSimpleName() + "Factory\");";
            // constructeur de la classe
            dc.constructor(JMod.PUBLIC).body().directStatement(source);

            // les deux attributs standards retourTest & errorCode avec
            // leurs
            // getters
            String is = "updated";
            String errorcode = "errorCode";

            /*JFieldVar selectedClass =*/ dc.field(JMod.PRIVATE, TS, toLowerCaseFirstChar(TS.getSimpleName()), null);
            if (!entityFactory.equals(null)) entityFact = dc.field(JMod.PRIVATE, entityFactory, attBean + "Factory", null);
            JFieldVar isDone = dc.field(JMod.PRIVATE, is.getClass(), is, null);
            JFieldVar errorCod = dc.field(JMod.PRIVATE, errorcode.getClass(), errorcode, null);
           /* JFieldVar entity =*/ dc.field(0, list2.getReturnType(), attBean, null);
            /*JFieldVar BEANID =*/ dc.field(JMod.PRIVATE | JMod.STATIC | JMod.FINAL, "".getClass(), "BEANID", JExpr.direct("\"" + className + "AcceptanceTest" + "\""));

            String listOfPrimitiveParams="";
            if (!(primitiveParamsNames.isEmpty())){
            for (int k=0;k<primitiveParamsNames.size();k++){
            	dc.field(0, primitiveParams.get(k), primitiveParamsNames.get(k), null);
            	listOfPrimitiveParams=listOfPrimitiveParams+","+primitiveParamsNames.get(k);
            }
            }
        
            // generation de la methode init
            JMethod init = dc.method(JMod.PRIVATE, list2.getReturnType(), "init" + list2.getReturnType().getSimpleName());

            // generation de la methode run
            JMethod run = dc.method(JMod.PRIVATE, Void.TYPE, "run" + toUpperCaseFirstChar(list2.getName()) );

            File file = new File(path);

            init.body().directStatement(attBean + " = " + toLowerCaseFirstChar(list2.getReturnType().getSimpleName()) + "Factory.buildBean();\n");
            // implementation de la methode run

            run.body().directStatement(
                     errorcode + " = \"null\" ;\n\t" + is + " =\" yes\";\n\n\t" + attBean + " = " + "init" + list2.getReturnType().getSimpleName() + " ();\n\n\t\t if (" + attBean
                              + " != null ) {\n \t\t\t try {\n\t\t\t" + attBean+listOfPrimitiveParams + " = " + toLowerCaseFirstChar(TS.getSimpleName()) + "." + list2.getName()
                              + "(getActionContext() ," + attBean + ");\n\t\t }\n\t\t catch (Exception e) {\n\t\t" + errorcode
                              + " = e.getMessage();\n\t\t" + is + "=\"no\";\n\t\t }\n\t\t \n\t\t}");
            for (int i=0;i<fieldsName.size();i++)
            {
            	 // implementation de la methode init
                // JFieldVar tempAtt = dc.field(JMod.PRIVATE, fieldsType.get(i), fieldsName.get(i), null);
                 /*JFieldVar tempAtt =*/ dc.field(JMod.PRIVATE, String.class, fieldsName.get(i), null);
                 // generation des attributs

                 // generation des setters
                 JMethod setters = dc.method(JMod.PUBLIC, Void.TYPE, "set" + toUpperCaseFirstChar(fieldsName.get(i)));
                 JMethod getters = dc.method(JMod.PUBLIC, String.class, "get" + toUpperCaseFirstChar(fieldsName.get(i)));
                 setters.param(String.class, fieldsName.get(i));
                 setters.body().directStatement("this." + fieldsName.get(i) + " = " + fieldsName.get(i)+ " ;");
                 getters.body().directStatement("return "+fieldsName.get(i)+";");
                 
                 if (!(fieldsType.get(i).isAssignableFrom(String.class))){
              	   String temp="to"+toUpperCaseFirstChar(fieldsType.get(i).getSimpleName());
              	   
              	   
              	   if (fieldsType.get(i).isAssignableFrom(Double.class)) temp="toDouble";
              	   if (fieldsType.get(i).isAssignableFrom(Long.class)) temp="toLong";
              	   if (fieldsType.get(i).isAssignableFrom(Integer.class)) temp="toInteger";
              	   if (fieldsType.get(i).isAssignableFrom(Date.class)) temp="parseDate";
              	   
                 init.body().directStatement(
                          "if (checkItem(" + fieldsName.get(i) + "))\n { \n \t " + attBean + ".set" + toUpperCaseFirstChar(fieldsName.get(i)) + "(" +temp+ "("+fieldsName.get(i) + "));\n }");
                 }
                 
                 else{
              	   
                     init.body().directStatement(
                              "if (checkItem(" + fieldsName.get(i) + "))\n { \n \t " + attBean + ".set" + toUpperCaseFirstChar(fieldsName.get(i)) + "(" +fieldsName.get(i) + ");\n }");
                     }
                 // init.body()._if();
                 
            }
            // getters
            JMethod getIs = dc.method(0, is.getClass(), "is" + toUpperCaseFirstChar(is));
            getIs.body().directStatement(run.name()+"() ;\n");
            getIs.body()._return(isDone);
            JMethod geterror = dc.method(0, is.getClass(), "getErrorCode");
            // geterror.body().directStatement("return " + errorcode +
            // " ;");
            geterror.body()._return(errorCod);

            init.body().directStatement("\n return " + attBean + " ;");

            file.mkdirs();
            cm.build(file);

      
   }
}
