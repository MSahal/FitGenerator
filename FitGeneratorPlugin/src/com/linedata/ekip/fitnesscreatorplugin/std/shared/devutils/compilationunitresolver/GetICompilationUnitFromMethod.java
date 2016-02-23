package com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.compilationunitresolver;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;

public class GetICompilationUnitFromMethod {
	
	public ICompilationUnit getUnit(String className) throws CoreException{
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for(IProject project: projects){
			 if (project.isNatureEnabled("org.eclipse.jdt.core.javanature") && project.getName().equals("ekip-std-acceptance")){
				 project.open(null);
				 IJavaProject javaProject = JavaCore.create(project);
				 IPackageFragment[] packages = javaProject.getPackageFragments();
				  for (IPackageFragment mypackage : packages) {
					   if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
						    for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
						    	 String test = unit.getCorrespondingResource().getName();
						    	 String[] name = test.split("\\.");
						    	 
						    	
						    	if (name[0].equals(className)) return unit;
						      }

					      }
				  }
			 }
			}
		return null;
	}

}
