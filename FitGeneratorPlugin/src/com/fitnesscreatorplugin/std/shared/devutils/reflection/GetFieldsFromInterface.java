package com.fitnesscreatorplugin.std.shared.devutils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;

import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;

public class GetFieldsFromInterface {
	
	/**
	 * @uml.property  name="fieldsName"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	ArrayList<String> fieldsName=new ArrayList<String>();
	/**
	 * @uml.property  name="fieldsType"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Class"
	 */
	ArrayList<Class> fieldsType=new ArrayList<Class>();
	
	String toLowerCaseFirstChar(String s)
	   {
	      String a = (String) s.subSequence(0, 1);
	      return a.toLowerCase() + s.substring(1);
	   }
	
	public void getFields(Class ref){
		
		String temp;
		Class returnType;
		 Method[] ListMethods = ref.getDeclaredMethods();
		 for (int i=0;i<ListMethods.length;i++){
			 if ((ListMethods[i].getName().startsWith("get")) && (!Collection.class.isAssignableFrom(ListMethods[i].getReturnType()))){
				 temp=toLowerCaseFirstChar(ListMethods[i].getName().substring(3));
				 fieldsName.add(temp+"Of"+toUpperCaseFirstChar(ref.getSimpleName()));
				 returnType= ListMethods[i].getReturnType();
				 fieldsType.add(returnType);
			 }
		 }
	
	}

	
	public void getFieldsAndReferenceFields(Class ref) throws MalformedURLException, CoreException{
	if (!(ref.getSimpleName().contains("Reference"))){
		
		
		String temp;
		Class returnType;
		 Method[] ListMethods = ref.getDeclaredMethods();
		 for (int i=0;i<ListMethods.length;i++){
			 if ((ListMethods[i].getName().startsWith("get")) && (!Collection.class.isAssignableFrom(ListMethods[i].getReturnType()))){
				 temp=toLowerCaseFirstChar(ListMethods[i].getName().substring(3));
				 fieldsName.add(temp+"Of"+toUpperCaseFirstChar(ref.getSimpleName()));
				 returnType= ListMethods[i].getReturnType();
				 fieldsType.add(returnType);
			 }
		 }
		 
		 
		 CustomClassLoader loader =new CustomClassLoader();
		 String actualpackage=ref.getPackage().toString().substring(8)+".impl";
		 Class reference=null;
		 try{
		 reference=loader.loadClass(actualpackage+"."+ref.getSimpleName()+"Impl");
		 }catch (Exception e){
			 reference=null;
		 }
		 
		 
		 if (reference != null){
		 Class superC=reference.getSuperclass();
		 Field[] test=superC.getDeclaredFields();
		 for (int i=0;i<test.length;i++){
			 fieldsName.add(test[i].getName()+"Of"+toUpperCaseFirstChar(ref.getSimpleName()+"Reference"));
			 fieldsType.add(test[i].getType());
		
		 }
		 }

	
	}
	
	else getFields(ref);
	
	}
	
	
	
	public ArrayList<String> getFieldsName() {
		return fieldsName;
	}

	

	public ArrayList<Class> getFieldsType() {
		return fieldsType;
	}
	  public String toUpperCaseFirstChar(String s)
	   {
	      String a = (String) s.subSequence(0, 1);

	      return a.toUpperCase() + s.substring(1);
	   }
	

}
