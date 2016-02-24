package com.fitnesscreatorplugin.std.shared.view.vues;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.osgi.framework.Bundle;

import com.fitnesscreatorplugin.std.classegenerator.controller.actions.CreateClasses;
import com.fitnesscreatorplugin.std.shared.devutils.compilationunitresolver.GetICompilationUnitFromMethod;
import com.fitnesscreatorplugin.std.shared.devutils.loader.CustomClassLoader;
import com.fitnesscreatorplugin.std.tablegenerator.controller.actions.CreateTables;



public class MainVue {

	/**
	 * @uml.property  name="shlFitnessPlugin"
	 * @uml.associationEnd  
	 */
	private Shell shlFitnessPlugin;
	/**
	 * @uml.property  name="openJavaClass"
	 */
	private boolean openJavaClass;
	/**
	 * @uml.property  name="openTextFile"
	 */
	private boolean openTextFile;
	/**
	 * @return
	 * @uml.property  name="openJavaClass"
	 */
	public boolean isOpenJavaClass() {
		return openJavaClass;
	}

	/**
	 * @param openJavaClass
	 * @uml.property  name="openJavaClass"
	 */
	public void setOpenJavaClass(boolean openJavaClass) {
		this.openJavaClass = openJavaClass;
	}

	/**
	 * @return
	 * @uml.property  name="openTextFile"
	 */
	public boolean isOpenTextFile() {
		return openTextFile;
	}

	/**
	 * @param openTextFile
	 * @uml.property  name="openTextFile"
	 */
	public void setOpenTextFile(boolean openTextFile) {
		this.openTextFile = openTextFile;
	}
	/**
	 * @uml.property  name="text_1"
	 * @uml.associationEnd  
	 */
	private Text text_1;
	/**
	 * @uml.property  name="table"
	 * @uml.associationEnd  
	 */
	private Table table;
	/**
	 * @uml.property  name="methods" multiplicity="(0 -1)" dimension="1"
	 */
	private Method[] methods;
	/**
	 * @uml.property  name="list"
	 */
	private ArrayList<Method> list;
	/**
	 * @uml.property  name="btnOk"
	 * @uml.associationEnd  
	 */
	private Button btnOk;
	/**
	 * @uml.property  name="btnSelectAll"
	 * @uml.associationEnd  
	 */
	private Button btnSelectAll;
	/**
	 * @uml.property  name="btnDeselectAll"
	 * @uml.associationEnd  
	 */
	private Button btnDeselectAll;
	/**
	 * @uml.property  name="cu"
	 * @uml.associationEnd  
	 */
	private ICompilationUnit cu;

	 /**
	 * @uml.property  name="javaDirectory"
	 */
	String javaDirectory;
	/**
	 * @uml.property  name="textDirectory"
	 */
	private String textDirectory;

	/**
	 * @return
	 * @uml.property  name="cu"
	 */
	public ICompilationUnit getCu() {
		return cu;
	}

	/**
	 * @param cu
	 * @uml.property  name="cu"
	 */
	public void setCu(ICompilationUnit cu) {
		this.cu = cu;
	}

	/**
	 * @return
	 * @uml.property  name="methods"
	 */
	public Method[] getMethods() {
		return methods;
	}

	/**
	 * @param methods
	 * @uml.property  name="methods"
	 */
	public void setMethods(Method[] methods) {
		this.methods = methods;
	}

	/**
	 * @return
	 * @uml.property  name="shlFitnessPlugin"
	 */
	public Shell getShlFitnessPlugin() {
		return shlFitnessPlugin;
	}

	/**
	 * @param shlFitnessPlugin
	 * @uml.property  name="shlFitnessPlugin"
	 */
	public void setShlFitnessPlugin(Shell shlFitnessPlugin) {
		this.shlFitnessPlugin = shlFitnessPlugin;
	}



	/**
	 * @return
	 * @uml.property  name="text_1"
	 */
	public Text getText_1() {
		return text_1;
	}

	/**
	 * @param text_1
	 * @uml.property  name="text_1"
	 */
	public void setText_1(Text text_1) {
		this.text_1 = text_1;
	}

	/**
	 * @return
	 * @uml.property  name="table"
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @param table
	 * @uml.property  name="table"
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	// public void run() {
	// try {
	// MainVue window = new MainVue();
	// window.open();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * Open the window.
	 * 
	 * @throws CoreException
	 * @throws IOException
	 */
	public void open() throws CoreException, IOException {
	
			Display display = Display.getCurrent();
			
			
			createContents(display);

	}

