package com.linedata.ekip.fitnesscreatorplugin.std.tablegenerator.model.servicesfonctionnels.actions;

import java.io.IOException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.CoreException;

import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.sun.codemodel.JClassAlreadyExistsException;


/**
 * @author  msahal
 */
public abstract class FitnesTableForFunctionnalService {

	/**
	 * @uml.property  name="className"
	 */
	public abstract String getClassName();
	abstract String toUpperCaseFirstChar(String s);
	abstract String toLowerCaseFirstChar(String s);
	public abstract void generateContent(Class<?> TS, Method list2, CustomClassLoader loader, String dir,boolean open) throws IOException, JClassAlreadyExistsException, CoreException;
}
