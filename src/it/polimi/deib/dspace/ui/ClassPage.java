package it.polimi.deib.dspace.ui;

import java.util.HashMap;

import javax.swing.JFileChooser;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import utils.JsonDatabase;

public class ClassPage extends WizardPage{
	private Composite container;
	private GridLayout layout;
	private List l1;
	private List l2;
	private String ddsmPath = "";
	private Label fileName, label_error;
	private int classCount = 0;
	private int numClasses;
	private HashMap<String, String> altDtsm;

	protected ClassPage(String title, String description) {
		super("Browse Files");
		setTitle(title);
		setDescription(description);
		altDtsm = new HashMap<String, String>();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 4;
		
		Label l = new Label(container, SWT.NONE);
		l.setText("Choose alternatives");
		l.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false));
		
		Label fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		
		l1 = new List(container, SWT.BORDER);
		l1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Button add = new Button(container, SWT.PUSH);
		add.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		add.setText(">>");
		
		Button remove = new Button(container, SWT.PUSH);
		remove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		remove.setText("<<");
		
		l2 = new List(container, SWT.BORDER);
		l2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		add.addSelectionListener(new SelectionAdapter() {
	          public void widgetSelected(SelectionEvent e) {
	        	  //Move alternative on the other side
	        	  if(l1.getSelectionCount() < 1){
	        		  return;
	        	  }
	        	  l2.add(l1.getSelection()[0]);
	        	  
	        	  container.layout();
	        	  
	        	  //Open file browser
	        	  JFileChooser chooser= new JFileChooser();
	        	  chooser.setMultiSelectionEnabled(false); //JUST ONE UML FILE
					
	        	  int choice = chooser.showOpenDialog(null);
	        	  if (choice != JFileChooser.APPROVE_OPTION) return;
	        	  altDtsm.put(l1.getSelection()[0], chooser.getSelectedFile().getPath());
	        	  
	        	  l1.remove(l1.getSelectionIndices()[0]);
	        	  
	        	  //Refresh page
	        	  container.layout();
	        	  getWizard().getContainer().updateButtons();
	          }

	      });
		
		remove.addSelectionListener(new SelectionAdapter() {
	          public void widgetSelected(SelectionEvent e) {
	        	  if(l2.getSelectionCount() < 1){
	        		  return;
	        	  }
	        	  l1.add(l2.getSelection()[0]);
	        	  altDtsm.remove(l2.getSelection()[0]);
	        	  l2.remove(l2.getSelectionIndices()[0]);
	        	  container.layout();
	          }
	      });
		
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		
		Button browse = new Button(container, SWT.PUSH);
		browse.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, false, false));
		browse.setText("Load DDSM for this class...");
		
		
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		
		fileName = new Label(container, SWT.NONE);
		fileName.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, false, false));
		
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		label_error = new Label(container, SWT.NONE);
		label_error.setText("Error: Unable to get vm configurations");
		label_error.setVisible(false);
		Button button = new Button(container, SWT.PUSH);
		button.setText("Refresh alternatives");
		button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				refreshAlternatives();
            }
		});
		fileName.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, false, false));	

		
		browse.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	JFileChooser chooser= new JFileChooser();
            	chooser.setMultiSelectionEnabled(false); //JUST ONE UML FILE
            	
            	int choice = chooser.showOpenDialog(null);

            	if (choice != JFileChooser.APPROVE_OPTION) return;
            	
            	ddsmPath = chooser.getSelectedFile().getPath();
            	
            	fileName.setText(chooser.getSelectedFile().getName());
            	//setPageComplete(true);
            	container.layout();
            	getWizard().getContainer().updateButtons();
            }

        });
		
        populateAlternatives();
        
        setPageComplete(false);
        setControl(container);
	}
	
	@Override
	public boolean canFlipToNextPage(){
		if(!ddsmPath.equals("") && l2.getItemCount() > 0){
			System.out.println("Can turn");
			return true;
		}
		return false;
	}
	
	private void populateAlternatives(){
		String[] vmConfigs = JsonDatabase.getInstance().getVmConfigs();
		if(vmConfigs == null){
			label_error.setVisible(true);
		}
		else{
			l1.setItems(JsonDatabase.getInstance().getVmConfigs());
		}

	}
	private void refreshAlternatives(){
		l1.setItems(JsonDatabase.getInstance().refreshDbContents());
	}
	
	public String getDDSMPath(){
		return ddsmPath;
	}

	public HashMap<String, String> getAltDtsm(){
		return altDtsm;
	}

	public void reset(){
		l2.removeAll();
		populateAlternatives();
		fileName.setText("");
		ddsmPath = "";
		getWizard().getContainer().updateButtons();
		container.layout();
		classCount++;
		altDtsm = new HashMap<String, String>();
	}
	
	public String[] getSelectedAlternatives() {
		return l2.getItems();
	}

	public void setNumClasses(int numClasses){
		this.numClasses = numClasses;
	}
}
