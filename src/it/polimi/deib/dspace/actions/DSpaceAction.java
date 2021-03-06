/*
Copyright 2017 Arlind Rufi
Copyright 2017 Gianmario Pozzi
Copyright 2017 Giorgio Pea

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package it.polimi.deib.dspace.actions;


import it.polimi.deib.dspace.control.DICEWrap;
import it.polimi.deib.dspace.control.FileManager;
import it.polimi.deib.dspace.control.PrivateConfiguration;
import it.polimi.deib.dspace.ui.ConfigurationDialog;
import it.polimi.deib.dspace.ui.DSpaceWizard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.wizard.*;
import org.eclipse.swt.widgets.Shell;
import it.polimi.deib.dspace.control.Configuration;

/**
 * This action activates the wizard and manages the preferences panel.
 */
public class DSpaceAction implements IWorkbenchWindowActionDelegate {
	/**
	 * The constructor.
	 */
	public DSpaceAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		System.out.println("Hola "+action.getId());
		if(action.getId().endsWith("Start")){
			Configuration.getCurrent().reset();
			PrivateConfiguration.getCurrent().clear();
			WizardDialog dialog = new WizardDialog(null, new DSpaceWizard());
			dialog.open();
			System.out.println("Starting");
//			FileManager.getInstance().parseXmlFile();
//			try {
//				DICEWrap.getWrapper().genGSPN();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			DICEWrap.getWrapper().start();
//			DICEWrap.getWrapper().sendModel();
//			FileManager.getInstance().generateInputJson();
//			FileManager.getInstance().parseDOMXmlFile("/home/kom/it.polimi.deib.dspace/DICE_plugin/input_models/hadoop/model_1_class.uml");
		}else{
			ConfigurationDialog con=new ConfigurationDialog(new Shell());
			con.load();
			con.setView();
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
	}
	
	
}