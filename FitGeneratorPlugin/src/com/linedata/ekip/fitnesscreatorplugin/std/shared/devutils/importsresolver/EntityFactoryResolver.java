package com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.importsresolver;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;

public class EntityFactoryResolver {
	
	public String getFactory(Class classe) throws CoreException{
		
//		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
//		for(IProject project: projects){
//		 if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")){
//		 IJavaProject javaProject = JavaCore.create(project);
//		 IPackageFragment[] packages = javaProject.getPackageFragments();
//		    for (IPackageFragment mypackage : packages) {
//		 
//		        if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
//		        	int index=mypackage.toString().indexOf("[")-1;
//		        	String tempPackage=mypackage.toString().substring(0, index);
//		        	
//		        	if (tempPackage.endsWith(".factory")) {
//		        		
//		        		
//		         
//		        	  for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
//		        		 
//		        		  if (unit.getCorrespondingResource().getName().equals(entityName+"Factory.java")){
//		        			  String pack = unit.getPackageDeclarations()[0].toString().substring(8, unit.getPackageDeclarations()[0].toString().indexOf("[") - 1);
//		        		      String test = unit.getCorrespondingResource().getName();
//		        		      String[] name = test.split("\\.");
//		        		     
//		        		      return pack + "." + name[0];
//		        		  }
//		        		  
//		        	  }
//		        	}
//
//		        }
//		 
//		
//		 }
//		}
//		
//	}
//		return null;
//
//}
		String temp=classe.getPackage().toString().substring(8);
		
		return temp+".factory."+classe.getSimpleName()+"Factory";
	}
}
