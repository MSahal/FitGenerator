package com.linedata.ekip.fitnesscreatorplugin.std.classegenerator.model.servicesfonctionnels.actions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.CoreException;

import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.sun.codemodel.JClassAlreadyExistsException;


public abstract class FitnesClassForFunctionnalService {
	
	public abstract String toUpperCaseFirstChar(String s);
	public abstract String toLowerCaseFirstChar(String s);
	public abstract Class<?>  findApproximatelyByClassName(ArrayList<Class<?> > classes, String className);
	public abstract Class<?> findByClassName(ArrayList<Class<?> > classes, String className);
	  public abstract void generateFixture(Class<?>  TS, Method list2, String path, CustomClassLoader loader,boolean open) throws IOException, JClassAlreadyExistsException, ClassNotFoundException,
      SecurityException, NoSuchMethodException, JAXBException, CoreException;

}
