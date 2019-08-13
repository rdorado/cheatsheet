package org.rdorado.cheatsheet.core.process;

import java.io.File;
import java.util.ArrayList;

import org.rdorado.cheatsheet.tagger.POSTagger;

public class CSProject {
	
	String javaCommand;
	String pythonCommand;
	ArrayList<CSTask> tasks = new ArrayList<CSTask>();  
	
	public void execute() {
		for(CSTask task : tasks) {
			task.execute();
		}
	}
	
}



interface CSProcessor extends CSProcess {	
	
}

class CSTagger implements CSProcessor{

	POSTagger tagger;

	CSTagger(POSTagger tagger){
		this.tagger=tagger;
	}
	
	@Override
	public CSOutput execute(CSInput input) {
			
		/*for(File directory : input.getDirectories()) {
			input.processFile();
			this.tagger.tag(sentence)
			
		}*/
		return null;
	}


	
} 

interface CSEvaluator extends CSProcess {
	
}

interface CSMixer extends CSProcess {
	
}



class CSInput {

	ArrayList<File> files;
	ArrayList<File> directories;
	
	public ArrayList<File> getDirectories() {
		return this.directories;
	}
	
	public void process() {
		
	}
}