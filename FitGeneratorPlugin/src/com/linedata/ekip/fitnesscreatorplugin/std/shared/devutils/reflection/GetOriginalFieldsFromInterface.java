package com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class GetOriginalFieldsFromInterface {
	
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
				 fieldsName.add(temp);
				 returnType= ListMethods[i].getReturnType();
				 fieldsType.add(returnType);
			 }
		 }
		

		
		
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
