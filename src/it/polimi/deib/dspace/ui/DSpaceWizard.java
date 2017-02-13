package it.polimi.deib.dspace.ui;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import it.polimi.deib.dspace.control.ClassDesc;
import it.polimi.deib.dspace.control.Configuration;
import it.polimi.deib.dspace.control.FileHandler;


public class DSpaceWizard extends Wizard{
	private FileHandler fileHandler;
	private ChoicePage choice;
	private ClassPage classp;
	private FinalPage fpage;
	private SelectFolderPage folPage;
	private int n = 0;
	private int classes;
	private ClassDesc c;
	private boolean finish = false;
	
	public DSpaceWizard() {
        super();
        setNeedsProgressMonitor(true);
        this.fileHandler=new FileHandler();
	}

	@Override
	public boolean performFinish() {
		System.out.println("FINISH");
		return true;
	}
	
	@Override
	public void addPages(){
		choice = new ChoicePage("Service type","Choose service type");
		classp = new ClassPage("Class page", "Select page parameters and alternatives");
		fpage = new FinalPage("Goodbye", ".");
		folPage=new SelectFolderPage("Select folder");
		addPage(choice);
		addPage(folPage);
		addPage(classp);
		addPage(fpage);
		
	}
	
	@Override
	public IWizardPage getNextPage(IWizardPage currentPage) {
		if (currentPage == choice){
			classes = choice.getClasses();
			Configuration.getCurrent().setNumClasses(classes);
			if(Configuration.getCurrent().getIsPrivate()){
				return folPage;
			}else{
				classp.setNumClasses(classes);
				if (Configuration.getCurrent().getHasLtc()){
					Configuration.getCurrent().setR(choice.getR());
					Configuration.getCurrent().setSpsr(choice.getSpsr());
				}
				
				return classp;
			}
			
		}
		
		if(currentPage == classp){
			c = new ClassDesc(++n);
			System.out.println("N: "+n+" classes: "+classes);
			c.setDtsmPath(classp.getDTSMPath());
			c.setAltDdsm(classp.getAltDdsm());
			Configuration.getCurrent().getClasses().add(c);
			if(n == classes){
				finish = true;
				Configuration.getCurrent().dump();
				return fpage;
			}
			classp.reset();
			return classp;
		}
		if(currentPage==this.folPage){
			
			fileHandler.setFolder(folPage.getSelectedFolder());
			fileHandler.setScenario(Configuration.getCurrent().getIsPrivate(),Configuration.getCurrent().getHasLtc());
			fileHandler.sendFile();
			finish = true;
			return fpage;
		}
		
		return null;
	}
	
	@Override
	public boolean canFinish() {
		if (finish == true)
			return true;
		return false;
	}
}
