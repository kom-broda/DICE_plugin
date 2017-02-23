package it.polimi.deib.dspace.startup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.ui.IStartup;

import it.polimi.deib.dspace.control.GeneralConfig;
import it.polimi.deib.dspace.control.ResultCheck;

public class StartUpThread implements IStartup{
	ResultCheck check;
	@Override
	public void earlyStartup() {
		TimerTask timerTask = new ResultCheck("results");
		// running timer task as daemon thread
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, GeneralConfig.getCurrent().getTime() * 1000);
   	}
	

}
