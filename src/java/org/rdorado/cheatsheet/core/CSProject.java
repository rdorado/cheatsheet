package org.rdorado.cheatsheet.core;

import java.io.File;
import java.util.ArrayList;

public class CSProject {
	
	String javaCommand;
	String pythonCommand;
	ArrayList<CSTask> tasks = new ArrayList<CSTask>();  
	
	void execute() {
		for(CSTask task : tasks) {
			task.execute();
		}
		
	}
	
}



interface CSProcess {

	void execute();
	
}

interface CSProcessor extends CSProcess {	
	
}

class CSTagger implements CSProcessor{

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
} 

interface CSEvaluator extends CSProcess {
	
}

interface CSMixer extends CSProcess {
	
}



class CSInput {
	File file;
}