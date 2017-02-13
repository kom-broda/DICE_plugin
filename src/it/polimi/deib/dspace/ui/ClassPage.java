package it.polimi.deib.dspace.ui;

import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import it.polimi.deib.dspace.control.Configuration;
import it.polimi.deib.dspace.control.DICEWrap;
import it.polimi.deib.dspace.net.NetworkManager;

public class ClassPage extends WizardPage{
	private Composite container;
	private GridLayout layout;
	private List l1;
	private List l2;
	private String dtsmPath = "";
	private Label fileName;
	private int classCount = 0;
	private int numClasses;
	private HashMap<String, String> altDdsm;

	protected ClassPage(String title, String description) {
		super("Browse Files");
		setTitle(title);
		setDescription(description);
		altDdsm = new HashMap<String, String>();
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
	        	  altDdsm.put(l1.getSelection()[0], chooser.getSelectedFile().getPath());
	        	  
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
	        	  altDdsm.remove(l2.getSelection()[0]);
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
		browse.setText("Load DTSM for this class...");
		
		
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		fl1 = new Label(container, SWT.NONE);
		
		fileName = new Label(container, SWT.NONE);
		fileName.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, false, false));	
		
		browse.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	JFileChooser chooser= new JFileChooser();
            	chooser.setMultiSelectionEnabled(false); //JUST ONE UML FILE
            	
            	int choice = chooser.showOpenDialog(null);

            	if (choice != JFileChooser.APPROVE_OPTION) return;
            	
            	dtsmPath = chooser.getSelectedFile().getPath();
            	
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
		if(!dtsmPath.equals("") && l2.getItemCount() > 0){
			System.out.println("Can turn");
			return true;
		}
		return false;
	}
	
	private void populateAlternatives(){
		l1.setItems(NetworkManager.getInstance().getAlternatives());
	}
	
	public String getDTSMPath(){
		return dtsmPath;
	}

	public HashMap<String, String> getAltDdsm(){
		return altDdsm;
	}
	
	public void reset(){
		l2.removeAll();
		populateAlternatives();
		fileName.setText("");
		dtsmPath = "";
		getWizard().getContainer().updateButtons();
		container.layout();
		classCount++;
		altDdsm = new HashMap<String, String>();
	}
	
	public String[] getSelectedAlternatives() {
		return l2.getItems();
	}

	public void setNumClasses(int numClasses){
		this.numClasses = numClasses;
	}
}
