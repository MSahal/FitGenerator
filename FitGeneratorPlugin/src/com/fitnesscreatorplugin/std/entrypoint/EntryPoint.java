package com.fitnesscreatorplugin.std.entrypoint;

import java.io.IOException;
import java.io.PrintStream;

import javax.xml.bind.JAXBException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fitnesscreatorplugin.std.shared.view.vues.MainVue;
import com.sun.codemodel.JClassAlreadyExistsException;

public class EntryPoint extends AbstractHandler{

	  public Object execute(ExecutionEvent event) throws ExecutionException
	   {
	      Shell shell = HandlerUtil.getActiveShell(event);
	      
	      ISelection sel = HandlerUtil.getActiveMenuSelection(event);
	      IStructuredSelection selection = (IStructuredSelection) sel;

	      Object firstElement = selection.getFirstElement();
	      if (firstElement instanceof ICompilationUnit)
	      {

	         try
	         {

	            createOutput(shell, firstElement);

	         }
	         catch (Exception e)
	         {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	        

	      }
	      else
	      {
	         MessageDialog.openInformation(shell, "Info", "Please select a Java source file");
	      }
	      return null;
	   }
	  
	  
	  private void createOutput(Shell shell, Object firstElement) throws ClassNotFoundException, IOException, JClassAlreadyExistsException, SecurityException, NoSuchMethodException,
      JAXBException, CoreException
      {
		  
		  
		     MessageConsole console = new MessageConsole("System Output", null);
		      ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]
		     { console });
		     ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);
		      MessageConsoleStream stream = console.newMessageStream();
		      System.setOut(new PrintStream(stream));
		      System.setErr(new PrintStream(stream));
		  
		       ICompilationUnit cu = (ICompilationUnit) firstElement;
		       MainVue vue =new MainVue();
		       vue.setCu(cu);
		       vue.open();
		  
		 
		 
     }   
	   
	  
}
