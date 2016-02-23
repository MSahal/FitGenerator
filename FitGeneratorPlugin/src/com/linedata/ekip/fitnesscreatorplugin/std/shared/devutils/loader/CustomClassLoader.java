package com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;

public class CustomClassLoader 
{
	

	
public Class<?> loadClass(String name) throws MalformedURLException, CoreException {
		 
	    
		
		System.out.println("getting all projects of the workspace:");	
		List<IJavaProject> javaProjects=findAllProject();
		for(IJavaProject project: javaProjects){
			System.out.println("getting project :"+project.getElementName());
		}
		System.out.println("all projects suuccefully getted from the workspace:");

		System.out.println("getting all classloaders:");
		
		Class<?> clazz=null;
		for(IJavaProject project: javaProjects){
			
			
			System.out.println("getting classloader of "+project.getElementName()+" ...");
			
			
			try {
				clazz =getClassLoader(project).loadClass(name);
			
				
				return clazz;
			} catch (ClassNotFoundException e) {
				
				// TODO Auto-generated catch block
				
			}
			
			
		
		}
		
		return null;
	}
	
	
	public List<IJavaProject>  findAllProject() throws CoreException{
		List<IJavaProject> javaProjects = new ArrayList<IJavaProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for(IProject project: projects){
			project.open(null);
		 if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")){
		 IJavaProject javaProject = JavaCore.create(project);
		 javaProjects.add(javaProject);
		 }
		}
		return javaProjects;
	}
	
	
	public URLClassLoader   getClassLoader(IJavaProject project) throws CoreException, MalformedURLException{
		
		ArrayList<String> classPathEntries1=new ArrayList<String>();
			
		//System.out.println("recherche de UnresolvedRuntimeClasspath de projet :"+project.getElementName());	
		//System.out.println("trying to use the class JavaRuntime for getting  UnresolvedRuntimeClasspath of project"+project.getElementName());	
		//IRuntimeClasspathEntry[] entries =JavaRuntime.computeUnresolvedRuntimeClasspath(project);
		//System.out.println("trying to use the class JavaRuntime ===> suuceed :");
		/*if (entries.length>0){
		for (int i = 0; i < entries.length; i++) {
			//System.out.println("le nom de entry unresolved :"+entries[i].toString());
			 IRuntimeClasspathEntry[] resolved = 
					 JavaRuntime.resolveRuntimeClasspathEntry(entries[i], project);
			 for (int j = 0; j < resolved.length;j++) {
				// System.out.println("le nom de entry that has been resolved :"+resolved[j].toString().substring(0, resolved[j].toString().indexOf("[")));
				 classPathEntries1.add(resolved[j].toString().substring(0, resolved[j].toString().indexOf("[")));
			 }
		}
		}*/
		
		//System.out.println("recherche de defaultRuntimeClasspath de projet :"+project.getElementName());	
		String[] classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(project);
		for (int i = 0; i < classPathEntries.length; i++) {
			 //System.out.println("Resolved class path:"+classPathEntries[i].toString());
			classPathEntries1.add(classPathEntries[i]);
		}
		
		List<URL> urlList = new ArrayList<URL>();
		/*for (int i = 0; i < classPathEntries.length; i++) {
		 String entry = classPathEntries[i];
		 System.out.println("le nom de l'entry path :"+entry);
		 IPath path = new Path(entry);
		 URL url =  path.toFile().toURI().toURL();
		 urlList.add(url);
		}*/

	
		
		for (int i = 0; i < classPathEntries1.size(); i++) {
			 String entry = classPathEntries1.get(i);
		
			 IPath path = new Path(entry);
			 URL url =  path.toFile().toURI().toURL();
			 urlList.add(url);
			}

		
		ClassLoader parentClassLoader = project.getClass().getClassLoader();
		URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
		URLClassLoader classLoader = new URLClassLoader(urls, parentClassLoader);
		
		return classLoader;
	}

}
