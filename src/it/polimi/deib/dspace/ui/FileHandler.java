package it.polimi.deib.dspace.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.polimi.deib.dspace.net.NetworkManager;

public class FileHandler {
	private NetworkManager network;
	private File[] files;
	private File folder;
	private String scenario;
	private String initialMarking;
	public FileHandler(){
		network=new NetworkManager();
		
	}
	public void setFolder(String path){
		folder=new File(path);
	}
	private void getFilesFromFolder(){
		files=folder.listFiles();
	}
	public void setScenario(String scenario){
		this.scenario=scenario;
	}
	public void setInitialMarking(String initialMarking){
		this.initialMarking=initialMarking;
	}
	public void sendFile(){
		this.getFilesFromFolder();
		try {
			network.sendModel(files, scenario, initialMarking);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
