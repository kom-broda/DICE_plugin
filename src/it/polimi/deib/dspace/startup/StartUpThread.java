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

package it.polimi.deib.dspace.startup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.ui.IStartup;

import it.polimi.deib.dspace.control.ResultCheck;

public class StartUpThread implements IStartup{
	private int time;
	ResultCheck check;
	@Override
	public void earlyStartup() {
		loadConfiguration();
		TimerTask timerTask = new ResultCheck("results");
		// running timer task as daemon thread
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, time * 1000);
   	}
	private void loadConfiguration(){
		String filePath="ConfigFile.txt";
		int defaultTime=5;
		File f = new File(filePath);
		if(!(f.exists())) { 
			this.time=defaultTime;
		}else{
			BufferedReader br=null;
			try {
				br = new BufferedReader(new FileReader(filePath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line+"\n");
			        line = br.readLine();
			    }
			    String everything = sb.toString();
			    String[] sp=everything.split("\n");
			    this.time=Integer.parseInt(sp[1]);
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			    try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
