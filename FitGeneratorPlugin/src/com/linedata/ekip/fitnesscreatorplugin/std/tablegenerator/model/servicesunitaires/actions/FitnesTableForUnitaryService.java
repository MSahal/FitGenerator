package com.linedata.ekip.fitnesscreatorplugin.std.tablegenerator.model.servicesunitaires.actions;

import java.io.IOException;
import java.lang.reflect.Method;

import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.sun.codemodel.JClassAlreadyExistsException;


public abstract class FitnesTableForUnitaryService {

	abstract public void generateContent(Class<?> TS, Method list2, CustomClassLoader loader, String dir,boolean open) throws IOException, JClassAlreadyExistsException;
	abstract  String toLowerCaseFirstChar(String s);
	abstract String toUpperCaseFirstChar(String s);
}