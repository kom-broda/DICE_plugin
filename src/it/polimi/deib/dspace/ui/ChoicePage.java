package it.polimi.deib.dspace.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import it.polimi.deib.dspace.net.NetworkManager;

public class ChoicePage extends WizardPage{
	private Composite container;
	private GridLayout layout;
	private Button pri;
	private Button pub;
	private int classes = 0;
	private int alternatives;
	private Text t1,h1,h2,h3;
	private List technologiesList;
	private Button existingLTC,nExistingLTC;
	private Text rTextField,SpsrTextField, classesTextField;
	private Composite ltcCompositeText;

	protected ChoicePage(String title, String description) {
		super("Choose service type");
		setTitle(title);
		setDescription(description);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		container.setLayout(layout);
        layout.numColumns = 2;
        layout.makeColumnsEqualWidth = true;
        
        
        this.provideLabel(container, "Number of classes:", SWT.NONE);
        this.provideLabel(container, "Select technology", SWT.NONE);
        
        this.classesTextField = this.provideText(container, true, SWT.BORDER);
        
        
        this.classesTextField.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	getWizard().getContainer().updateButtons();
            }

        });
        
        technologiesList = new List(container, SWT.BORDER);
        //t2.setLayoutData(g4);
        technologiesList.setItems(NetworkManager.getInstance().getTechnologies());
        technologiesList.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	getWizard().getContainer().updateButtons();
            }

        });
        
        h1 = new Text(container, SWT.PUSH);
        h1.setVisible(false);
        //h1.setLayoutData(f1);
        
        h2 = new Text(container, SWT.BORDER);
        h2.setVisible(false);
        //h2.setLayoutData(f2);
        
        pri = new Button(container, SWT.RADIO);
        //pri.setLayoutData(g5);
        
        h3 = new Text(container, SWT.BORDER);
        h3.setVisible(false);
        //h3.setLayoutData(f3);
        
        pub = new Button(container, SWT.RADIO);
        //pub.setLayoutData(g6);
        

        pri.setVisible(true);
        pub.setVisible(true);
        pri.setText("Private");
        pub.setText("Public");
        
        Composite ltcUberComposite = new Composite(container, SWT.NONE);
        RowLayout layoutRow_uber = new RowLayout();
        layoutRow_uber.fill = true;
        ltcUberComposite.setLayout(layoutRow_uber);
        //Composite for ltc radio btns
        final Composite ltcComposite = new Composite(ltcUberComposite, SWT.NONE);
        RowLayout layoutRow = new RowLayout();
        layoutRow.type = SWT.VERTICAL;
        ltcComposite.setLayout(layoutRow);
        ltcComposite.setVisible(false);
        
        //Composite for ltc text inputs
        ltcCompositeText = new Composite(ltcUberComposite, SWT.NONE);
        RowLayout layoutRow_1 = new RowLayout();
        layoutRow_1.type = SWT.VERTICAL;
        ltcCompositeText.setLayout(layoutRow_1);
        ltcCompositeText.setVisible(false);
        
        Composite rTextComposite = new Composite(ltcCompositeText, SWT.NONE);
        RowLayout layoutRow_4 = new RowLayout();
        layoutRow_4.type = SWT.HORIZONTAL;
        layoutRow_4.pack = false;
        rTextComposite.setLayout(layoutRow_4);
        Label rTextLabel = new Label(rTextComposite, SWT.NONE);
        rTextLabel.setText("R");
        this.rTextField = new Text(rTextComposite, SWT.BORDER);
        this.rTextField.setEditable(true);
  
        
        Composite tTextComposite = new Composite(ltcCompositeText, SWT.NONE);
        RowLayout layoutRow_3 = new RowLayout();
        layoutRow_3.type = SWT.HORIZONTAL;
        layoutRow_3.pack = false;
        tTextComposite.setLayout(layoutRow_3);
        Label tTextLabel = new Label(tTextComposite, SWT.NONE);
        tTextLabel.setText("Spsr");
        this.SpsrTextField = new Text(tTextComposite,SWT.BORDER);
        this.SpsrTextField.setEditable(true);
        this.existingLTC = new Button(ltcComposite,SWT.RADIO);
        this.existingLTC.setText("Existing LTC");
        this.nExistingLTC = new Button(ltcComposite, SWT.RADIO);
        this.nExistingLTC.setText("Non existing LTC");
        
        
        
		
        
        
        
        pri.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	getWizard().getContainer().updateButtons();
            	System.out.println("Choice: PRIVATE");
            	ltcCompositeText.setVisible(false);
            	ltcComposite.setVisible(false);
            }

        });
        
        pub.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	getWizard().getContainer().updateButtons();
            	ltcComposite.setVisible(true);
            	if(existingLTC.getSelection()){
            		ltcCompositeText.setVisible(true);
            	}
            	System.out.println("Choice: PUBLIC");
            }

        });
        this.existingLTC.addSelectionListener(new SelectionAdapter(){
        	 public void widgetSelected(SelectionEvent e) {
             	ltcCompositeText.setVisible(true);
             	getWizard().getContainer().updateButtons();
             }
        });
        this.rTextField.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent arg0) {
				getWizard().getContainer().updateButtons();
			}
        });
        this.SpsrTextField.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent arg0) {
				getWizard().getContainer().updateButtons();
			}
        });
        this.nExistingLTC.addSelectionListener(new SelectionAdapter(){
       	 public void widgetSelected(SelectionEvent e) {
            	ltcCompositeText.setVisible(false);
            	getWizard().getContainer().updateButtons();
            }
       });
        
        setControl(container);
        setPageComplete(false);
	}
	/**
	 * 
	 * @param container
	 * @param labelText
	 * @param style
	 * @return
	 */
	private Label provideLabel(Composite container, String labelText, int style){
		Label label = new Label(container, style);
		if(labelText != null){
			label.setText(labelText);
		}
		return label;
		
	}
	/**
	 * 
	 * @param container
	 * @param editable
	 * @param style
	 * @return
	 */
	private Text provideText(Composite container, Boolean editable, int style){
		Text text = new Text(container, style);
		text.setEditable(editable);
		return text;
	}
	
	public boolean getChoice(){
		if(this.ltcCompositeText.getVisible()){
			if(!this.rTextField.getText().equals("") &&
					!this.SpsrTextField.getText().equals("")){
				return true;
			}
			else{
				return false;
			}
		}
		return true;
		
	}
	
	public int getClasses(){
		classes = Integer.parseInt(t1.getText());
		return classes;
	}

	public int getAlternatives(){
		return alternatives;
	}
	
	public String getTechnology(){
		return technologiesList.getSelection()[0];
	}
	
	@Override
	public boolean canFlipToNextPage(){
		if(this.getChoice() && technologiesList.getSelectionCount() > 0 && (pri.getSelection() || pub.getSelection()) && getClasses() != 0){
			return true;
		}
		return false;
}
}