	/**
	 * Create contents of the window.
	 * 
	 * @throws CoreException
	 * @throws IOException
	 * @wbp.parser.entryPoint
	 */
	protected void createContents(Display display) throws CoreException,
			IOException {
		list = new ArrayList<Method>();
		shlFitnessPlugin = new Shell(display, SWT.CLOSE | SWT.ON_TOP | SWT.MIN );
		shlFitnessPlugin.setSize(450, 467);
		shlFitnessPlugin.setText("FitNesse Plugin");

		Bundle bundle = Platform.getBundle("LinedataFitNesseEclipsePlugin");
		Path path = new Path("resources/Capture.PNG");
		@SuppressWarnings("deprecation")
		URL fileURL = Platform.find(bundle, path);
		InputStream in = fileURL.openStream();
		Image small = new Image(display, in);
		shlFitnessPlugin.setImage(small);

		CustomClassLoader classLoader = new CustomClassLoader();
		String pack = cu.getPackageDeclarations()[0].toString().substring(8,
				cu.getPackageDeclarations()[0].toString().indexOf("[") - 1);
		String test = cu.getCorrespondingResource().getName();

		// Need
		String[] name = test.split("\\.");
		Class<?> clas = classLoader.loadClass(pack + "." + name[0]);
		methods = clas.getDeclaredMethods();

		btnSelectAll = new Button(shlFitnessPlugin, SWT.NONE);

		btnSelectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int m = 0; m < table.getItemCount(); m++) {
					if (table.getItems()[m].getChecked() == false) {
						table.getItems()[m].setChecked(true);
						table.selectAll();
						for (int k = 0; k < methods.length; k++) {

							list.add(methods[k]);

						}
					}
				}
			}
		});

		btnSelectAll.setBounds(349, 36, 75, 25);
		btnSelectAll.setText("Select All");

		btnDeselectAll = new Button(shlFitnessPlugin, SWT.NONE);

		btnDeselectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int m = 0; m < table.getItems().length; m++) {
					if (table.getItems()[m].getChecked() == true) {
						table.getItems()[m].setChecked(false);
					}
					for (int k = 0; k < methods.length; k++) {

						list.remove(methods[k]);

					}

				}
			}
		});

		btnDeselectAll.setBounds(349, 67, 75, 25);
		btnDeselectAll.setText("Deselect All");

		Button btnCancel = new Button(shlFitnessPlugin, SWT.NONE);

		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				shlFitnessPlugin.close();

			}
		});
		btnCancel.setBounds(336, 404, 75, 25);
		btnCancel.setText("Cancel");

		Label lblTextFilesDirectory = new Label(shlFitnessPlugin, SWT.NONE);
		lblTextFilesDirectory.setBounds(10, 353, 122, 15);
		lblTextFilesDirectory.setText("Text Files Directory");

		text_1 = new Text(shlFitnessPlugin, SWT.BORDER);
		text_1.setBounds(139, 350, 214, 21);

		Button btnBrowse_1 = new Button(shlFitnessPlugin, SWT.NONE);
		btnBrowse_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(new Shell(SWT.ON_TOP)  );

				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(text_1.getText());

				// Change the title bar text
				dlg.setText("Select a directory");

				// Customizable message displayed in the dialog
				dlg.setMessage("Select a directory");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dlg.open();
				if (dir != null) {
					// Set the text box to the new selection
					text_1.setText(dir);
					textDirectory = dir;
				}
			}
		});

		btnBrowse_1.setBounds(359, 348, 75, 25);
		btnBrowse_1.setText("browse ...");

		Label lblListOfMethods = new Label(shlFitnessPlugin, SWT.NONE);
		lblListOfMethods.setBounds(10, 15, 103, 15);
		lblListOfMethods.setText("List of Methods");

		table = new Table(shlFitnessPlugin, SWT.CHECK | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL );

	

		for (int i = 0; i < methods.length; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			
			item.setText(methods[i].getReturnType().getSimpleName() + " "
					+ methods[i].getName());

		}

		table.setBounds(10, 36, 333, 233);

		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {

				TableItem[] selection = table.getItems();

				for (int i = 0; i < selection.length; i++) {
					if (selection[i].getChecked() == true) {

						for (int k = 0; k < methods.length; k++) {
							if (selection[i].getText().equals(
									methods[k].getReturnType().getSimpleName()
											+ " " + methods[k].getName())) {

								list.add(methods[k]);

							}

						}
					}

					if (selection[i].getChecked() == false) {

						table.getItems()[i].setChecked(false);
						for (int k = 0; k < methods.length; k++) {
							if (selection[i].getText().equals(
									methods[k].getReturnType().getSimpleName()
											+ " " + methods[k].getName())) {

								list.remove(methods[k]);

							}

						}
					}

				}
			}
		});

		btnOk = new Button(shlFitnessPlugin, SWT.NONE);
		
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlFitnessPlugin.close();
				
				final CreateClasses createClasses = new CreateClasses();
				final CreateTables createTables = new CreateTables();
				
				IProject acceptanceProject=null;
				try {
					IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
					for(IProject project: projects){
						 if (project.isNatureEnabled("org.eclipse.jdt.core.javanature") && project.getName().equals("ekip-std-acceptance")){
						  javaDirectory=project.getLocation().toString()+"/src/test/java";
						  System.out.println("le chemin des classes java : "+javaDirectory);
						  acceptanceProject=project;
						 }
						}
					
					System.out.println("*****************  Generating fixture ...");
					createClasses.write(javaDirectory, cu, list,openJavaClass);
					acceptanceProject.refreshLocal(IResource.DEPTH_INFINITE, null);
			
					//openJavaClass
					 System.out.println("***************** Generating tables ...");
					createTables.write(textDirectory, cu, list,openTextFile);
					
					
					GetICompilationUnitFromMethod getICompilationUnitFromMethod=new GetICompilationUnitFromMethod();
					 IEditorPart javaEditor =null;
					if (openJavaClass==true){
						String className=null;
						for (int i=0;i<list.size();i++){
							if (Collection.class.isAssignableFrom(list.get(i).getReturnType())){
								   Class listElementType=null;
						           	Type genericFieldType = list.get(i).getGenericReturnType();	
						           	if(genericFieldType instanceof ParameterizedType){
						           		ParameterizedType aType = (ParameterizedType) genericFieldType;
						           		
						           	   Type[] fieldArgTypes = aType.getActualTypeArguments();
						           	    for(Type fieldArgType : fieldArgTypes){
						           	    	listElementType = (Class) fieldArgType;      
						           	    }
						           	}
								
							className=toUpperCaseFirstChar(list.get(i).getName())+toUpperCaseFirstChar(listElementType.getSimpleName())+"AcceptanceTest";
							}
							else{
								className=toUpperCaseFirstChar(list.get(i).getName())+list.get(i).getReturnType().getSimpleName()+"AcceptanceTest";
							}
							System.out.println("opening "+className);
							javaEditor = JavaUI.openInEditor(getICompilationUnitFromMethod.getUnit(className));
						}
					}
					
					
					
					//openTextFile
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				
			}

		});

	
		btnOk.setBounds(254, 404, 75, 25);
		btnOk.setText("OK");

		table.setHeaderVisible(false);
		table.setLinesVisible(false);
		shlFitnessPlugin.open();
		shlFitnessPlugin.layout();
		centrerSurEcran(display, shlFitnessPlugin);
		
		final Button btnOpenGeneratedJava = new Button(shlFitnessPlugin, SWT.CHECK);
		btnOpenGeneratedJava.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnOpenGeneratedJava.getSelection()) setOpenJavaClass(true);
				else setOpenJavaClass(false);
			}
		});
		btnOpenGeneratedJava.setBounds(10, 285, 180, 16);
		btnOpenGeneratedJava.setText("open generated java classes");
		
		final Button btnOpenGeneratedText = new Button(shlFitnessPlugin, SWT.CHECK);
		btnOpenGeneratedText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if (btnOpenGeneratedText.getSelection()) setOpenTextFile(true);
				else setOpenTextFile(false);
			}
		});
		btnOpenGeneratedText.setBounds(10, 307, 150, 16);
		btnOpenGeneratedText.setText("open generated text files");
		while (!shlFitnessPlugin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		

	}

	public static void centrerSurEcran(Display display, Shell shell) {
		Rectangle rect = display.getClientArea();
		Point size = shell.getSize();
		int x = (rect.width - size.x) / 2;
		int y = (rect.height - size.y) / 2;
		shell.setLocation(new Point(x, y));
	}
	
	  public String toUpperCaseFirstChar(String s)
	   {
	      String a = (String) s.subSequence(0, 1);

	      return a.toUpperCase() + s.substring(1);
	   }
}
