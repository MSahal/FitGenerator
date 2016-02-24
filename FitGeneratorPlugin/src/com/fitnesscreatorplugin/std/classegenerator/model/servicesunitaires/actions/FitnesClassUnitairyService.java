package com.fitnesscreatorplugin.std.classegenerator.model.servicesunitaires.actions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;

import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.sun.codemodel.JClassAlreadyExistsException;



public abstract class FitnesClassUnitairyService
{
   abstract String toUpperCaseFirstChar(String s);

   abstract String toLowerCaseFirstChar(String s);

   abstract Class<?> findApproximatelyByClassName(ArrayList<Class<?>> classes, String className);

   abstract Class<?> findByClassName(ArrayList<Class<?>> classes, String className);

   public abstract void generateFixture(Class<?> TS, Method list2, String path, CustomClassLoader loader,boolean open) throws IOException, JClassAlreadyExistsException,
            ClassNotFoundException, JavaModelException, SecurityException, NoSuchMethodException, JAXBException, CoreException;
}
